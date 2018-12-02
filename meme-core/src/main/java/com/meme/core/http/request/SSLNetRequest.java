package com.meme.core.http.request;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.meme.core.http.HttpsTrustManager;

/**
 * https请求类
 * 
 * @author hzl
 * 
 */
public class SSLNetRequest extends AbstractNetRequest {

	@Override
	public HttpURLConnection getConnection() {
		HttpsURLConnection con = null;
		try {
			SSLContext ctx = this.getSSLContext();
			con = (HttpsURLConnection) new URL(this.getEntity().getUrl()).openConnection();
			con.setSSLSocketFactory(ctx.getSocketFactory());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 初始化SSL上下文
	 * 
	 * @return
	 */
	public SSLContext getSSLContext() {
		SSLContext sslContext = null;
		try {
			if (this.getEntity().isSslBinary()) {
				KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
				KeyStore keyStore = this.getKeyStore();
				keyManagerFactory.init(keyStore, this.getEntity().getKeyPassword().toCharArray());
				sslContext = SSLContext.getInstance("TLS");
				sslContext.init(keyManagerFactory.getKeyManagers(), null,new SecureRandom());
			} else {
				sslContext = SSLContext.getInstance("TLS");
				sslContext.init(new KeyManager[0],new TrustManager[] { new HttpsTrustManager() },new SecureRandom());
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return sslContext;
	}

	/**
	 * 获取KeyStore
	 * 
	 * @return
	 */
	public KeyStore getKeyStore() {
		KeyStore keyStore = null;
		FileInputStream in = null;
		try {
			keyStore = KeyStore.getInstance(this.getEntity().getKeyStoreType().getKeyStoreType());
			in = new FileInputStream(this.getEntity().getKeyStorePath());
			keyStore.load(in, this.getEntity().getKeyPassword().toCharArray());
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return keyStore;
	}
}
