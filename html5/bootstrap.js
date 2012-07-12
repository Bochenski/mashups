var connect = require('connect');
connect.createServer(
    connect.static('bootstrap')
).listen(8080);