package com.ias.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.junit.Test;

import com.ias.common.utils.date.TimeUtil;
import com.ias.common.utils.encrypt.Base64Util;
import com.ias.common.utils.file.FileUtil;
import com.ias.common.utils.file.ImageUtils;

public class ImageTest {
	
	@Test
    public void gray() {
      ImageUtils.gray("D:\\resources\\my\\demo\\zhimeng\\imgs\\clients\\cor_wicresoft.jpg"
      , "D:\\resources\\my\\demo\\zhimeng\\imgs\\clients\\cor_wicresoft_half.jpg");
    }
	@Test
	public void encode() {
	    String mdStr = Base64Util.encode(FileUtil.readToByte("/Users/jiuzhou.hu/local/resources/tmp/qrcode.png"));
        System.out.println("data:image/png;base64," + mdStr);
	}
	
	@Test
    public void display() throws Exception {
	    Path path = Paths.get("E:/temp/张学友 - 每天爱你多一些 (粤语).mp3");
	    System.out.println(Files.isDirectory(path));  
	    System.out.println(Files.isExecutable(path));// 是否是可执行文件 true  
	    System.out.println(Files.isHidden(path));  
	    System.out.println(Files.isReadable(path));  
	    System.out.println(Files.isWritable(path));  
	    System.out.println(Files.isRegularFile(path));// 是否是常规文件  
	    System.out.println(Files.isSymbolicLink(path));// 是否是符号链接  
	    System.out.println(Files.size(path));//   
	    System.out.println("默认获取的是中心时区（0）的时间："+ Files.getLastModifiedTime(path));// 2016-01-12T05:20:44.466277Z  
	    //格式化时间  
	    Date date = new Date(Files.getLastModifiedTime(path).toMillis());  
	    String time = TimeUtil.toString(date);  
	    System.out.println("使用默认的东八区格式化：" + time);  
	    System.out.println(Files.getOwner(path)); 
    }
	
	@Test
	public void waterMarkTest() {
		Font font = new Font("微软雅黑", Font.ITALIC, 100);                     //水印字体
        String srcImgPath="/Users/jiuzhou.hu/Pictures/123.jpg"; //源图片地址
        String tarImgPath="/Users/jiuzhou.hu/Pictures/123-1.jpg"; //待存储的地址
        String logoText="仅供专用";  //水印内容
        
        ImageUtils.pressText(logoText, srcImgPath, tarImgPath, Color.GRAY, font, 0, 0, 0.5f, -45);
        
	}
	@Test
    public void convertTest() {
	    ImageUtils.convert("F:/TEMP/favicon.ico", "png", "f:/TEMP/logo.png");//测试OK
    }
	
}
