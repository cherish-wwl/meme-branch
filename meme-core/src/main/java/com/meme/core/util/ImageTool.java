package com.meme.core.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import com.sun.imageio.plugins.jpeg.JPEGImageWriter;


/**
 * 图片处理工具类
 * @author hzl
 *
 */
public class ImageTool {
	
	/**
	 * 返回图像的宽高大小数组
	 * @param file
	 * @return
	 */
	public static Integer[] getImageWH(File file){
		BufferedImage io=null;
		Integer[] wh=new Integer[2];
		try {
			io = ImageIO.read(file);
			int imageWidth=io.getWidth();
			int imageHeight=io.getHeight();
			wh[0]=imageWidth;
			wh[1]=imageHeight;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wh;
	}
	
	/**
	 * 返回图像的宽高大小数组
	 * @param file
	 * @return
	 */
	public static Integer[] getImageWH(byte[] bytes){
		BufferedImage io=null;
		Integer[] wh=new Integer[2];
		try {
			InputStream bais = new ByteArrayInputStream(bytes);
			io = ImageIO.read(bais);
			int imageWidth=io.getWidth();
			int imageHeight=io.getHeight();
			wh[0]=imageWidth;
			wh[1]=imageHeight;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wh;
	}

	/**
	 * 按比例绽放图片
	 * @param path
	 * @param ratio
	 * @param maxWidth
	 * @param maxHeight
	 * @throws Exception
	 */
	public void resize(String path,String ratio,int maxWidth,int maxHeight) throws Exception{
		if(maxWidth<=0||maxHeight<=0){
			throw new Exception("未指定最大宽度和高度限制！");
		}
		if(path==null||path.equals("")){
			throw new Exception("未指定图片文件路径！");
		}
//		System.out.println(path);
		File file=new File(path);
		BufferedImage io=ImageIO.read(file);
		int imageWidth=io.getWidth();
		int imageHeight=io.getHeight();
		if(ratio==null||ratio.equals("")){
			BigDecimal wRatio=new BigDecimal(maxWidth).divide(new BigDecimal(imageWidth),6,BigDecimal.ROUND_DOWN);
			BigDecimal hRatio=new BigDecimal(maxHeight).divide(new BigDecimal(imageHeight),6,BigDecimal.ROUND_DOWN);
			ratio=String.valueOf(wRatio.subtract(hRatio).intValue()>0?wRatio:hRatio);
		}
//		System.out.println(ratio);
		int width=(int) (imageWidth*Double.valueOf(ratio));
		int height=(int) (imageHeight*Double.valueOf(ratio));
//		System.out.println("width:"+ratio+",height:"+ratio);
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(io, 0, 0, width, height, null);
        ImageIO.write(image, path.substring(path.lastIndexOf(".")+1).toLowerCase(), new File(path));
	}
	
	/**
	 * 裁剪图片
	 * @param path
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public void cropImage(String path,int x,int y,int width,int height) throws IOException{
		BufferedImage srcImage=ImageIO.read(new File(path));
		ImageFilter filter=new CropImageFilter(x,y,width,height);
		Image tmp = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(srcImage.getSource(), filter));
        BufferedImage img = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();   
        g.drawImage(tmp, 0, 0, null);
        g.dispose();
        ImageIO.write(img, path.substring(path.lastIndexOf(".")+1).toLowerCase(), new File(path));
	}
	
	/**
	 * 压缩图片
	 * @param fullname，原文件名全称，带路径
	 * @param out，输出流对象
	 * @param scale，缩放比例，0-1之间的值，超过边界默认取1
	 * @param quality，压缩品质，0.1~1.0之间，超出则默认取0.75f
	 * @param width，压缩后宽度
	 * @param height，压缩后高度
	 */
	public static void compress(String fullname,OutputStream out,float scale,float quality,int width,int height){
		try{
			long startTime = System.currentTimeMillis();
			File originFile=new File(fullname);
			Image image = ImageIO.read(originFile);
			int imageWidth = image.getWidth(null);
	        int imageHeight = image.getHeight(null);
	        if(scale>1||scale<=0){
	        	scale=1f;
	        }
	        float realscale = getRatio(imageWidth, imageHeight, width, height);
            float finalScale = Math.min(scale, realscale);// 取压缩比最小的进行压缩
            if(scale==1f){
            	imageWidth = (int) (scale * imageWidth);
                imageHeight = (int) (scale * imageHeight);
            }else{
            	imageWidth = (int) (finalScale * imageWidth);
                imageHeight = (int) (finalScale * imageHeight);
            }
            image = image.getScaledInstance(imageWidth, imageHeight,Image.SCALE_AREA_AVERAGING);
            BufferedImage mBufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = mBufferedImage.createGraphics();
            g2.drawImage(image, 0, 0, imageWidth, imageHeight, Color.white, null);
            g2.dispose();
            float[] kernelData2 = { -0.125f, -0.125f, -0.125f, -0.125f, 2, -0.125f, -0.125f, -0.125f, -0.125f };
            Kernel kernel = new Kernel(3, 3, kernelData2);
            ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            mBufferedImage = cOp.filter(mBufferedImage, null);

            JPEGImageWriter imageWriter=(JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
            ImageOutputStream ios  =  ImageIO.createImageOutputStream(out);
            imageWriter.setOutput(ios);
            IIOMetadata imageMetaData  =  imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(mBufferedImage), null);
            if(quality>1f||quality<0.1f) quality=0.75f;
            JPEGImageWriteParam jpegParams  =  (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(quality);
            imageWriter.write(imageMetaData, new IIOImage(mBufferedImage, null, null), null);
            ios.close();
            imageWriter.dispose();

            //以下为过时API
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(mBufferedImage);
//            if(quality>1f||quality<0.1f) quality=0.75f;
//            param.setQuality(quality, true);
//            encoder.setJPEGEncodeParam(param);
//            encoder.encode(mBufferedImage);
            
            long endTime = System.currentTimeMillis();
            System.out.println("压缩完成，用时：" + (endTime - startTime)+ "ms");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 默认缩放比例1，默认压缩后宽高保持原值
	 * @param fullname
	 * @param out
	 * @param quality
	 */
	public static void compress(String fullname,OutputStream out,float quality){
		compress(fullname,out,1,quality,0,0);
	}
	
	/**
	 * 默认缩放比例1，默认压缩后宽高保持原值，默认压缩品质为0.75
	 * @param fullname
	 * @param out
	 */
	public static void compress(String fullname,OutputStream out){
		compress(fullname,out,1,0.75f,0,0);
	}
	
	public static void compress(File imageFile,OutputStream out){
		compress(imageFile.getAbsolutePath(),out,1,0.75f,0,0);
	}

	/**
	 * 获得压缩比率
	 * @param width
	 * @param height
	 * @param maxWidth
	 * @param maxHeight
	 * @return
	 */
    private static float getRatio(int width, int height, int maxWidth, int maxHeight) {
        float Ratio = 1.0f;
        float widthRatio;
        float heightRatio;
        widthRatio = (float) maxWidth / width;
        heightRatio = (float) maxHeight / height;
        if (widthRatio < 1.0 || heightRatio < 1.0) {
            Ratio = widthRatio <= heightRatio ? widthRatio : heightRatio;
        }
        return Ratio;
    }

    /**
     * 图片格式转换
     * @param imageFile
     * @param imageType
     * @return
     * @throws Exception
     */
    public static byte[] convertImage2Type(String imageFile, String fType) throws Exception {
        File file = new File(imageFile);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedImage input = ImageIO.read(file);
        ImageIO.write(input, fType, out);
        return out.toByteArray();
    }

    /**
     * 图片格式转换
     * @param imageFile
     * @param imageType
     * @throws Exception
     */
    public static void convertImage2TypePng(File imageFile, String fType) throws Exception {
    	if(imageFile.isDirectory()) throw new Exception("文件不正确，请确认文件非目录！");
        String filename = imageFile.getName();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        if (!"png".equals(extension.toLowerCase())) {
            String fullName = imageFile.getParent()+"\\"+filename.substring(0,filename.lastIndexOf(".")) + ".png";
            File desFile = new File(fullName);
            BufferedImage input = ImageIO.read(imageFile);
            ImageIO.write(input, fType, desFile);
        }
    }
	
//	public static void main(String[] args) throws Exception{
//		ImageTool image=new ImageTool();
//		String path="D:\\apache-tomcat-7.0.62\\webapps\\oa\\logoimg\\20150626175022.jpg";
//		System.out.println(path.substring(path.lastIndexOf(".")));
//		image.resize(path, null, 400, 300);
//		image.cropImage(path, 55, 29, 130, 130);
//	}
}
