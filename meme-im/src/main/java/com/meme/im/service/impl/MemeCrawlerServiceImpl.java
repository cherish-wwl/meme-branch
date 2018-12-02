package com.meme.im.service.impl;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.meme.core.base.AbstractService;
import com.meme.core.cache.ParamsCache;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;
import com.meme.im.dao.MemeCrawlerMapper;
import com.meme.im.pojo.MemeCrawler;
import com.meme.im.service.MemeCrawlerService;
import com.meme.qiniu.util.QiniuAPI;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;

@Service("MemeCrawlerDataService")
public class MemeCrawlerServiceImpl extends AbstractService implements MemeCrawlerService {

	@Resource private MemeCrawlerMapper mapper;
	
	public static boolean flag = false;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeCrawler record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemeCrawler record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemeCrawler selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeCrawler record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeCrawler record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeCrawler> selectAll(){
		return (List<MemeCrawler>) MapperHelper.toBeanList(this.mapper.selectAll(MemeCrawler.class), MemeCrawler.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeCrawler> selectByPagination(Form form) {
		this.buildSearchCriterion(MemeCrawler.class, form);
		this.buildOrderByCriterion(MemeCrawler.class, form);
		this.buildLimitCriterion(MemeCrawler.class, form);
		return (List<MemeCrawler>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeCrawler.class,this.getList()), MemeCrawler.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeCrawler.class, form);
		return this.mapper.count(MemeCrawler.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeCrawler> selectByEntity(MemeCrawler record) {
		return (List<MemeCrawler>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeCrawler.class);
	}

	@Override
	public int batchInsert(List<MemeCrawler> record) {
		return this.mapper.batchInsert(record,MemeCrawler.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		Form form = new Form();
		this.buildSearchCriterion(MemeCrawler.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("id").operator(Operator.IN).leftValue(record).javaType(String.class).build());
		this.getList().addAll(criterions);
		List<MemeCrawler> list = (List<MemeCrawler>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeCrawler.class,this.getList()), MemeCrawler.class);
		List<String> arrayList = new ArrayList<String>();
		for(MemeCrawler m:list) {
			if(!StringUtils.isEmpty(m.getCover())) {
				String[] split = m.getCover().split(";");
				for(String str:split) {
					arrayList.add(str.replace("http://ex.mmgmmj.com/", ""));
				}
			}
			if(!StringUtils.isEmpty(m.getFile())) {
				arrayList.add(m.getFile().replace("http://ex.mmgmmj.com/", ""));
			}
		}
		if(arrayList.size()>0) {
			BucketManager bucketManager = QiniuAPI.getBucketManager(Zone.autoZone());
			String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
			BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
		    batchOperations.addDeleteOp(bucket, arrayList.toArray(new String[0]));
			try {
				Response response = bucketManager.batch(batchOperations);
			    System.out.println(response.toString());
			} catch (QiniuException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return this.mapper.batchDelete(record,MemeCrawler.class);
	}

	@Override
	public int batchUpdate(List<MemeCrawler> record) {
		return this.mapper.batchUpdate(record,MemeCrawler.class);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeCrawler> selectByPagination(HttpServletRequest request, Form form) {
		String file_type=request.getParameter("file_type");
		String source=request.getParameter("source");
		String type=request.getParameter("type");
		this.buildSearchCriterion(MemeCrawler.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(file_type)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("file_type").operator(Operator.EQUAL).leftValue(file_type).javaType(Integer.class).build());
		}
		if(StringUtil.isAllNotEmpty(source)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("source").operator(Operator.EQUAL).leftValue(source).javaType(Integer.class).build());
		}
		if(StringUtil.isAllNotEmpty(type)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("type").operator(Operator.EQUAL).leftValue(type).javaType(Integer.class).build());
		}
		this.getList().addAll(criterions);
		if(StringUtil.isEmpty(form.getSort())) {
			form.setSort("insert_date");
			form.setOrder("desc");
		}
		this.buildOrderByCriterion(MemeCrawler.class, form);
		this.buildLimitCriterion(MemeCrawler.class, form);
		return (List<MemeCrawler>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeCrawler.class,this.getList()), MemeCrawler.class);
	}

	@Override
	public int count(HttpServletRequest request, Form form) {
		String file_type=request.getParameter("file_type");
		String source=request.getParameter("source");
		String type=request.getParameter("type");
		this.buildSearchCriterion(MemeCrawler.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(file_type)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("file_type").operator(Operator.EQUAL).leftValue(file_type).javaType(Integer.class).build());
		}
		if(StringUtil.isAllNotEmpty(source)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("source").operator(Operator.EQUAL).leftValue(source).javaType(Integer.class).build());
		}
		if(StringUtil.isAllNotEmpty(type)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("type").operator(Operator.EQUAL).leftValue(type).javaType(Integer.class).build());
		}
		this.getList().addAll(criterions);
		return this.mapper.count(MemeCrawler.class,this.getList());
	}

	@Override
	public void aTask() throws Exception{
	//public static void main(String[] args) throws Exception{
		flag = true;
		System.out.println("执行任务");
		String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
		ResultMessage result=QiniuAPI.getBucketDomains(bucket);
		String[] domains=(String[]) result.getData();
		String domain=null;
		if(null!=domains&&domains.length>0) domain=domains[0];
		String osname = System.getProperties().getProperty("os.name");
		DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();
		if (osname.equals("Mac OS X")) {//判断系统的环境
			desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"/Users/guoyanjian/app/phantomjs-2.1.1-macosx/bin/phantomjs");
		} else {
			desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"/usr/local/phantomjs-2.1.1-linux-x86_64/bin/phantomjs");
		}
		//ssl证书支持
		desiredCapabilities.setCapability("acceptSslCerts", true);
		 //css搜索支持
		desiredCapabilities.setCapability("cssSelectorsEnabled", true);
        //js支持
        desiredCapabilities.setJavascriptEnabled(true);
        desiredCapabilities.setCapability("takesScreenshot",false);
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
					 MemeCrawler memeCrawler = new MemeCrawler();
					 memeCrawler.setId(String.valueOf(IDGenerator.generateId()));
					 memeCrawler.setTitle(title);
					 memeCrawler.setFile_original_link(file_original_link);
					 memeCrawler.setFile_type(1);
					 memeCrawler.setSource(1);
					 memeCrawler.setType(1);
					 String key = uploadFile(file, bucket);
					 memeCrawler.setFile("http://"+domain+"/"+key);
					 memeCrawler.setCover("http://"+domain+"/"+key);
					 mapper.insertSelective(memeCrawler);
				 }catch(Exception ex) {
					 ex.printStackTrace();
				 }
	          }
		} catch (Exception e) {
			e.printStackTrace();
		}
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://www.qiushibaike.com/media/");
        Thread.sleep(1000L);
        for(int i=0;i<=4;i++) {
        	 	try {
        	 		  Document document = Jsoup.parse(driver.getPageSource());
		          Elements elements = document.getElementsByTag("li");
		          for(Element e:elements) {
		        	  	for(Element el:e.getElementsByTag("a")) {
					 try {
						 MemeCrawler memeCrawler = new MemeCrawler();
						 String title = el.getElementsByClass("imagesText").text();
						 String file_original_link = "https://www.qiushibaike.com"+el.attr("href");
						 System.out.println(title);
						 System.out.println(file_original_link);
						 memeCrawler.setId(String.valueOf(IDGenerator.generateId()));
						 memeCrawler.setTitle(title);
						 memeCrawler.setFile_original_link(file_original_link);
						 memeCrawler.setFile_type(3);
						 memeCrawler.setSource(2);
						 memeCrawler.setType(1);
						 String cover = "https:"+el.getElementsByClass("imagesDiv").attr("data-src");
						 String key = uploadFile(cover, bucket);
						 memeCrawler.setCover("http://"+domain+"/"+key);
						 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				         driver.get(file_original_link);
				         Thread.sleep(1000L);
				         String file = "https:"+driver.findElementById("videoBox").getAttribute("data-video");
				         System.out.println(file);
				         String filekey = uploadFile(file, bucket);
				         memeCrawler.setFile("http://"+domain+"/"+filekey);
						 mapper.insertSelective(memeCrawler);
					 }catch(Exception ex) {
						 ex.printStackTrace();
					 }
		        	  	}
		          }
		          driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		          driver.get("https://www.qiushibaike.com/imgrank/page/"+(i+2));
		          Thread.sleep(1000L);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        toutiao(driver, "shipin", 3, bucket, domain);
        toutiao(driver, "bagua", 4, bucket, domain);
        toutiao(driver, "shehui", 5, bucket, domain);
        toutiao(driver, "jiankang", 6, bucket, domain);
        toutiao(driver, "xiaohua", 7, bucket, domain);
        toutiao(driver, "lishi", 8, bucket, domain);
       tenxun(driver, "https://info.3g.qq.com/g/channel_home.htm?chId=ent_h_nch", 9, bucket, domain);
        tenxun(driver, "https://info.3g.qq.com/g/channel_home.htm?chId=society_h_nch", 10, bucket, domain);
        tenxun(driver, "https://info.3g.qq.com/g/channel_home.htm?chId=joke_h_nch", 12, bucket, domain);
        flag = false;
        driver.close();
        driver.quit();
        System.out.println("爬虫任务执行完毕");
	}
	
	public String uploadFile(String fileurl,String bucket) throws Exception{
		String hash=UUID.randomUUID().toString().replaceAll("-", "");
		 String suffix=fileurl.substring(fileurl.lastIndexOf(".")+1,fileurl.length());
		 UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
		 String upToken = QiniuAPI.getUploadToken(bucket,null);
		 String key="crawler/"+hash+"."+suffix;
		 URL url = new URL(fileurl);
		 URLConnection urlConnection = url.openConnection();
		 InputStream inputStream = urlConnection.getInputStream();
		 uploadManager.put(inputStream, key, upToken, null, null);
		 return key;
	}
	
	public void toutiao(PhantomJSDriver driver,String column,Integer source,String bucket,String domain) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("http://toutiao.eastday.com/");
    	 	try {
    	 		Thread.sleep(1000);
//    	 		WebDriverWait wait = new WebDriverWait(driver, 10);
//    	 		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@data-type='shipin']")));//开始打开网页，等待输入元素出现
//    	 		System.out.println(driver.getPageSource());
    	 		 WebElement el = driver.findElement(By.xpath("//a[@data-type='"+column+"']"));
    	         el.click();
    	         Thread.sleep(1000);
    	         Actions action = new Actions(driver);
    	  		 action.moveToElement(driver.findElement(By.id("J_loading"))).build().perform();
    	          Thread.sleep(1000);
    	 		  Document document = Jsoup.parse(driver.getPageSource());
	          Element element = document.getElementById("J_news_list");
	          Elements children = element.getElementsByTag("section");
	          for(Element e:children) {
				 try {
					 String file_original_link = e.child(0).attr("href");
					 String title = e.getElementsByTag("h3").text();
					 StringBuffer buf = new StringBuffer();
					 Elements imgtags = e.getElementsByTag("img");
					 for(Element tag:imgtags) {
						 String key = uploadFile(tag.attr("src"), bucket);
						 buf.append("http://"+domain+"/"+key).append(";");
					 }
					 if(buf.length()>0) {
						 buf = buf.delete(buf.length()-1, buf.length());
					 }
					 System.out.println(title);
					 System.out.println(file_original_link);
					 System.out.println(buf.toString());
					 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			         driver.get(file_original_link);
			         Thread.sleep(1000L);
			         Document doc = Jsoup.parse(driver.getPageSource());
					 MemeCrawler memeCrawler = new MemeCrawler();
					 memeCrawler.setId(String.valueOf(IDGenerator.generateId()));
					 if(e.hasClass("news-item-s1") || e.hasClass("news-item-s2")) {
						 memeCrawler.setFile_type(4);
				         Element ele = doc.getElementById("content");
				         Elements children2 = ele.children();
				         StringBuffer content = new StringBuffer();
				         for(Element child:children2) {
				        	 	if(child.hasClass("txt")) {
				        	 		content.append(child.text());
				        	 	}
				        	 	if(child.hasClass("img")) {
				        	 		content.append(child.child(0).html());
				        	 	}
				         }
				         System.out.println(content.toString());
				         memeCrawler.setContent(content.toString());
					 }
					 if(e.hasClass("news-video")) {
						 memeCrawler.setFile_type(3);
						 String file = "https:"+doc.getElementsByTag("video").attr("src");
						 String key = uploadFile(file, bucket);
						 memeCrawler.setFile("http://"+domain+"/"+key);
					 }
					 memeCrawler.setSource(source);
					 memeCrawler.setTitle(title);
					 memeCrawler.setFile_original_link(file_original_link);
					 memeCrawler.setType(1);
					 memeCrawler.setCover(buf.toString());
					 mapper.insertSelective(memeCrawler);
				 }catch(Exception ex) {
					 ex.printStackTrace();
				 }
	          }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tenxun(PhantomJSDriver driver,String url,Integer source,String bucket,String domain) throws Exception{
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(url);
        Thread.sleep(2000L);
        try {
	 		  Document document = Jsoup.parse(driver.getPageSource());
		        Elements children = document.getElementsByClass("lincoapp-graphic-list2 panel-section");
		        System.out.println(children.size());
		        for(Element e:children) {
					 try {
						 String file_original_link = "https:"+e.child(0).attr("href");
						 String title = e.getElementsByTag("strong").text();
						 System.out.println(title);
						 System.out.println(file_original_link);
						 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				         driver.get(file_original_link);
				         Thread.sleep(2000L);
				         Document doc = Jsoup.parse(driver.getPageSource());
				         Element ele = doc.getElementsByTag("article").get(0);
				         Elements children2 = ele.children();
				         StringBuffer buf = new StringBuffer();
				         StringBuffer content = new StringBuffer();
				         for(Element child:children2) {
				        	 	if(child.hasClass("art-img")) {
				        	 		Elements imgtag = child.getElementsByTag("img");
				        	 		String src = imgtag.attr("src");
				        	 		System.out.println(src);
				        	 		if(src.startsWith("http")) {
					        	 		String key = uploadFile(imgtag.attr("src"), bucket);
									buf.append("http://"+domain+"/"+key).append(";");
					        	 		content.append(imgtag.toString());
				        	 		}
				        	 	}
				        	 	if(child.tagName().equals("p")) {
				        	 		content.append(child.toString());
				        	 	}
				         }
				         Elements catmore = ele.getElementsByClass("catmore");
				         if(catmore!=null && catmore.size()>0) {
				        	 	Elements children3 = catmore.get(0).children();
				        	 	for(Element child:children3) {
					        	 	if(child.hasClass("art-img")) {
					        	 		Elements imgtag = child.getElementsByTag("img");
					        	 		String src = imgtag.attr("src");
					        	 		System.out.println(src);
					        	 		if(src.startsWith("http")) {
						        	 		String key = uploadFile(imgtag.attr("src"), bucket);
										buf.append("http://"+domain+"/"+key).append(";");
						        	 		content.append(imgtag.toString());
					        	 		}
					        	 	}
					        	 	if(child.tagName().equals("p")) {
					        	 		content.append(child.toString());
					        	 	}
					         }
				         }
				         if(buf.length()>0) {
					         buf = buf.delete(buf.length()-1, buf.length());
				         }
				         System.out.println(buf.toString());
				         System.out.println(content.toString());
				         MemeCrawler memeCrawler = new MemeCrawler();
						 memeCrawler.setId(String.valueOf(IDGenerator.generateId()));
						 memeCrawler.setTitle(title);
						 memeCrawler.setCover(buf.toString());
						 memeCrawler.setFile_original_link(file_original_link);
						 memeCrawler.setFile_type(4);
						 memeCrawler.setSource(source);
						 memeCrawler.setType(1);
						 memeCrawler.setContent(content.toString());
						 mapper.insertSelective(memeCrawler);
					 }catch(Exception ex) {
						 ex.printStackTrace();
					 }
		        		}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
	}
	
	
}
