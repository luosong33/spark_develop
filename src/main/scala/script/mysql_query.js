var express = require('express');
var mysql = require('mysql');
var conn = mysql.createConnection({
    host:'localhost',
    user:'root',
    database:'nodejs_mysql_test',
    password:'999',
    port:3306
});

var app = express();
app.use(function(req, res, next){
  console.log('%s %s', req.method, req.url);
  next();
});


conn.connect();
app.get('/', function(req, res){
   conn.query('SELECT * from test', function(err, rows, fields) {
        if(err) throw err;
        var data = '';
        foreach(rows,function(key,value){
            data += '<p>' + 'contents：' + value.contents + '</p>';
            data += '<hr>';
        }
        res.send(data);
    });
});
app.listen(81);
