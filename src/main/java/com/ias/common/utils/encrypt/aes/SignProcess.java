package com.ias.common.utils.encrypt.aes;

import com.ias.common.utils.encrypt.aes.AesAuthUtil;
import com.ias.common.utils.encrypt.aes.EncryptUtil;

/**
 * 签名处理
 * @author Justin Hu
 *
 */
public class SignProcess {
	
	/**
	 * 获取单个对象值
	 * @param obj
	 * @return
	 */
	private static String queryObject(Object obj){
		if(obj instanceof Number ){
			return ((Number)obj).toString();
		}
		else if(obj instanceof String){
			
			return (String)obj;
		}
		
		return null;
	}
	
	/**
	 * 获取多个对象串起来的字符串
	 * @param objects
	 * @return
	 */
	public static String queryObjects(Object ... objects){
		StringBuffer sb=new StringBuffer();
		for(Object obj:objects){
			sb.append(queryObject(obj));
		}
		
		return sb.toString();
	}
	
	/**
	 * objects中的参数只能是基本对象，如String,Integer等
	 * 生成签名
	 * @param objects 参数列表
	 * @return
	 */
	public static String makeSign(Object[] objects,String secret){
		
		String value=queryObjects(objects);
		
		return EncryptUtil.getInstance().hash(value, secret);
	}
	
	/**
	 * 验证签名是否正确
	 * @param sign 签名
	 * @param objects 
	 * @return
	 */
	public static boolean checkSign(String sign,Object[] objects,String secret){
		
		String value=queryObjects(objects);
		String signNow=EncryptUtil.getInstance().hash(value, secret);
		
		if(signNow.equals(sign)){
			return true;
		}
		else{
			return false;
		}
	}
	public static void main(String[] args) {
	    String secret = "12345678"; //私钥
	    String json = "{\"equipmentsId\":\"e88bd5b2c20245a9930401088117a0ad\",\"repTypeId\":\"af9607ccf2b740beac2b0916944d1037\",\"descs\":\"灯坏了，请尽快出来，谢谢！\",\"attach\":{\"audio\":\"1c8a65be55264c939e783e5a92ed8da8\",\"video\":\"f69f509fbbbf4cde895ef5a7e319c299\",\"photo\":\"5f0ed1c82123423794a23a3068678906\"},\"lavel\":1}";
	    String encrypt = AesAuthUtil.encrypt(json); //获得加密串
	    System.out.println(encrypt);
	    String sign = makeSign(new Object[]{encrypt}, secret); //获得签名
	    System.out.println(sign);
	    System.out.println(checkSign(sign,new Object[]{encrypt},secret));//验证签名
        
	    /*System.out.println(sign);
        Long l=222l;
        Integer i=33322;
        Object[] objects={"Rno992Npk/L2g0lNRSOUsXPf+Vf4ogsqi3fbYrUjgrvRisQtFtwv/LkOP4pXf4UVHJzaDyhsjEAsE83gOtaUQNezQTYrSetsfRHrrrDQ6ljQN7lJFDO0ArWER8cM3PRPMLXoA7CvtI6KeTtiqY9ZVok4cEXuk5LCopsUJIEjgpBDpLCIK22RamvJHB0fTtztbByy+b8YG0KsqXZpFg4fASnGvn1HatKzaTgL2Y1HmSr1FWuCjgo9VUY8nDfXohso1E/cu4JyqyNGvneZ2oZ21cVWMp2PpFAY3kaT3V7/8jnhdJQhDlFZSIq/JMd15C1BQkCGnxtkBmOGGuMo9f5uoC1ckHjFP5Nw/pfmR4OuolyR/vpBB7kAuU1PBR+IVSioNI9za5zNvzH25cAwW50U8A=="};
        System.out.println(makeSign(objects, "12345678"));
        System.out.println(checkSign("n2s1CoxXQCZHwlPWq3mXVizVvrHilUSD0MJFcQbxMVBQdWdpRJIzqMzVvWhWF8ST0AAFPzybZ79As5O7JyFOVA==",objects,"12345678"));
        
        Object[] objects2={l,i,"33"};
        System.out.println(makeSign(objects2, "12345678"));*/
    }
}
