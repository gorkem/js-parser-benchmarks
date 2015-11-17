package org.jboss.tools.benchmark.parsers;

import com.shapesecurity.shift.ast.Script;
import com.shapesecurity.shift.parser.JsError;
import com.shapesecurity.shift.parser.Parser;

public class ShiftJava extends TestBase{

	@Override
	public void testDelegate(final String content) {
		try {
			@SuppressWarnings("unused")
			final Script s = Parser.parseScript(content);
		} catch (JsError e) {
			throw new RuntimeException(e);
		}
	}
	

}
