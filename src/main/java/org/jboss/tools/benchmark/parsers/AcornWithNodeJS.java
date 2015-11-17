package org.jboss.tools.benchmark.parsers;

import java.io.IOException;

import org.jboss.tools.benchmark.parsers.acorn.AcornParser;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class AcornWithNodeJS extends TestBase{

	private final AcornParser parser = new AcornParser();
	
	@Override
	public void testDelegate(final String content) {
		try {
			final String result  = parser.parse(content);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args){
		AcornWithNodeJS self = new AcornWithNodeJS();
		try {
			self.testAngular125();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
