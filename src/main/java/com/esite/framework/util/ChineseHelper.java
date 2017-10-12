package com.esite.framework.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class ChineseHelper {

	private static HanyuPinyinOutputFormat FORMAT = new HanyuPinyinOutputFormat();
	static {
		// UPPERCASE：大写  (ZHONG)  
		// LOWERCASE：小写  (zhong)  
		FORMAT.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		
		// WITHOUT_TONE：无音标  (zhong)  
		// WITH_TONE_NUMBER：1-4数字表示英标  (zhong4)  
		// WITH_TONE_MARK：直接用音标符（必须WITH_U_UNICODE否则异常）  (zhòng)  
		FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		
		// WITH_V：用v表示ü  (nv)  
		// WITH_U_AND_COLON：用"u:"表示ü  (nu:)  
		// WITH_U_UNICODE：直接用ü (nü)  
		FORMAT.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
		
	}
	
	public static String convertSpell(String str) throws BadHanyuPinyinOutputFormatCombination {
		if(StringHelper.isEmpty(str)) {
			return "";
		}
		char[] nameArray = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for(char name : nameArray) {
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(name, FORMAT);
			if(null != pinyinArray && pinyinArray.length > 0) {
				sb.append(pinyinArray[0]).append(";");
			}
		}
		if(sb.length() > 0 && sb.charAt(sb.length() - 1) == ';') {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("于" + "->" + ChineseHelper.convertSpell("2424"));
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
	}
	
}
