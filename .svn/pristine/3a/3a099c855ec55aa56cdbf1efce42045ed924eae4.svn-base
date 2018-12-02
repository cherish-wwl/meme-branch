package com.meme.im.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class CrawlerTest {

	public static void main(String[] args) throws Exception{
		DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();
		desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"/Users/guoyanjian/app/phantomjs-2.1.1-macosx/bin/phantomjs");
		//ssl证书支持
		desiredCapabilities.setCapability("acceptSslCerts", true);
		 //css搜索支持
		desiredCapabilities.setCapability("cssSelectorsEnabled", true);
        //js支持
        desiredCapabilities.setJavascriptEnabled(true);
        desiredCapabilities.setCapability("phantomjs.page.settings.userAgent","Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
		PhantomJSDriver driver = new PhantomJSDriver(desiredCapabilities);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		 driver.get("https://www.qiushibaike.com/imgrank/");
		 Thread.sleep(1000L);
	    	 	try {
		          List<WebElement> list = driver.findElementsByTagName("article");
		          for(int i=0;i<10;i++) {
		        	  	  Actions action = new Actions(driver);
		        	  	  action.moveToElement(list.get(list.size()-1)).build().perform();
			          Thread.sleep(1000);
			          list = driver.findElementsByTagName("article");
		          }
		          Document doc = Jsoup.parse(driver.getPageSource());
		          for(Element e:doc.getElementsByTag("article")) {
					 try {
						 String title = null;
						 String file_original_link = null;
						 String file = null;
						 if(e.hasClass("gif")) {
							 Element a = e.getElementsByClass("gifBox").get(0);
							 file_original_link = "https://www.qiushibaike.com"+a.getElementsByTag("a").attr("href");
							 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
						     driver.get(file_original_link);
						     Thread.sleep(1000L);
							 file = driver.findElement(By.className("imageHref")).findElement(By.tagName("img")).getAttribute("src");
						 }else if(e.hasClass("tiezi")){
							 title = e.getElementsByClass("text").text();
							 Element a = e.getElementsByClass("illustration-box").get(0);
							 file_original_link = "https://www.qiushibaike.com"+a.attr("href");
							 file = "https:"+a.getElementsByTag("img").attr("src");
						 }else {
							 continue;
						 }
						 System.out.println(title);
						 System.out.println(file_original_link);
						 System.out.println(file);
					 }catch(Exception ex) {
						 ex.printStackTrace();
					 }
		          }
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
