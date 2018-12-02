package com.meme.pay.alipay.util;

public class AlipayConfig {
	/** 商户appid **/
	public static String APPID = "2017092708950137";
	/** 私钥 pkcs8格式的 **/
	public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCgNIdZDvAU+5vTFeW0XkI4weLczQXsBr9AWV8T7yR/8dsRfcHLhNLLV/bFPPTYgoXVt0Gu8uTY2LLybuwa2js0dHvdsFN9EIiRPaJdGh+9H/IwHxTHBNLJvUF1dAjZzlndpoRy/XcwwTnjKK+Cb/C42me9opMKhk8FHia/QQKrNnjoeahP0n49lxAS4RnqDJVZGDJPE5l9yIhFuvzB6G8RDYIiXiTj7Fc8pLam8brvy5rPx5q/7NSWjwn9P7tqIdZiC/OpZ4Cvt4vR/wAAKcVK+7JYPD4SPvqL1X7VKMseQcVFoog5A0HSD5z82xtN00P/LowYzuDkvdVUwUaPjFjFAgMBAAECggEAd4L4PU3ReDXDJxM8yvNOP30UOleQRWU7I0wIi5xpBDFulXvVKHgrsJosczNoDGRCZw5Qp6x3X/U4eXaaF+YZKKaFCPpTLgc5efAawY2Ng87jI1bsbxOAFBNlU7N+Ls2raJiiUg4X7n0XLAjb9ViAocX8ghFX1BAhvtWhXienUAUeoMMa6PCmWLN5nKzzQbpt44B25gCJoWYVp5gs/uG0tA1kjDfoFBSYvoQWfETdjEQzS9m37Op9qjnEF91S9dJLMJOnCUnsdo8o9CPVmSCcoscOcfH1IixcdfLNw5Cay019xVrfh6YE983Ws9qBCap9Oz+nPsCZC7Hw87zWtstpgQKBgQDu4ctIg48EY6hBzsxAoPqoKvAgI8EcC7VNtwUiAtv2keTXeZcmdUNoJ8LLrz0aschSXmsGqnuvebhDLgofn9iVRKeeKOLggsBmhOWrU50bHq59b9Vrp81e2Yif+0q8VLvi2kHUhEL03gJe2B9IgRjz4iPTAkus0Z3KdBG20DnXMQKBgQCrr2+tWLEFntvD3bBgRbMUQ5TWDgCQz0AWXcCbTGRpDRIwhUtNP4/DosfF9LlsXbbgw9ETWkPWg0tYHfyM1Ff5L2kh5i4SZYTR8Z4c4c0NA/6dXZuORUeRe/OwNGPCFlD2GnFmpZGESndrsKEdKfowv+0lZxwPmPVabarjb1fd1QKBgGyuzCcku9faY331UVaCJyiFyJRG0CArb4R5JVuxA1SuUfh6+Hrl1RI5LNYw11Mi2aooDPeObxGKBnyCINb8ibR39O7vWTGeK105T6jgNNVW4zpsvCPrx3NE83UeBoWi44y1kkfXGQOBUoDGQY1iNvEWzQe+vqFVCT5ICpsrJ3WxAoGBAJlgXXCLb3oJ9WMBxfP70IQl6+1fDPpRbHuOveb/VCTVXI6kRiV4x0KLRdV/T7eT8TDTPxD+7uPQ35qVD23wtbcTt+Rk8nvU3deA9zPuNXWPQAPuAG4VTl6WUZn087oMnb9+2BhPibSZoh4+6XBYc/7hN0bW2qbEi0yB9MzCR8kRAoGBAI3VS+KQGthC6y2DzYKjoY4LeXvmSzd6fNkco/g2uPF/NoxP+VpBUEUaJwuCN5sxQZnbOz2jMvCZkmSmbt0YeZ1XpuXTtFPZCUY+02XqsKIn7kg5aaDJvDWotvJLDmcDw2Nwom8A/lCn9RhFxm5A4jQTIdGqPaFxnDDjmQKy4Ikv";
	/** 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 **/
	public static String notify_url = "http://127.0.0.1:8080/meme-web/pay/alipay/notify_url.jsp";
	/** 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址 **/
	public static String return_url = "http://127.0.0.1:8080/meme-web/pay/alipay/return_url.jsp";
	/** 请求网关地址 **/
	public static String URL = "https://openapi.alipay.com/gateway.do";
	/** 编码 **/
	public static String CHARSET = "UTF-8";
	/** 返回格式 **/
	public static String FORMAT = "json";
	/** 支付宝公钥 **/
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyPxUlU1fMXGy1NbPVgjU6gTT2IoPTXct5XZ21QuiyYqtjsBp72B3BXXWq5zA3GD4saPW6jWDs+1iiesJ3AZBkGCWQvjtpvggpApemsne7a6eYA9xLRhETWFtfTf/LT6dq8vHo3zioBkvMO+LL5MAEORabNO9Bawl2/E921H9b4DGf+ZmFFw/hKcGiNFJYs+SXTCBjNin2P0arhQbs1EL7DLgd+ERD2sSFMLeBfFmHigqcEVNriWavQG9E3b5vcs8fOP+LulDIKL+IIKSpeaPF7hpuQFHI6m2yjB6jCsl0dwrdsd244rr5gUMAnrpPNXzK4OxbPzLh6u36sl6dUsRLwIDAQAB";
	/** 日志记录目录 **/
	public static String log_path = "/log";
	/** RSA2 **/
	public static String SIGNTYPE = "RSA2";
}
