package com.meme.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 文件工具类
 * @author hzl
 *
 */
public class FileTool {

	/**
	 * 保存所有上传文件
	 * @param request
	 * @param saveRelativePath 文件保存的相对路径
	 * @return
	 */
	public static List<UploadFile> saveAllFiles(HttpServletRequest request,String saveRelativePath){
		List<UploadFile> list=new ArrayList<UploadFile>();
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Iterator<String> it = multiRequest.getFileNames();
		while (it.hasNext()) {
			MultipartFile mfile = multiRequest.getFile(it.next());
			if(null!=mfile){
				String fileName = mfile.getOriginalFilename();
				UploadFile uploadFile=new UploadFile();
				uploadFile.setExtension(fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase());
				uploadFile.setPath(saveRelativePath);
				uploadFile.setOldName(fileName);
				String newName = String.valueOf(System.currentTimeMillis())+"."+uploadFile.getExtension();
				uploadFile.setNewName(newName);
				//创建存储文件夹
				String realPath=getRealPath(request, saveRelativePath);
				File f = new File(realPath);
				if (!f.exists() && !f.isDirectory()) {
					f.mkdir();
				}
				try{
					String fileFullName=realPath+"\\"+ newName;
					File localFile = new File(fileFullName);
					mfile.transferTo(localFile);
					uploadFile.setState(true);
				}catch (IOException e) {
					uploadFile.setState(false);
					e.printStackTrace();
				}
				list.add(uploadFile);
			}
		}
		return list;
	}
	
	/**
	 * 返回相对路径在服务器上的绝对路径
	 * @param request
	 * @param relativPath
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request,String relativPath){
		return request.getServletContext().getRealPath(relativPath);
	}
	
	/**
	 * 根据实际路径删除文件
	 * @param realPath
	 * @return
	 */
	public static boolean deleteByRealPath(String realPath){
		File f=new File(realPath);
		if(f.isFile()&&f.exists()) return f.delete();
		return false;
	}
	
	/**
	 * 根据相对路径删除文件
	 * @param request
	 * @param relativPath
	 * @return
	 */
	public static boolean deleteByRelativePath(HttpServletRequest request,String relativPath){
		File f=new File(getRealPath(request, relativPath));
		if(f.isFile()&&f.exists()) return f.delete();
		return false;
	}
	
	/**
	 * http响应输出下载文件流
	 * @param response
	 * @param fileRelativePath 文件绝对路径全称
	 * @throws IOException
	 */
	public static void download(HttpServletRequest request,HttpServletResponse response, String fileRelativePath) throws IOException{
		download(response,getRealPath(request, fileRelativePath));
	}
	
	/**
	 * http响应输出下载文件流
	 * @param response
	 * @param fileRealPath 文件相对路径全称，带文件名
	 * @throws IOException
	 */
	public static void download(HttpServletResponse response, String fileRealPath) throws IOException{
		OutputStream out=null;
		FileInputStream in=null;
		try{
			File file = new File(fileRealPath);
			String fileName = new String(fileRealPath.substring(0,fileRealPath.lastIndexOf(".")).getBytes("UTF-8"),"ISO8859-1");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename="+fileName);
//			String len = String.valueOf(file.length());
//			response.setHeader("Content-Length", len);
			out = response.getOutputStream();
	        in = new FileInputStream(file);
	        byte[] b = new byte[1024];
	        int n;
	        while((n=in.read(b))!=-1){
	            out.write(b, 0, n);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
	        if(null!=in) in.close();
	        if(null!=out) out.close();
		}
	}
}
