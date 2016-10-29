var mysql = require('mysql'); 
//创建连接 
var client = mysql.createConnection({ 
  user: 'root', 
  password: '999', 
});

//要创建的数据库名
TEST_DATABASE = 'nodejs_mysql_test',
 //要创建的表名
TEST_TABLE = 'test';

client.query('CREATE DATABASE '+TEST_DATABASE, function(err) {
 if (err && err.number != Client.ERROR_DB_CREATE_EXISTS) {
 throw err;
 }
 });

// If no callback is provided, any errors will be emitted as `'error'`
 // events by the client
 client.query('USE '+TEST_DATABASE);
 client.query(
'CREATE TABLE '+TEST_TABLE+
'(id INT(11) AUTO_INCREMENT, '+
'title VARCHAR(255), '+
'text TEXT, '+
'created DATETIME, '+
'PRIMARY KEY (id))'
 );

client.query(
'INSERT INTO '+TEST_TABLE+' '+
'SET title = ?, text = ?, created = ?',
 ['super cool', 'this is a nice text', '2010-08-16 10:00:23']
 );

var query = client.query(
'INSERT INTO '+TEST_TABLE+' '+
'SET title = ?, text = ?, created = ?',
 ['another entry', 'because 2 entries make a better test', '2010-08-16 12:42:15']
 );

client.query(
'SELECT * FROM '+TEST_TABLE,
 function selectCb(err, results, fields) {
 if (err) {
 throw err;
 }

console.log(results);
 console.log(fields);
 client.end();
 }
 );
 
 /*
 var express = require('express');
var mysql = require('mysql');
var app = express();
app.use(function(req, res, next){
  console.log('%s %s', req.method, req.url);
  next();
});
var conn = mysql.createConnection({
    host:'localhost',
    user:'root',
    database:'ceshi',
    password:'123456',
    port:3306
});
conn.connect();
app.get('/', function(req, res){
   conn.query('SELECT * from ceshibiao', function(err, rows, fields) {
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
 */
 
 /*
 var mysql = require('mysql'); 
var TEST_DATABASE = 'my_news_test'; 
var TEST_TABLE = 'node_user'; 
//创建连接 
var client = mysql.createConnection({ 
  user: 'root', 
  password: 'root123', 
}); 
client.connect();
client.query("use " + TEST_DATABASE);
client.query( 
  'SELECT * FROM '+TEST_TABLE, 
  function selectCb(err, results, fields) { 
    if (err) { 
      throw err; 
    } 
       if(results)
      {
          for(var i = 0; i < results.length; i++)
          {
              console.log("%d\t%s\t%s", results[i].id, results[i].name, results[i].age);
          }
      }   
    client.end(); 
  } 
);
 */
