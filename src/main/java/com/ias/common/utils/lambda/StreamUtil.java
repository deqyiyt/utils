package com.ias.common.utils.lambda;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class StreamUtil {

	public static Comparator<Number> dc = (a, b) -> {
		if (a != null && b != null) {
			Double result = a.doubleValue() - b.doubleValue();
			return result > 0 ? 1 : result == 0 ? 0 : -1;
		} else {
			return 0;
		}
	};
	
	public static Comparator<Double> dc_desc = (a, b) -> dc.compare(b, a);
	
	public static void main(String[] args) {
		DoubleStream.of(1.2, 2.3, 43.4).mapToObj(a -> a).sorted((a,b) -> dc_desc.compare(a, b)).forEach(System.out::println);
	}

	/**
	 * 封装groupingBy方法，提供有序的结果集
	 * @author zhili.yang
	 * @date 2017年11月16日 下午4:51:56
	 * @param classifier
	 * @return
	 */
	public static <T,K> Collector<T,?,LinkedHashMap<K,List<T>>> groupingByOrdered(Function<? super T,? extends K> classifier) {
		return Collectors.groupingBy(classifier, LinkedHashMap::new, Collectors.mapping(s -> s, Collectors.toList()));
	}
	
}
