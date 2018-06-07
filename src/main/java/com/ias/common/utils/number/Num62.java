package com.ias.common.utils.number;

import com.ias.common.utils.string.StringUtil;

public class Num62 {
	
	/**
	 * 62个字母和数字，含大小写
	 */
	public static final char[] N62_CHARS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };
	/**
	 * 36个小写字母和数字
	 */
	public static final char[] N36_CHARS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };
	/**
	 * 长整型用N36表示的最大长度
	 */
	public static final int LONG_N36_LEN = 13;
	/**
	 * 长整型用N62表示的最大长度
	 */
	public static final int LONG_N62_LEN = 11;
	
	
	/**
	 * 长整型转换成字符串
	 * 
	 * @param l
	 * @param chars
	 * @return
	 */
	private static StringBuilder longToNBuf(long l, char[] chars) {
		int upgrade = chars.length;
		StringBuilder result = new StringBuilder();
		int last;
		while (l > 0) {
			last = (int) (l % upgrade);
			result.append(chars[last]);
			l /= upgrade;
		}
		return result;
	}

	/**
	 * 长整数转换成N62
	 * 
	 * @param l
	 * @return
	 */
	public static String longToN62(long l) {
		return longToNBuf(l, N62_CHARS).reverse().toString();
	}

	/**
	 * 长整型转换成N36
	 * 
	 * @param l
	 * @return
	 */
	public static String longToN36(long l) {
		return longToNBuf(l, N36_CHARS).reverse().toString();
	}

	/**
	 * 长整数转换成N62
	 * 
	 * @param l
	 * @param length
	 *            如不足length长度，则补足0。
	 * @return
	 */
	public static String longToN62(long l, int length) {
		StringBuilder sb = longToNBuf(l, N62_CHARS);
		for (int i = sb.length(); i < length; i++) {
			sb.append('0');
		}
		return sb.reverse().toString();
	}

	/**
	 * 长整型转换成N36
	 * 
	 * @param l
	 * @param length
	 *            如不足length长度，则补足0。
	 * @return
	 */
	public static String longToN36(long l, int length) {
		StringBuilder sb = longToNBuf(l, N36_CHARS);
		for (int i = sb.length(); i < length; i++) {
			sb.append('0');
		}
		return sb.reverse().toString();
	}

	/**
	 * N62转换成整数
	 * 
	 * @param n62
	 * @return
	 */
	public static long n62ToLong(String n62) {
		return nToLong(n62, N62_CHARS);
	}

	/**
	 * N36转换成整数
	 * 
	 * @param n36
	 * @return
	 */
	public static long n36ToLong(String n36) {
		return nToLong(n36, N36_CHARS);
	}

	private static long nToLong(String s, char[] chars) {
		char[] nc = s.toCharArray();
		long result = 0;
		long pow = 1;
		for (int i = nc.length - 1; i >= 0; i--, pow *= chars.length) {
			int n = findNIndex(nc[i], chars);
			result += n * pow;
		}
		return result;
	}

	private static int findNIndex(char c, char[] chars) {
		for (int i = 0; i < chars.length; i++) {
			if (c == chars[i]) {
				return i;
			}
		}
		throw new RuntimeException("N62(N36)非法字符：" + c);
	}

	/** 
	 * 将字节数组转化为16进制
	 * @author: jiuzhou.hu
	 * @date:2017年3月29日下午2:52:25 
	 * @param bytes
	 * @return
	 */
	public static String bytes2hex03(byte[] bytes) {  
        final String HEX = "0123456789abcdef";  
        StringBuilder sb = new StringBuilder(bytes.length * 2);  
        for (byte b : bytes) {  
            // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数  
            sb.append(HEX.charAt((b >> 4) & 0x0f));  
            // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数  
            sb.append(HEX.charAt(b & 0x0f));  
        }  
  
        return sb.toString();  
    }
	

	/** 
	 * 十六进制转八进制
	 * @author: jiuzhou.hu
	 * @date:2017年5月2日下午10:20:18 
	 * @param numb
	 * @return
	 */
	public static String C16T8(String numb) {
		int temp3 = Integer.parseInt(numb, 16);
		return C10T8(Integer.toString(temp3));
	}
	
	/** 
	 * 八进制转十六进制
	 * @author: jiuzhou.hu
	 * @date:2017年5月2日下午10:21:40 
	 * @param numb
	 * @return
	 */
	public static String C8T16(String numb) {
		return Integer.toHexString(Integer.valueOf(C8T10(numb))).toUpperCase();
	}
	
	/** 
	 * 十六进制转二进制
	 * @author: jiuzhou.hu
	 * @date:2017年5月2日下午10:19:53 
	 * @param numb
	 * @return
	 */
	public static String C16T2(String numb) {
		int temp2 = Integer.parseInt(numb, 16);
		return Integer.toBinaryString(temp2);
	}
	
	/** 
	 * 二进制转十六进制
	 * @author: jiuzhou.hu
	 * @date:2017年5月2日下午10:17:00 
	 * @param numb
	 * @return
	 */
	public static String C2T16(String numb) {
		return Integer.toHexString(Integer.valueOf(C2T10(numb))).toUpperCase();
	}
	
	/** 
	 * 二进制转八进制
	 * @author: jiuzhou.hu
	 * @date:2017年5月2日下午10:18:25 
	 * @param numb
	 * @return
	 */
	public static String C2T8(String numb) {
		return C10T8(Integer.toString(C2T10(numb)));
	}
	
	/** 
	 * 八进制转二进制
	 * @author: jiuzhou.hu
	 * @date:2017年5月2日下午10:19:17 
	 * @param numb
	 * @return
	 */
	public static String C8T2(String numb) {
		return C10T2(Integer.toString(C8T10(numb)));
	}
	
	/** 
	 * 十进制转十六进制
	 * @author: jiuzhou.hu
	 * @date:2017年5月2日下午10:15:37 
	 * @param numb
	 * @return
	 */
	public static String C10T16(int numb) {
		return Integer.toHexString(numb).toUpperCase();
	}
	
	/** 
	 * 十六进制转十进制
	 * @author: jiuzhou.hu
	 * @date:2017年5月2日下午10:16:14 
	 * @param numb
	 * @return
	 */
	public static int C16T10(String numb) {
		return Integer.parseInt(numb, 16);
	}

	/** 
	 * 二进制转十进制
	 * @author: jiuzhou.hu
	 * @date:2017年5月2日下午10:14:16 
	 * @param numb
	 * @return
	 */
	public static int C2T10(String numb) {
		int k = 0, result = 0;
		// String result=null;
		for (int i = Integer.valueOf(numb); i > 0; i /= 10) {
			result += (i % 10) * Math.pow(2, k);
			k++;
		}
		return result;
	}

	/** 
	 * 八进制转十进制
	 * @author: jiuzhou.hu
	 * @date:2017年5月2日下午10:14:07 
	 * @param numb
	 * @return
	 */
	public static int C8T10(String numb) {
		int k = 0, temp = 0;
		for (int i = Integer.valueOf(numb); i > 0; i /= 10) {
			temp += (i % 10) * Math.pow(8, k);
			k++;
		}
		return temp;
	}

	/** 
	 * 十进制转八进制
	 * @author: jiuzhou.hu
	 * @date:2017年5月2日下午10:13:58 
	 * @param numb
	 * @return
	 */
	public static String C10T8(String numb) {
		String result = "";
		for (int i = Integer.valueOf(numb); i > 0; i /= 8)
			result = i % 8 + result;
		return result;
	}

	/** 
	 * 十进制转二进制
	 * @author: jiuzhou.hu
	 * @date:2017年5月2日下午10:13:21 
	 * @param numb
	 * @return
	 */
	public static String C10T2(String numb) {
		String result = "";
		for (int i = Integer.valueOf(numb); i > 0; i /= 2)
			result = i % 2 + result;
		return result;
	}
	
	public static String IpTo16(String ip) {
		StringBuffer sf = new StringBuffer();
		for(String s:ip.split("\\.")) {
			sf.append(StringUtil.addCharForString(C10T16(Integer.valueOf(s)), 2, '0', 1));
		}
		return sf.toString();
	}
	
	public static Long ip2long(String ip) {  
        String[] ips = ip.split("[.]");  
        Long num =  16777216L*Long.parseLong(ips[0]) + 65536L*Long.parseLong(ips[1]) + 256*Long.parseLong(ips[2]) + Long.parseLong(ips[3]);  
        return num;  
    } 

	public static void main(String[] args) {
		/*String numb = "192";
		System.out.println("10>>>2 = " + C10T2(numb));
		System.out.println("2>>>10 = " + C2T10(numb));
		System.out.println("10>>>8 = " + C10T8(numb));
		System.out.println("8>>>10 = " + C8T10(numb));
		System.out.println("10>>>16 = " + C10T16(Integer.valueOf(numb)));
		System.out.println("16>>>10 = " + C16T10(numb));
		System.out.println("2>>>8 = " + C2T8(numb));
		System.out.println("2>>>16 = " + C2T16(numb));
		System.out.println("8>>>2 = " + C8T2(numb));
		System.out.println("8>>>16 = " + C8T16(numb));
		System.out.println("16>>>2 = " + C16T2(numb));
		System.out.println("16>>>8 = " + C16T8(numb));
		
		System.out.println(longToN62(Long.MAX_VALUE));
		System.out.println(n36ToLong("29"));*/
		Long l = Long.parseLong(IpTo16("192.168.1.1"), 16);
		System.out.println(l.intValue());
		System.out.println(Long.parseLong(IpTo16("192.168.1.1"), 16));
		System.out.println(ip2long("192.168.1.1"));
	}
}
