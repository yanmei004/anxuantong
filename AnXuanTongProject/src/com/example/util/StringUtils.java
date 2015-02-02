package com.example.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工�?
 * @author 黄荣�?
 * @time 2013-3-5
 */
public class StringUtils {

	private static StringUtils instance;

	private StringUtils() {
		super();
	}

	public static synchronized StringUtils getInstance() {
		if (instance == null)
			instance = new StringUtils();
		return instance;
	}
	/*
	 * 时间格式转换，yyyy-MM-dd xx:xx:xx为：yyyy-MM-dd
	 */
	public static String getStringDate(String date) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss");
		Date d = new Date();
		try {
			d = formatter.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dateString = formatter.format(d);
		return dateString;

	}
	/**
	 * 判断是否为空
	 * @param text
	 * @return
	 */
	public static boolean isNullOrEmpty(String text) {
		if (text == null || "".equals(text.trim()) || text.trim().length() == 0 || "null".equals(text.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否为�?
	 */
	public static boolean isEmpty(String str) {
		return null == str || str.trim().equals("");
	}

	/**
	 * 获取字串字节长度
	 * @param str
	 * @return
	 */
	public static int getLength(String str) {
		return str.replaceAll("[^\\x00-\\xff]", "**").trim().length();
	}

	/**
	 * �?��是否JSON字串
	 * @param source
	 * @return
	 */
	public static boolean isJson(String source) {
		String tmp = null == source ? "" : source.trim();
		if ("".equals(tmp) || tmp.length() < 1)
			return false;
		else if (tmp.startsWith("{") && tmp.endsWith("}"))
			return true;
		else if (tmp.startsWith("[") && tmp.endsWith("]"))
			return true;
		else
			return false;
	}

	public static String str2int(String source) {
		String result = "";
		for (int i = 0; i < source.length(); i++) {
			if (source.charAt(i) >= '0' && source.charAt(i) <= '9')
				result += source.charAt(i);

		}
		return result;
	}

	/**
	 * MD5编码
	 * @param source
	 * @return
	 */
	public static String md5(byte[] source) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			for (int i = 0, k = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * base64编码,将字节数组编码为字符�?
	 * @param source
	 * @return String
	 */
	public static String base64_encode(byte[] source) {
		char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
		try {
			StringBuffer sb = new StringBuffer();
			int len = source.length;
			int i = 0;
			int b1, b2, b3;
			while (i < len) {
				b1 = source[i++] & 0xff;
				if (i == len) {
					sb.append(base64EncodeChars[b1 >>> 2]);
					sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
					sb.append("==");
					break;
				}
				b2 = source[i++] & 0xff;
				if (i == len) {
					sb.append(base64EncodeChars[b1 >>> 2]);
					sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
					sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
					sb.append("=");
					break;
				}
				b3 = source[i++] & 0xff;
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
				sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
				sb.append(base64EncodeChars[b3 & 0x3f]);
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * base64编码,解码为字节数�?
	 * @param source
	 * @return byte[]
	 */
	public static byte[] base64_decode(String source) {
		byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };
		try {
			byte[] data = source.getBytes();
			int len = data.length;
			ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
			int i = 0;
			int b1, b2, b3, b4;
			while (i < len) {
				/* b1 */
				do {
					b1 = base64DecodeChars[data[i++]];
				} while (i < len && b1 == -1);
				if (b1 == -1)
					break;

				/* b2 */
				do {
					b2 = base64DecodeChars[data[i++]];
				} while (i < len && b2 == -1);
				if (b2 == -1)
					break;
				buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

				/* b3 */
				do {
					b3 = data[i++];
					if (b3 == 61)
						return buf.toByteArray();
					b3 = base64DecodeChars[b3];
				} while (i < len && b3 == -1);
				if (b3 == -1)
					break;
				buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

				/* b4 */
				do {
					b4 = data[i++];
					if (b4 == 61)
						return buf.toByteArray();
					b4 = base64DecodeChars[b4];
				} while (i < len && b4 == -1);
				if (b4 == -1)
					break;
				buf.write((int) (((b3 & 0x03) << 6) | b4));
			}
			return buf.toByteArray();
		} catch (Exception e) {
			return "".getBytes();
		}
	}

	public static String join(String separator, Object[] o) {
		return implode(separator, Arrays.asList(o));
	}

	protected static <T> String implode(String separator, Iterable<T> elements) {
		StringBuilder out = new StringBuilder();
		boolean first = true;
		for (Object s : elements) {
			if (s == null)
				continue;
			else if (first)
				first = false;
			else
				out.append(separator);
			out.append(s);
		}
		return out.toString();
	}

	public static String[] explode(String glue, String str) {
		String[] outData;
		try {
			StringTokenizer st = new StringTokenizer(str, glue);
			outData = new String[st.countTokens()];
			for (int i = 0; st.hasMoreTokens();) {
				outData[i++] = st.nextToken();
			}
		} catch (Exception e) {
			outData = new String[] { str };
			e.printStackTrace();
		}
		return outData;
	}

	/**
	 * 得到字符串双字节长度
	 * @param str
	 * @return
	 */
	public static int getDoubleByteLength(String str) {
		return str.replaceAll("[^\\x00-\\xff]", "xx").trim().length();
	}

	/**
	 * 将流转换成字符串
	 * @param is
	 * @return
	 */
	public static String getStringByStream(InputStream is) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 将流转换成字符串
	 * @param is
	 * @param chatset 字符编码
	 * @return
	 */
	public static String getStringByStream(InputStream is, String chatset) {
		StringBuffer sb = new StringBuffer();
		try {
			byte[] bytes = new byte[1024];
			int len = 0;
			while ((len = is.read(bytes)) != -1) {
				sb.append(new String(bytes, 0, len, chatset));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * method desc：手机号码验�?移动�?34�?35�?36�?37�?38�?39�?50�?51�?57(TD)�?58�?59�?87�?88 联�?�?30�?31�?32�?52�?55�?56�?85�?86 电信�?33�?53�?80�?89、（1349卫�?�?
	 * @param mobiles 手机号码
	 * @return true验证通过，false 不�?�?
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(145)|(147)|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 判断是否为数�?
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

}