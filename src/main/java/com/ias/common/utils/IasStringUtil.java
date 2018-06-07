package com.ias.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Random;
import java.util.zip.CRC32;

import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2016/5/13 0013.
 */
public class IasStringUtil {

    public static final String UTF8 = "UTF-8";
    public static final Charset CHARSET_UTF8 = Charset.forName(UTF8);

    private static final char[] toHexTable = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'E', 'F'};

    /**
     * 每位允许的字符
     */
    public static final String POSSIBLE_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f'};

    private static final String OS_WIN = "Windows";

    public static boolean isWin() {
        String osName = System.getProperties().getProperty("os.name");
        return osName.contains(OS_WIN);
    }

    public static boolean isPhone(String phoneStr) {
        if (StringUtils.isEmpty(phoneStr) || phoneStr.length() != 11 || !phoneStr.startsWith("1")) {
            return false;
        }
        try {
            Long.parseLong(phoneStr);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static final String random(int length) {
        char[] rs = new char[length];
        for (int i = 0; i < rs.length; i++) {
            rs[i] = hexDigits[(int) (Math.random() * 16)];
        }
        return new String(rs).toLowerCase();
    }

    private static String toHex(byte[] bs) {
        int j = bs.length;
        char rs[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = bs[i];
            rs[k++] = hexDigits[byte0 >>> 4 & 0xf];
            rs[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(rs);
    }

    public static String sign4bytes(String v, byte[] privateKey) {
        CRC32 c = new CRC32();
        c.update(v.getBytes(CHARSET_UTF8));
        c.update(privateKey);
        byte[] bs = new byte[4];
        char[] cs = new char[8];
        intToBytes((int) c.getValue(), bs);
        toHex(bs, cs);
        return String.valueOf(cs);
    }

    public static void toHex(byte[] in, char[] out) {
        assert in != null && in.length != 0 && out != null && out.length == in.length * 2;
        for (int i = 0; i < in.length; i++) {
            out[i * 2] = toHexTable[(in[i] & 0xF0) >> 4];
            out[i * 2 + 1] = toHexTable[in[i] & 0xF];
        }
    }

    public static void intToBytes(int in, byte[] out) {
        assert out != null && out.length == 4;
        out[0] = (byte) (in >>> 24);
        out[1] = (byte) (in >> 16);
        out[2] = (byte) (in >> 8);
        out[3] = (byte) in;
    }

    public final static String md5(byte[] bs) {
        try {
            MessageDigest o = MessageDigest.getInstance("MD5");
            o.update(bs);
            byte[] r = o.digest();
            return toHex(r);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public final static String sha1(byte[] bs) {
        try {
            MessageDigest o = MessageDigest.getInstance("SHA1");
            o.update(bs);
            byte[] r = o.digest();
            return toHex(r);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 处理要保护的数字
     *
     * @param number      需要处理的数字
     * @param prefixIndex 从前数第几个开始保护 从1开始
     * @param suffixIndex 从后数第几个开始保护
     * @return
     */
    public static String procHideNum(String number, int prefixIndex, int suffixIndex) {
        if (StringUtils.hasText(number)) {
            char[] cs = number.toCharArray();
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < cs.length; i++) {
                if ((i >= 0 && i <= prefixIndex - 1) || (i >= cs.length - suffixIndex && i <= cs.length - 1)) {
                    b.append(cs[i]);
                } else {
                    b.append("*");
                }
            }
            number = b.toString();
        }
        return number;
    }

    // GENERAL_PUNCTUATION 判断中文的“号
    // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
    // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
    private static final boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static final boolean isChinese(String strName) {
        if (StringUtils.isEmpty(strName))
            return false;
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isChineseCharacter(String chineseStr) {
        if (StringUtils.isEmpty(chineseStr))
            return false;
        char[] charArray = chineseStr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @deprecated; 弃用。和方法isChineseCharacter比效率太低。
     */
    public static final boolean isChineseCharacter_f2() {
        String str = "！？";
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i, i + 1).matches("[\\u4e00-\\u9fbb]+")) {
                return true;
            }
        }
        return false;
    }

    public static final int generateRandomInt(int start, int end) {
        Random random = new Random();
        return random.nextInt(end) % (end - start + 1) + start;
    }

    /**
     * 获取输入流的MD5
     *
     * @param is
     * @return
     */
    public static final String getMD5Checksum(InputStream is) {
        byte[] b = new byte[0];
        try {
            b = createChecksum(is);
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < b.length; i++) {
                result.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));//加0x100是因为有的b[i]的十六进制只有1位
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] createChecksum(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        MessageDigest complete = null; //如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
        try {
            complete = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int numRead;
        do {
            numRead = is.read(buffer);    //从文件读到buffer，最多装满buffer
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);  //用读到的字节进行MD5的计算，第二个参数是偏移量
            }
        } while (numRead != -1);

        //is.close();
        return complete.digest();
    }

    /**
     * 获取输入流的MD5
     *
     * @return
     */
    public static final String getMD5Checksum(byte[] bytes) {
        try {
            byte[] b = createChecksum(bytes);
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < b.length; i++) {
                result.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));//加0x100是因为有的b[i]的十六进制只有1位
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] createChecksum(byte[] bytes) throws IOException {
        MessageDigest complete = null; //如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
        try {
            complete = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        complete.update(bytes);  //用读到的字节进行MD5的计算

        return complete.digest();
    }

    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isEmpty(source)) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                // do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }

        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {

        if (!containsEmoji(source)) {
            return source;// 如果不包含，直接返回
        }
        // 到这里铁定包含
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            } else {
            }
        }

        if (buf == null) {
            return source;// 如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }

    }

    @SuppressWarnings("unchecked")
	public static <T> String join(T... elements) {
        return join(elements, null);
    }

    public static String join(Object[] array, char separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(long[] array, char separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(int[] array, char separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(short[] array, char separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(byte[] array, char separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(char[] array, char separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(float[] array, char separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(double[] array, char separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    if (array[i] != null) {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(long[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(int[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(byte[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(short[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(char[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(double[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(float[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(Object[] array, String separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            if (separator == null) {
                separator = "";
            }

            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    if (array[i] != null) {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String obj2Str(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static String join(Iterator<?> iterator, char separator) {
        if (iterator == null) {
            return null;
        } else if (!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if (!iterator.hasNext()) {
                String buf1 = obj2Str(first);
                return buf1;
            } else {
                StringBuilder buf = new StringBuilder(256);
                if (first != null) {
                    buf.append(first);
                }

                while (iterator.hasNext()) {
                    buf.append(separator);
                    Object obj = iterator.next();
                    if (obj != null) {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(Iterator<?> iterator, String separator) {
        if (iterator == null) {
            return null;
        } else if (!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if (!iterator.hasNext()) {
                String buf1 = obj2Str(first);
                return buf1;
            } else {
                StringBuilder buf = new StringBuilder(256);
                if (first != null) {
                    buf.append(first);
                }

                while (iterator.hasNext()) {
                    if (separator != null) {
                        buf.append(separator);
                    }

                    Object obj = iterator.next();
                    if (obj != null) {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(Iterable<?> iterable, char separator) {
        return iterable == null ? null : join(iterable.iterator(), separator);
    }

    public static String join(Iterable<?> iterable, String separator) {
        return iterable == null ? null : join(iterable.iterator(), separator);
    }

    public static final String getSaltedPwd(String pwd, String salt) {
        return sha1((pwd + salt).getBytes());
    }

    public static <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
        return StringUtils.isEmpty(str) ? defaultStr : str;
    }

    public static <T extends Object> T defaultIfNull(T t, T defaultValue) {
        return t == null ? defaultValue : t;
    }

    public static Integer[] str2IntArr(String str, String separator) {
        String[] strArr = str.split(separator);
        Integer[] numbers = new Integer[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            // Note that this is assuming valid input
            // If you want to check then add a try/catch
            // and another index for the numbers if to continue adding the others
            numbers[i] = Integer.parseInt(strArr[i]);
        }
        return numbers;
    }

}
