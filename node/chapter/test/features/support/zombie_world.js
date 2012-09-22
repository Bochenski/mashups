var ZombieWorld = function ZombieWorld(callback) {
  var LOG_FILE = __dirname + '/../../log/cucumber.zombie.log';

  var fs      = require('fs');
  var App     = require('../../../app.js');
  var Browser = require("zombie");

  var self = this;

  var init = function() {
    var logFile  = fs.createWriteStream(LOG_FILE, {flags: 'a'});
    self.app     = new App({port: 21013, logStream: logFile});
    self.app.start();
    self.browser = new Browser({ site: self.app.baseUrl });
    callback();
  };

  self.cleanUp(init);
};

ZombieWorld.prototype.addNewBook = function (callback) {
  var self = this;

  self.prepareNewBookAttributes();
  self.browser.visit('/', function() {
    self.browser.clickLink('Add a new Book', function() {
      self.browser
        .fill("#bookTitle", self.newBookAttributes.title)
        .fill("#bookChapters", self.newBookAttributes.chapters)
        .pressButton("#btnAddNewBook", callback);
    });
  });
};

ZombieWorld.prototype.assertNewBookIsInDiary = function (callback) {
  var self = this;
  self.browser.visit('/', function() {
    self.browser.clickLink(self.newBookAttributes.title, function() {
      if (self.browser.text('body').indexOf(self.newBookAttributes.title) == -1)
        callback.fail("Book title not found (" + self.newBookAttributes.title + ").");

      if (self.browser.text('body').indexOf(self.newBookAttributes.chapters) == -1)
        callback.fail("Book chapters not found (" + self.newBookAttributes.chapters + ").");

      else
        callback();
    });
  });
};

ZombieWorld.prototype.cleanUp = function (callback) {
  var Book = require('../../../models/book');
  Book.collection.drop(function (err) {
    callback();
  });
};

ZombieWorld.prototype.prepareNewBookAttributes = function () {
    this.newBookAttributes = {
      title: "Cucumber au gratin",
      chapters: "2"
    };
  };

exports.World = ZombieWorld;
