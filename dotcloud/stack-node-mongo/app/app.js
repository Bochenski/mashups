/* Express quick setup */
var express = require('express');
var app = express();

/* mongodb setup */
var mongo = require('mongodb');

var host = process.env['DOTCLOUD_DB_MONGODB_HOST'] || 'localhost';
var port = process.env['DOTCLOUD_DB_MONGODB_PORT'] ||  27017;
port = parseInt(port);
var user = process.env['DOTCLOUD_DB_MONGODB_LOGIN'] || undefined;
var pass = process.env['DOTCLOUD_DB_MONGODB_PASSWORD'] || undefined;

var mongoServer = new mongo.Server(host, port, {});
var db = new mongo.Db("test", mongoServer, {auto_reconnect:true});

app.get("/", function(req, res){
    var html = '<div id="content" data-stack="node" data-appname="' + process.env['DOTCLOUD_PROJECT'] + '">';
    html += 'Hello World, from Express!';
    html += '<script type="text/javascript" src="https://helloapp.dotcloud.com/inject.min.js"></script>';

    db.collection("test", function(err, collection){
        if(err) console.log(err);
        collection.find(function(err, cursor){
            if(err) console.log(err);
            res.send(html);
        });
    })
});

db.open(function(err){
    if(err) console.log(err);

    if(user && pass) {
        db.authenticate(user, pass, function(err) {
            app.listen(8080);
        });
    }
    else {
        app.listen(8080);
    }
});