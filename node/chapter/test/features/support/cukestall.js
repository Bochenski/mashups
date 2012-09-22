var CukeStallSupport = function CukeStallSupport () {
  if (typeof window == 'undefined')
    return; // do not run outside of browsers

  // --- WORLD ---

  var CukeStallWorld = function CukeStallWorld(callback) {
    this.browser = new window.CukeStall.FrameBrowser('#cucumber-browser');
    this.runInSequence(
      this.cleanUp,
      callback
    );
  };

  this.World = CukeStallWorld;

  // DSL

  CukeStallWorld.prototype.addNewBook = function (callback) {
    var self = this;

    self.prepareNewBookAttributes();
    var visitRoot               = self.browser.visitUrl("/");
    var waitForPageToLoad       = self.browser.waitForPageToLoad();
    var clickAddBookLink        = self.browser.clickLink("Add a new Book");
    var fillInTitle             = self.browser.fillIn("#bookTitle", self.newBookAttributes.title);
    var fillInChapters          = self.browser.fillIn("#bookChapters", self.newBookAttributes.chapters);
    var clickCreateBookButton   = self.browser.clickButton("#btnAddNewBook");
    var waitForPageToLoad       = self.browser.waitForPageToLoad();
    self.runInSequence(
      visitRoot,
      waitForPageToLoad,
      clickAddBookLink,
      visitRoot,
      fillInTitle,
      fillInChapters,
      clickCreateBookButton,
      waitForPageToLoad,
      callback
    );
  };

  CukeStallWorld.prototype.assertNewBookIsInLibrary = function (callback) {
    var self = this;

    var visitRoot                         = self.browser.visitUrl("/");
    var clickBookLink                     = self.browser.clickLink(self.newBookAttributes.title);
    var waitForPageToLoad                 = self.browser.waitForPageToLoad();
    var assertDisplayedBookTitle          = self.browser.assertBodyText(self.newBookAttributes.title);
    var assertDisplayedBookChapters       = self.browser.assertBodyText(self.newBookAttributes.chapters);
    self.runInSequence(
      visitRoot,
      clickBookLink,
      waitForPageToLoad,
      assertDisplayedBookTitle,
      assertDisplayedBookChapters,
      callback
    );
  };

  // helpers

  CukeStallWorld.prototype.cleanUp = function (callback) {
    var resetAllRemotely = RemoteCommand("reset_all");
    var visitRoot        = this.browser.visitUrl("about:blank");
    this.runInSequence(
      resetAllRemotely,
      visitRoot,
      callback
    );
  };

  CukeStallWorld.prototype.prepareNewBookAttributes = function () {
    this.newBookAttributes = {
      title: "Cucumber au gratin",
      chapters: "2"
    };
  };

  CukeStallWorld.prototype.runInSequence = function () {
    var self      = this;
    var funcCalls = Array.prototype.slice.apply(arguments);
    var funcCall  = funcCalls.shift();
    if (funcCalls.length > 0) {
      var subCallback = function () { self.runInSequence.apply(self, funcCalls) };
      funcCall.call(self, subCallback);
    } else {
      funcCall.call(self);
    }
  };

  // Remote calls

  var getRemoteUrlForFunction = function (funcName) {
    return "/cukestall/" + funcName;
  };

  var RemoteQuery = function RemoteQuery(funcName, data) {
    var self = this;

    return function (callback) {
      var url = getRemoteUrlForFunction(funcName);
      $.getJSON(url, data, function (results, textStatus, jqXHR) {
        callback(results);
      });
    };
  };

  var RemoteCommand = function RemoteCommand(funcName, data) {
    var self = this;

    return function (callback) {
      var url = getRemoteUrlForFunction(funcName);
      $.post(url, data, function (results, textStatus, jqXHR) {
        callback();
      });
    };
  };
};

module.exports = CukeStallSupport;
