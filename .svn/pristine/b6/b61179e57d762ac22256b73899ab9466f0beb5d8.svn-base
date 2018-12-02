package com.meme.core.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * Bean工具类
 * 
 * @author hzl
 *
 */
public class BeanUtil {

	/**
	 * map转xml字符串
	 * @param map
	 * @param root
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String map2Xml(Map map,String root){
		StringBuffer xml=new StringBuffer();
		xml.append("<").append(root).append(">");
		if(null!=map&&map.size()>0){
			Iterator it=map.keySet().iterator();
			while(it.hasNext()){
				Object key=it.next();
				Object value=map.get(key);
				xml.append("<").append(key.toString()).append(">");
				xml.append(value.toString());
				xml.append("</").append(key.toString()).append(">");
			}
		}
		xml.append("</").append(root).append(">");
		return xml.toString();
	}
	/**
	 * xml字符串转成map
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,String> xml2Map(String xml){
		Map<String,String> xmlMap=new LinkedHashMap<String, String>();
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			List elements = root.elements();
			Iterator it = elements.iterator();
			while (it.hasNext()) {
				Element node = (Element) it.next();
				String name = node.getName();
				String value = node.getText();
				xmlMap.put(name, value);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return xmlMap;
	}
	/**
	 * xml输入流转map，key为节点名
	 * @param xml
	 * @param in
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,String> xml2Map(InputStream in){
		Map<String,String> xmlMap=new LinkedHashMap<String, String>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document w3cdoc = builder.parse(in);
			// 将w3c的Document转换为Dom4J的Document
			DOMReader domReader = new DOMReader();
			Document document = domReader.read((org.w3c.dom.Document) w3cdoc);

			Element root = document.getRootElement();
			List elements = root.elements();
			Iterator it = elements.iterator();
			while (it.hasNext()) {
				Element node = (Element) it.next();
				String name = node.getName();
				String value = node.getText();
				xmlMap.put(name, value);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xmlMap;
	}
	/**
	 * xml字符串转实体
	 * 
	 * @param clazz
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object xml2Bean(Class<?> clazz, String xml) {
		Object object = null;
		try {
			object = clazz.newInstance();
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			List elements = root.elements();
			Iterator it = elements.iterator();
			while (it.hasNext()) {
				Element node = (Element) it.next();
				String name = node.getName();
				String value = node.getText();
				PropertyDescriptor prop = new PropertyDescriptor(name,clazz);
				prop.getWriteMethod().invoke(object, value);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * xml输入流转实体类
	 * 
	 * @param clazz
	 * @param in
	 * @param cdata
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object xml2Bean(Class<?> clazz, InputStream in) {
		Object object = null;
		try {
			object = clazz.newInstance();
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document w3cdoc = builder.parse(in);
			// 将w3c的Document转换为Dom4J的Document
			DOMReader domReader = new DOMReader();
			Document document = domReader.read((org.w3c.dom.Document) w3cdoc);

			Element root = document.getRootElement();
			List elements = root.elements();
			Iterator it = elements.iterator();
			while (it.hasNext()) {
				Element node = (Element) it.next();
				String name = node.getName();
				String value = node.getText();
				PropertyDescriptor prop = new PropertyDescriptor(name, clazz);
				prop.getWriteMethod().invoke(object, value);
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * 实体转map
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map bean2Map(Object obj){
		Map<String,Object> map=new HashMap<String, Object>();
		PropertyUtilsBean propertyUtilsBean=new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
        for (int i = 0; i < descriptors.length; i++) {
            String name = descriptors[i].getName();
            if (!"class".equals(name)) {
                try {
					map.put(name, propertyUtilsBean.getNestedProperty(obj, name));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
            }
        }
        return map;
	}
	
	/**
	 * 实体类转xml字符串,默认xml根节点为xml，所有数据加cdata标记
	 * 
	 * @param bean
	 * @return
	 */
	public static String bean2Xml(Object bean) {
		return bean2Xml(bean, "xml", true);
	}

	/**
	 * 实体类转xml字符串
	 * 
	 * @param bean
	 * @param rootNode
	 *            xml根节点，null值时使用bean实体类名
	 * @param cdata
	 *            数据是否加cdata标记
	 * @return
	 */
	public static String bean2Xml(Object bean, String rootNode, boolean cdata) {
		XStream xStream = null;
		if (cdata) {
			xStream = new XStream(new XppDriver(new NoNameCoder()) {
				@Override
				public HierarchicalStreamWriter createWriter(Writer out) {
					return new PrettyPrintWriter(out) {

						@Override
						@SuppressWarnings("rawtypes")
						public void startNode(String name, Class clazz) {
							super.startNode(name, clazz);
						}

						@Override
						public String encodeNode(String name) {
							return name;
						}

						@Override
						protected void writeText(QuickWriter writer, String text) {
							writer.write("<![CDATA[");
							writer.write(text);
							writer.write("]]>");
						}
					};
				}
			});
		} else {
			xStream = new XStream(new XppDriver(new NoNameCoder()) {
				@Override
				public HierarchicalStreamWriter createWriter(Writer out) {
					return new PrettyPrintWriter(out) {

						@Override
						@SuppressWarnings("rawtypes")
						public void startNode(String name, Class clazz) {
							super.startNode(name, clazz);
						}

						@Override
						public String encodeNode(String name) {
							return name;
						}

						@Override
						protected void writeText(QuickWriter writer, String text) {
							writer.write(text);
						}
					};
				}
			});
		}
		if (StringUtil.isAllNotEmpty(rootNode)) {
			xStream.alias(rootNode, bean.getClass());
		}

		return xStream.toXML(bean);
	}
}
