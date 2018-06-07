package com.ias.common.utils;

import java.io.File;

import org.junit.Test;

import com.ias.common.utils.file.FileUtil;
import com.ias.common.utils.web.ScriptUtil;

public class ScriptTest {

	@Test
	public void obfuscateFileTest() {
		String filePath = "/Users/jiuzhou.hu/local/project/svn/tools/trunk/project/ias-web-tools/resources/tools/portal/default/dist/scripts/qrcode/qrcode/subnav.js";
		File file = new File(filePath);
		String content = FileUtil.read(file, "UTF-8");
		String encryptStr = ScriptUtil.obfuscateScript(content);
		FileUtil.write(encryptStr, file, "UTF-8");
		System.out.println(encryptStr);
	}
	
	@Test
	public void test() {
		String content = FileUtil.read(new File("/Users/jiuzhou.hu/local/resources/tmp/demo/qrcode/meihua/tmp.html"), "UTF-8");
		System.out.println(content.replaceAll("\r\n", " ").replaceAll(" +", " "));
		
	}
}
