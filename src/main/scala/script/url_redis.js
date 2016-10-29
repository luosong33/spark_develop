var casper = require('casper').create(),
phantom = require('phantom').create();
// redis
//var redis = require("redis").create();
//var client = redis.createClient();


function getLinks() {	
	var lis = document.getElementsByTagName("li");
        
	var urls = [];
	var $lis = $(lis);
			
	for (var i = 0;i<$lis.length;i++){
		var url = $($lis[i]).find("a:last").attr("href");

		urls.push(url);
	}
	console.log(urls);
    return urls;
}
phantom.outputEncoding="GBK";
casper.start('http://www.weather.com.cn/alarm/warninglist.shtml');

var cars=new Array();
casper.then(function() {
	
    var links = this.evaluate(getLinks);
	for (var i in links){
		console.log(links[i]+"======="+i);
		//client.rpush("links", links[i], function (err, reply) {
		//	console.log("==========rpush========="+reply.toString()+"===================");
		//});
		//client.lindex("links", i,function (err, reply) {
		//	console.log("==================="+reply.toString()+"==========lindex=========");
		//});
	}
		
});

casper.run(function() {
	casper.done();
});




