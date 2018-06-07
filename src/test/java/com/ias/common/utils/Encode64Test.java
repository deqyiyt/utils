package com.ias.common.utils;

import org.junit.Test;

import com.ias.common.utils.number.Encode64;

public class Encode64Test {

	@Test
	public void compressTest() {
		System.out.println(Encode64.compressNumber(99999));
	}
	
	@Test
	public void unCompressTest() {
		System.out.println(Encode64.unCompressNumber("0fMa"));
	}
}
