package org.jboss.tools.benchmark.parsers;

import org.openjdk.jmh.annotations.Benchmark;

public abstract class TestBase {
	public static final String LIB_ANGULAR = "/libraries/angular-1.2.5.js";
	public static final String LIB_JQM = "/libraries/jquery.mobile-1.4.2.js";

	@Benchmark
	public void testAngular125() throws Exception{
		final String content = Helper.readResource(LIB_ANGULAR);
		testDelegate(content);
	}
	
	@Benchmark
	public void testJQM142() throws Exception{
		final String content = Helper.readResource(LIB_JQM);
		testDelegate(content);
	}
	
	public abstract void testDelegate(String content);

}
