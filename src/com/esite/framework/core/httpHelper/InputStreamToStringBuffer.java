package com.esite.framework.core.httpHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamToStringBuffer {
	
	private static String CHAR_SET = "UTF-8";
	
	public StringBuffer convert(InputStream inputStream) {
		StringBuffer stringBuffer = new StringBuffer();
		if(null == inputStream) {
			return stringBuffer;
		}
		int bufferSize = 1024;
		byte[] byteArray = new byte[bufferSize];
		try {
			int readLength = -1;
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			while(-1 != (readLength = inputStream.read(byteArray,0,bufferSize))) {
				//stringBuffer.append(new String(byteArray,0,readLength,CHAR_SET));
				outStream.write(byteArray,0,readLength);
			}
			stringBuffer.append(new String(outStream.toByteArray(),CHAR_SET));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringBuffer;
	}
	
}
