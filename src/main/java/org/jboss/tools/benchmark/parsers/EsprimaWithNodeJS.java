package org.jboss.tools.benchmark.parsers;

import java.io.IOException;

import org.jboss.tools.benchmark.parsers.esprima.EsprimaParser;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class EsprimaWithNodeJS extends TestBase{
	private final EsprimaParser parser = new EsprimaParser();

	@Override
	public void testDelegate(final String content) {
		try {
			final String result = parser.parse(content);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args){
		EsprimaWithNodeJS self = new EsprimaWithNodeJS();
		try {
			self.testAngular125();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
