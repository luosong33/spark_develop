var casper = require('casper').create({
  clientScripts: ["jquery.js"],
  verbose: false,
  logLevel: 'debug',
  pageSettings: {
    loadImages: false, // The WebPage instance used by Casper will
    loadPlugins: false // use these settings
  }
});<br><br>phantom.outputEncoding = "gbk";//解决乱码问题

/*
获取需要采集的url列表
*/
casper.start(url, function() {
  casper.GetDetailUrl(url);
});
 
/*
打开具体url
*/
casper.GetDetailUrl = function(detailUrl) {
　　casper.thenOpen(detailUrl, function() {
　　console.log(this.getCurrentUrl());
});
 
};

/*
处理当前页面的所有sku价格与信息
*/
casper.then(function getPic() {
 
  // console.log(this.getHTML());
 
  // fs.write('123', this.getHTML(), 'w');
 
  product = casper.evaluate(function getProductFromPage() {
    return $('ul[class*="tb-img"]').children().size();
  });
 
  console.log(product);
 
  var str = ''
  for (var i = 1; i <= product; i++) {
    str += casper.getPrice(i) + "|";
  }
 
  var item = new Object();
  item.price = str;
  item.numiid = this.getCurrentUrl();
 
  casper.PostData(item);
 
  // fs.write('myfile.html', str, 'w');
 
  //this.capture("4.png");  
});


/*
获取商品的价格
*/
casper.getPrice = function(index) {
var dd = casper.clickByImg(index);
if (dd == -1) {
return '';
}
 
productPrice = casper.evaluate(function getPriceFromPage() {
return $('.tm-price').first().text().trim();
});
 
return (dd + "_" + productPrice);
 
};
 
/*
点击小图及获取此商品的data-value
*/
casper.clickByImg = function(index) {
 
            var x = require('casper').selectXPath;
// 如果此商品缺货则跳出
var path = '//*[@id="J_DetailMeta"]/div[1]/div[1]/div/div[4]/div/div/dl[1]/dd/ul/li[' + index + ']';
var outOfStock = this.getElementAttribute(x(path), 'class');
if (outOfStock == 'tb-out-of-stock')
return '-1';
 
this.click(x('//*[@id="J_DetailMeta"]/div[1]/div[1]/div/div[4]/div/div/dl[1]/dd/ul/li[' + index + ']/a'));
 
return this.getElementAttribute(x(path), 'data-value'); // "data-value"
};


/*
提交商品价格信息到服务器
*/
casper.PostData = function(item) {
 
  casper.open('http://XXX/UpdateItemsPrice').then(function() {
 
    this.fill("form", {
      'numiid': item.numiid,
      'value': item.price
    }, false);
 
    this.capture('post.png');
    this.click("#btnSave");
 
    this.echo('GOT it1.' + item.numiid);
  });
 
  this.echo('GOT it2.' + item.numiid);
 
  this.wait(2000, function() {
    this.echo("I've waited for a second.");
  });
 
 
}

casper.run();