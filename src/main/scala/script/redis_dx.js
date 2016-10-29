var redis = require("redis"),
client = redis.createClient();
  
client.on("error", function (err) {
  console.log("Error " + err);
});
  
client.on("connect", runSample);
  
function runSample() {
	
  client.expire('string key', 3);

  // Set a value
  client.set("string key", "Hello World", function (err, reply) {
    console.log(reply.toString() + "123");
  });
  // Get a value
  client.get("string key", function (err, reply) {
    console.log(reply.toString());
  });
  
  client.rpush("links", "links1", function (err, reply) {
	console.log("==========rpush========="+reply.toString()+"===================");
  });
  client.lindex("links", 0,function (err, reply) {
	console.log("==========lindex========"+reply.toString()+"===================");
  });
}

