package com.meme.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class QrCodeUtil {

	public static byte[] createQrCode(int width,int height,String type,String content){
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		try {
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
	        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			MatrixToImageConfig config=new MatrixToImageConfig();
			MatrixToImageWriter.writeToStream(bitMatrix, type, output, config);
			return output.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}
}
