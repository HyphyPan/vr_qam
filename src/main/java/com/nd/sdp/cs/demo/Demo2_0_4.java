package com.nd.sdp.cs.demo;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.HttpPatch;

import com.alibaba.fastjson.JSONObject;
import com.nd.gaea.client.http.WafSecurityHttpClient;
import com.nd.gaea.client.support.WafUcServerConfig;
import com.nd.sdp.cs.common.CsConfig;
import com.nd.sdp.cs.sdk.Capacity;
import com.nd.sdp.cs.sdk.Dentry;
import com.nd.sdp.cs.sdk.ExtendUploadData;
import com.nd.sdp.cs.sdk.InputStreamObject;
import com.nd.sdp.cs.sdk.PackProcess;
import com.nd.sdp.cs.sdk.Recycle;

/**
 * 0.1.8版本demo
 * @author Administrator
 *
 */
@SuppressWarnings("deprecation")
public class Demo2_0_4 {

	static String serviceId = "a6dea973-25c6-4d17-9dd2-353ebf6c863c";
	static String serviceName = "preproduction_content_sdktest";
	
	public static void main(String[] args) {
		CsConfig.setHost("sdpcs.beta.web.sdp.101.com");
		try {
			uploadFile();  //文件上传
			coverUploadFile();  //文件覆盖上传
			uploadFileByte();  //byte方式文件上传
			quickUpload();//秒传(本地文件)
			quickUploadByMd5();//秒传(md5)
			download(); //一般下载
			resource(); //path方式下载
			create(); //添加目录项
			edit();//修改目录项
			move();//移动目录项
			multiMove();//批量移动目录项
			delete();//单个删除目录项(通过dentryId删除)
			deleteByPath();//单个删除目录项(通过path删除)
			multidelete();//批量删除目录项
			get();//单个获取目录项信息
			muliGet();//批量获取目录项信息
			addHits();//单个增加目录项访问次数
			addMultiHits();//批量增加目录项访问次数
			getCapacity();//获取目录项容量信息
			list();//获取目录项列表
			shareAndUnShare();//分享目录项,取消分享目录项,查看分享的目录项,验证分享的目录项密码
			packProcess();//异步打包,获取打包进度
			recycleList();//获取回收站目录项列表
			recycleRestore();//批量还原回收站的目录项
			recycleDelete();//批量删除回收站的目录项
			recycleFlush();//清空回收站
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static  Dentry uploadFile() throws Exception{		
		Dentry dentry = new Dentry();			
		dentry.setPath("/" + serviceName + "/test/");//path
		dentry.setName("testName.jpg");//name	
		String fileName = getFileName();
		String session = getSession();
		Dentry resDentryh = dentry.upload(serviceName,fileName, null, session,null);
		return resDentryh;
	}
	
	public static  Dentry coverUploadFile() throws Exception{	
		ExtendUploadData eud = new ExtendUploadData();
		eud.setFilePath("/" + serviceName + "/test/testName.jpg");
		Dentry dentry = new Dentry();			
//		dentry.setName("testName.jpg");//name	
		String fileName = getFileName();
		String session = getSession();
		Dentry resDentryh = dentry.upload(serviceName,fileName, eud, session,null);
		System.out.println("resDentryh="+JSONObject.toJSONString(resDentryh));
		return resDentryh;
	}
	
	
	public static  void uploadFileByte() throws Exception{		
		Dentry dentry = new Dentry();			
		dentry.setPath("/" + serviceName + "/test/");//path
		String fileName = getFileName();
		String session = getSession();		
		File file = new File(fileName);
		byte[] bytes = FileUtils.readFileToByteArray(file);
		Dentry resDentryh = dentry.upload(serviceName,bytes, null, session,null);
		System.out.println(JSONObject.toJSONString(resDentryh));
	}
	
	public static void quickUpload() throws Exception{
		try {
			Dentry dentry = new Dentry();			
			dentry.setPath("/" + serviceName + "/quick/");
			dentry.setName("quickName.jpg");
			String fileName = getFileName();
			Integer expireDays = null;
			String session = getSession();
			Dentry resDentryh = dentry.quickUpload(serviceName,fileName, expireDays, session);
			System.out.println(JSONObject.toJSONString(resDentryh));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void quickUploadByMd5() throws Exception{
		try {			
			Dentry fileDentry = uploadFile();			
			Dentry dentry = new Dentry();			
			dentry.setPath("/" + serviceName + "/quick/");
			dentry.setName("quickName.jpg");
			Integer expireDays = null;
			String session = getSession();
			Dentry resDentryh = dentry.quickUploadByMd5(serviceName,fileDentry.getInode().getMd5(), expireDays, session);
			System.out.println(JSONObject.toJSONString(resDentryh));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void download() throws Exception{
		InputStreamObject isObject = null;
		try {
			Dentry fileDentry = uploadFile();
			String session = getSession();
			String path = null;
			Integer size = null; //size不为空时是下载缩略图：缩略图短边尺寸（像素）,可选，仅支持80,120,160,240,320,480,640,960
			String ext = null;
			
			isObject = Dentry.download(serviceName,fileDentry.getDentryId(), path, size, ext, session);
			//TODO 使用流
			InputStream is = isObject.getInputStream();
			System.out.println(is);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(isObject != null){
				isObject.close();
			}
		}
	}
	
	public static void resource() throws Exception{
		InputStreamObject isObject = null;
		try {
			Dentry fileDentry = uploadFile();
			String session = getSession();
			Integer size = null; //size不为空时是下载缩略图：缩略图短边尺寸（像素）,可选，仅支持80,120,160,240,320,480,640,960
			String ext = null;
			isObject = Dentry.resource(serviceName,fileDentry.getPath(), size,ext, session);
			InputStream is = isObject.getInputStream();
			System.out.println(is);		
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(isObject != null){
				isObject.close();
			}
		}
	}
	
	public static void create() throws Exception{
		try {
			Dentry dentry = new Dentry();			
			dentry.setPath("/"+serviceName+"/test");
			dentry.setName("testFloder");
			Integer capacity = null;
			Integer expireDays = null;
			String session = getSession();
			Dentry resDentryh = dentry.create(serviceName,capacity, expireDays, session);
			System.out.println(JSONObject.toJSONString(resDentryh));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void edit() throws Exception{
		try {
			Dentry dentryFolder = createFolder();
			String otherNameModify = "testFloderModify";
			
			Dentry dentry = new Dentry();	
			dentry.setDentryId(dentryFolder.getDentryId());
			dentry.setOtherName(otherNameModify);
			String session = getSession();
			Dentry resDentryh = dentry.edit(serviceName,session);
			System.out.println(JSONObject.toJSONString(resDentryh));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void move() throws Exception{
		try {			
			Dentry dentryFolder1 = createFolder();
			Dentry dentryFolder2 = createFolder();
			String session = getSession();
			Dentry.move(serviceName,dentryFolder1.getDentryId()
					,dentryFolder2.getDentryId(),session);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public static void multiMove() throws Exception{
		try {
			
			Dentry dentryFolder1 = createFolder();
			Dentry dentryFolder2 = createFolder();
			Dentry dentryFolder3 = createFolder();
			
			List<String> dentrysId = new ArrayList<String>();
			dentrysId.add(dentryFolder2.getDentryId());
			dentrysId.add(dentryFolder3.getDentryId());
			
			String session = getSession();
			Dentry.multiMove(serviceName,dentryFolder1.getParentId()
					,dentrysId,dentryFolder1.getDentryId(),session);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void delete() throws Exception{
		try {
			Dentry fileDentry = uploadFile();
			String session = getSession();
			Dentry.delete(serviceName,fileDentry.getDentryId(), session);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void deleteByPath() throws Exception{
		try {
			Dentry fileDentry = uploadFile();
			String session = getSession();
			Dentry.deleteByPath(serviceName,fileDentry.getPath(), session);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void multidelete() throws Exception{
		try {
			Dentry fileDentry1 = uploadFile();
			Dentry fileDentry2 = uploadFile();
			List<String> dentryIds = new ArrayList<String>();
			dentryIds.add(fileDentry1.getDentryId());
			dentryIds.add(fileDentry2.getDentryId());			
			String session = getSession();
			Dentry.multidelete(serviceName,fileDentry1.getParentId(),dentryIds, session);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void get() throws Exception{
		try {
			Dentry dentryFolder = createFolder();
			String session = getSession();
			Dentry resDentry = Dentry.get(serviceName,dentryFolder.getDentryId(), session);			
			System.out.println(JSONObject.toJSONString(resDentry));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void muliGet() throws Exception{
		try {			
			Dentry dentryFolder1 = createFolder();
			Dentry dentryFolder2 = createFolder();
			List<String> dentrysId = new ArrayList<String>();
			dentrysId.add(dentryFolder1.getDentryId());
			dentrysId.add(dentryFolder2.getDentryId());			
			List<String> paths = null;
			String orderby = null;
			String session = getSession();
			List<Dentry> resDentry = Dentry.muliGet(serviceName,dentrysId,paths,orderby, session);		
			System.out.println(JSONObject.toJSONString(resDentry));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void addHits() throws Exception{
		Dentry fileDentry = uploadFile();
		Dentry.addHits(serviceName,fileDentry.getDentryId(), 1);
	}
	
	public static void addMultiHits() throws Exception{
		try {
			Dentry fileDentry1 = uploadFile();
			Dentry fileDentry2 = uploadFile();
			List<String> dentryIds = new ArrayList<String>();
			dentryIds.add(fileDentry1.getDentryId());
			dentryIds.add(fileDentry2.getDentryId());
			List<Integer> hits = new ArrayList<Integer>();
			hits.add(2);
			hits.add(2);
			Dentry.addMultiHits(serviceName,dentryIds, hits);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void getCapacity() throws Exception{
		try {			
			Dentry dentryFolder = createFolder();
			String session = getSession();
			Capacity resMap = Dentry.getCapacity(serviceName,dentryFolder.getDentryId(), session);
			System.out.println(JSONObject.toJSONString(resMap));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void list() throws Exception{
		try {
			Dentry fileDentry1 = uploadFile();
			String path = null;
			String session = getSession();
			List<Dentry> list = Dentry.list(serviceName,path, fileDentry1.getParentId(), "createAt gt 0", "createAt desc", 10, session);
			System.out.println(JSONObject.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void shareAndUnShare() throws Exception{
		try {
			String password = "123456654xx";
			Dentry fileDentry1 = uploadFile();
			String session = getSession();
			Dentry.share(serviceName,fileDentry1.getDentryId(),password, session);
			Map<String,String> res = Dentry.checkPassword(serviceName,fileDentry1.getDentryId(), password);
			Dentry resDentry = Dentry.getShare(serviceName,fileDentry1.getDentryId());
			System.out.println(JSONObject.toJSONString(resDentry));
			Dentry.unshare(serviceName,fileDentry1.getDentryId(), session);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void packProcess() throws Exception{
		try {
			Dentry fileDentry1 = uploadFile();
			Map<String,String> dentryIds =  new HashMap<String,String>();
			dentryIds.put(fileDentry1.getDentryId(), "/");
			Dentry dentry = new Dentry();			
			dentry.setPath("/"+serviceName+"/pack");
			dentry.setName("test.zip");
			dentry.setScope(1);
			String session = getSession();
			boolean rename = false;
			String filePath = null;
			Map<String,String> paths = null;
			Integer expireDays = null;
			Dentry resDentryh = dentry.asynPack(serviceName,rename, filePath, dentryIds, paths, expireDays, session);
			System.out.println(JSONObject.toJSONString(resDentryh));
			PackProcess proc = Dentry.getPackProcess(serviceName,resDentryh.getDentryId(), null, session);
			System.out.println(JSONObject.toJSONString(proc));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void recycleList() throws Exception{
		try {
			Dentry fileDentry1 = uploadFile();
			Dentry fileDentry2 = uploadFile();
			String session = getSession();
			Dentry.delete(serviceName,fileDentry1.getDentryId(), session);
			Dentry.delete(serviceName,fileDentry2.getDentryId(), session);
			List<Dentry> list = Recycle.list(serviceName,"updateAt gt 0", "updateAt   desc", 200, session);
			System.out.println(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void recycleRestore() throws Exception{
		try {
			Dentry fileDentry1 = uploadFile();	
			String session = getSession();
			Dentry.delete(serviceName,fileDentry1.getDentryId(), session);
			
			List<String> dentryIds = new ArrayList<String>();
			dentryIds.add(fileDentry1.getDentryId());
			Recycle.restore(serviceName,dentryIds, session);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void recycleDelete() throws Exception{
		try {
			Dentry fileDentry1 = uploadFile();		
			Dentry fileDentry2 = createFolder();
			String session = getSession();
			Dentry.delete(serviceName,fileDentry1.getDentryId(), session);
			Dentry.delete(serviceName,fileDentry2.getDentryId(), session);
			List<String> dentryIds = new ArrayList<String>();
			dentryIds.add(fileDentry1.getDentryId());
			dentryIds.add(fileDentry2.getDentryId());
			Recycle.delete(serviceName,dentryIds, session);
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true,false);
		}
	}
	
	public static void recycleFlush() throws Exception{
		try {
			Dentry fileDentry1 = uploadFile();		
			Dentry fileDentry2 = uploadFile();
			String session = getSession();
			Dentry.delete(serviceName,fileDentry1.getDentryId(), session);
			Dentry.delete(serviceName,fileDentry2.getDentryId(), session);
			Recycle.flush(serviceName,session);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String getFileName(){
		String classDir = Demo2_0_4.class.getResource("/").getPath();
		return classDir+"com/nd/sdp/cs/file/test.jpg";
	}
	
	private static Dentry createFolder() throws Exception{
		Dentry dentry = new Dentry();			
		dentry.setPath("/"+serviceName+"/test");
		dentry.setName("testFloder");
		Integer capacity = null;
		Integer expireDays = null;
		String session = getSession();
		Dentry resDentryh = dentry.create(serviceName,capacity, expireDays, session);
		return resDentryh;
		
		
	}
	
	private static String session = null;
	private static long expireAt = 0;
	private static String getSession(){
		if(session == null){
			Map sessionMap = requestSession();
			session = (String)sessionMap.get("session");
			expireAt = (Long)sessionMap.get("expire_at");
			return session;
		}else{
			if(expireAt < System.currentTimeMillis()){
				Map sessionMap = requestSession();
				session = (String)sessionMap.get("session");
				expireAt = (Long)sessionMap.get("expire_at");
				return session;
			}
		}
		return session;
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	private static Map requestSession(){
		WafUcServerConfig wafUcServerConfig = new WafUcServerConfig();
//      wafUcServerConfig.setUC_API_DOMAIN("http://101uccenter-pressure.web.sdp.101.com/");
//		wafUcServerConfig.setUC_API_DOMAIN("http://101uccenter.dev.web.nd/");
//		wafUcServerConfig.setUC_API_DOMAIN("http://101uccenter.qa.web.sdp.101.com/");
		wafUcServerConfig.setUC_API_DOMAIN("http://101uccenter.beta.web.sdp.101.com/");//		
		wafUcServerConfig.setUC_API_VERSION("v0.93");
		wafUcServerConfig.setWafUcServerConfig();
		
		HttpPatch httpPath = null;
		
		WafSecurityHttpClient httpClient = new WafSecurityHttpClient();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("path", "/"+serviceName); //必须以 "/"+服务名称作为起始路径(例如：申请的服务名称为:example,path的开头为"/example");
		param.put("uid", "859008"); //用户uid
		param.put("role", "admin"); //取值仅限字符串"user"、"admin"(user：只能管理授权的路径下自己的目录项,admin：可以管理授权的路径下全部的目录项)。 
		param.put("service_id", serviceId);
		param.put("expires", 5*60);//  session过期时间，单位秒
		String url = CsConfig.getHostUrl()+"/sessions";
		try {
			Map sessionMap = httpClient.post(url, param, Map.class);
			System.out.println("session="+sessionMap);
			return sessionMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
