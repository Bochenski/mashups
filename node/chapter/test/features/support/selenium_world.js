var SeleniumWorld = function SeleniumWorld(callback) {
  var LOG_FILE       = __dirname + '/../../log/cucumber.selenium.log';
  var SELENIUM_SPEED = 1000; // ms

  var fs       = require('fs');
  var selenium = require('selenium-launcher');
  var soda     = require('soda');
  var App      = require('../../../app.js');
  var Browser  = require("zombie");

  var self = this;

  var withSelenium = function (callback) {
    if (global._seleniumInstance) {
      callback(null, global._seleniumInstance);
    } else {
      selenium(function (err, selenium) {
        global._seleniumInstance = selenium;
        process.on('exit', function() {
          selenium.kill();
        });
        callback(null, selenium);
      });
    }
  };

  var init = function () {
    var logFile  = fs.createWriteStream(LOG_FILE, {flags: 'a'});
    self.app     = new App({port: 21014, logStream: logFile});
    self.app.start();

    withSelenium(function(err, selenium) {
      self.browser = soda.createClient({
        host: selenium.host,
        port: selenium.port,
        url:  self.app.baseUrl,
        browser: 'safari'
      });

      self.browser.setSpeed(SELENIUM_SPEED, function(err) {
        self.browser.session(function(err) {
          callback();
        });
      });
    })
  };

  self.cleanUp(init);
};

SeleniumWorld.prototype.addNewBook = function (callback) {
  var self = this;

  self.prepareNewBookAttributes();
  self.browser
    .chain
    .open('/')
    .clickAndWait('link=Add a new Book')
    .type('id=bookTitle', self.newBookAttributes.title)
    .type('id=bookChapters', self.newBookAttributes.chapters)
    .clickAndWait('id=btnAddNewBook')
    .end(function(err) {
      if (err)
        callback.fail(err);
      else
        callback();
    });
};

SeleniumWorld.prototype.assertNewBookIsInLibrary = function (callback) {
  var self = this;
  self.browser
    .chain
    .open('/')
    .clickAndWait('link='+self.newBookAttributes.title)
    .getBodyText(function(text) {
      text = text.replace(/^ +| +$/gm, '');

      if (text.indexOf(self.newBookAttributes.title) == -1)
        throw new Error("Book title not found (" + self.newBookAttributes.title + ").");

      if (text.indexOf(self.newBookAttributes.chapters) == -1)
        throw new Error("Book chapters not found (" + self.newBookAttributes.chapters + ").");

    })
    .end(function(err) {
      if (err)
        callback.fail(err);
      else
        callback();
    });
};

SeleniumWorld.prototype.cleanUp = function (callback) {
  var Book = require('../../../models/book');
  Book.collection.drop(function (err) {
    callback();
  });
};

SeleniumWorld.prototype.tearDown = function (callback) {
  var self = this;
  if (self && self.app) {
    self.app.stop();
    self.browser.close(function () {
      self.app = null;
      self.browser = null;
      callback();
    });
  } else {
    callback();
  }
};

SeleniumWorld.prototype.prepareNewBookAttributes = function () {
    this.newBookAttributes = {
      title: "Cucumber au gratin",
      chapters: "2"
    };
  };

exports.World = SeleniumWorld;
