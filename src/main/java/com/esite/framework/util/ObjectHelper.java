package com.esite.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

public final class ObjectHelper {
	
	public static <T> T copyProperties(Object srcObject,Class<T> copyToClazz) {
		T origObject = null;
		try {
			origObject = copyToClazz.newInstance();
			BeanUtils.copyProperties(origObject,srcObject);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return origObject;
	}
	
	public static String serialization(Object obj) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			objectOutputStream.flush();
			String result = byteToHexString(byteArrayOutputStream.toByteArray());
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(null != objectOutputStream) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	private final static byte[] HEX = "0123456789ABCDEF".getBytes();
	private static String byteToHexString(byte[] byteArray) {
		byte[] buff = new byte[2 * byteArray.length];
		for(int i = 0;i<byteArray.length;++i) {
			buff[2 * i] = HEX[(byteArray[i] >> 4) & 0x0f];
			buff[2 * i + 1] = HEX[byteArray[i] & 0x0f];
		}
		return new String(buff);
	}
	
	public static Object reSerialization(String hexString) {
		byte[] byteArray = hexStringToByteArray(hexString);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
		ObjectInputStream ois = null;
		Object obj = null;
		try {
			ois = new ObjectInputStream(byteArrayInputStream);
			obj = ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(null != ois) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}
	
	
	private static byte[] hexStringToByteArray(String hexString) {
		byte[] b = new byte[hexString.length() / 2];
		int j = 0;
		for(int i = 0;i<b.length;++i) {
			char c0 = hexString.charAt(j++);
			char c1 = hexString.charAt(j++);
			b[i] = (byte)((parse(c0) << 4 | parse(c1)));
		}
		return b;
	}
	
	private static int parse(char c) {
		if(c >= 'a') {
			return (c - 'a' + 10) & 0x0f;
		}
		if(c >= 'A') {
			return (c - 'A' + 10) & 0x0f;
		}
		return (c - '0') & 0x0f;
	}
}
