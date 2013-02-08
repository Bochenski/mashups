var express = require('express');
var app = express();

app.get('/hello/:name', function(req, res) {
  res.send('app1 says hellossd ' + req.params.name);
});

app.get('/helloworld', function(req, res) {
  res.send('hello world!');
})

module.exports = app;