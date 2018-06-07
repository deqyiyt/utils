package com.ias.common.utils;

import com.ias.common.utils.date.DateUtil;
import com.ias.common.utils.date.TimeUtil;
import com.ias.common.utils.random.RandomUtils;

public class RandomTest {
	public static void main(String[] args) {  
		System.out.println(TimeUtil.toString(DateUtil.addSecond(TimeUtil.getSysTimestamp(), -(RandomUtils.nextInt(4950)+50))));
	}
}
