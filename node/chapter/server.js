var nopt = require('nopt');
var App  = require('./app');
var app  = new App();

var knownOptions = { "with-cukestall": Boolean };
var shortOptions = {};
var options      = nopt(knownOptions, shortOptions, process.argv);

if (options['with-cukestall']) {
  var CukeStall = require('cukestall');

  var Book = require('./models/book');
  
  console.log("Mounting CukeStall on /cukestall");

  app.server.use(
    CukeStall.runner({
      featurePaths:     [__dirname + '/test/features/manage_books.feature'],
      stepDefsPaths:    [__dirname + '/test/features/step_definitions/book_stepdefs.js'],
      supportCodePaths: [__dirname + '/test/features/support/cukestall.js'],
      backdoors: {
        reset_all: function (req, res, next) {
          Book.collection.drop(function() {
            res.end("Done.");
          });
        }
      }
    })
  );
}

app.start();
