package com.ias.common.utils.encrypt;

import org.junit.Test;

public class EncodingTest {

	@Test
	public void encode() {
		String sign = EncodingUtil.encode("pvSpa4+M7OrpfUYyAN0a0yEVLCZt86F7BLml8FpfMy1867hi4HvY/PaXbLQBd6Z2");
		System.out.println(sign);
	}
}
