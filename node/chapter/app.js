
/**
 * Module dependencies.
 */

var express = require('express'),
  routes = require('./routes'),
  api = require('./routes/api');

var DEFAULT_HTTP_LISTEN_PORT = 9797;

var App = function App(options) {
  var self = this;
  options        = options || {};
  self.port      = options['port'] || DEFAULT_HTTP_LISTEN_PORT;
  self.logStream = options['logStream'] || process.stdout;

  var server = this.server = express.createServer();
  // Configuration

  server.configure(function(){
    server.set('views', __dirname + '/views');
    server.set('view engine', 'jade');
    server.set('view options', {
      layout: false
    });
    server.use(express.logger({format: 'dev', stream: self.logStream}));
    server.use(express.bodyParser());
    server.use(express.methodOverride());
    server.use(express.static(__dirname + '/public'));
    server.use(server.router);
  });

  server.configure('development', function(){
    server.use(express.errorHandler({ dumpExceptions: true, showStack: true }));
  });

  server.configure('production', function(){
    server.use(express.errorHandler());
  });

  // Routes

  server.get('/', routes.index);
  server.get('/partials/:name', routes.partials);

  // JSON API

  server.get('/api/books', api.books);

  server.get('/api/book/:id', api.book);
  server.post('/api/book', api.addBook);
  server.put('/api/book/:id', api.editBook);
  server.delete('/api/book/:id', api.deleteBook);

  // redirect all others to the index (HTML5 history)
  // server.get('*', routes.index);

}

App.prototype.start = function start() {
  this.server.listen(this.port);
  this.baseUrl = "http://localhost:" + this.port;
  console.log("Listening on port " + this.port);
};

App.prototype.stop = function stop() {
  this.server.close();
};

module.exports = App;