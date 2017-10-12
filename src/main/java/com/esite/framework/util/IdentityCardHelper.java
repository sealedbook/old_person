package com.esite.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 身份证帮助类
 * 
 * @author Administrator
 * 
 */
public class IdentityCardHelper {

	public static int getSex(String idCard) {
		checkIdCard(idCard);
		int sex = Integer.parseInt(idCard.toCharArray()[16] + "");
		if ((sex % 2) == 0) {
			return 0;
		}
		return 1;
	}

	public static Date getBirthday(String idCard) {
		checkIdCard(idCard);
		String birthdayStr = idCard.substring(6, 14);
		try {
			return new SimpleDateFormat("yyyyMMdd").parse(birthdayStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getAge(String idCard) {
		checkIdCard(idCard);
		Date birthday = getBirthday(idCard);
		Date today = new Date();
		long i = (today.getTime() - birthday.getTime()) / (1000 * 60 * 60 * 24);
		return (int) (i / 365);
	}

	public static void checkIdCard(String idCard) {
		if (!isIdCard(idCard)) {
			throw new IllegalArgumentException("【" + idCard + "】是无效的身份证号.");
		}
		if (idCard.length() == 15) {
			idCard = IdentityCardHelper.get18idCard(idCard);
		}
	}

	public static boolean isIdCard(String idCard) {
		if (null == idCard || idCard.length() <= 0) {
			return false;
		}
		if (idCard.length() != 15 && idCard.length() != 18) {
			return false;
		}
		if(idCard.length() == 15) {
			idCard = get18idCard(idCard);
		}
		return true;
	}

	private static final int[] W = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
			5, 8, 4, 2, 1 };
	private static final String[] A = { "1", "0", "X", "9", "8", "7", "6", "5",
			"4", "3", "2" };

	public static String get18idCard(String idCard15) {
		if (StringHelper.isEmpty(idCard15)) {
			return "";
		}
		if (idCard15.length() == 18) {
			return idCard15;
		}
		try {
			int i, j, s = 0;
			String newid = idCard15;
			newid = newid.substring(0, 6) + "19" + newid.substring(6, 15); // 得17位
			for (i = 0; i < newid.length(); i++) {
				j = Integer.parseInt(newid.substring(i, i + 1)) * W[i];
				s = s + j;
			}
			s = s % 11;
			newid = newid + A[s]; // 取最后一位校验码
			return newid;
		} catch (NumberFormatException e) {
			return idCard15;
		}
	}

}
