var bookStepDefs = function() {
  this.After(function (callback) {
    if (this.tearDown) {
      this.tearDown(callback);
    } else {
      callback();
    }
  });

  this.When(/^I add a book$/, function(callback) {
    this.addNewBook(callback);
  });

  this.Then(/^I see the book in the library$/, function(callback) {
    this.assertNewBookIsInLibrary(callback);
  });
};

// Node.js:
if (typeof(module) !== 'undefined')
  module.exports = bookStepDefs;
