var mysql = require('mysql'); 
var TEST_DATABASE = 'nodejs_mysql_test'; 
var TEST_TABLE = 'test'; 
//创建连接 
var client = mysql.createConnection({ 
  user: 'root', 
  password: '999', 
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
              console.log("%d\t%s\t%s", results[i].id, results[i].title, results[i].created, results[i].text);
          }
      }   
    client.end(); 
  } 
);