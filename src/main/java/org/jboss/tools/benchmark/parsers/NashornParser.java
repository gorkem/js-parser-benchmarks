package org.jboss.tools.benchmark.parsers;

import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;

import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.options.Options;

@State(Scope.Thread)
public class NashornParser extends TestBase{

	private Context context;

	@Setup
	public void initNashorn(){
		Options options = new Options("nashorn");
		options.set("anon.functions", true);
		options.set("parse.only", true);
		options.set("scripting", true);
		context = new Context(options, new ErrorManager(), Thread.currentThread().getContextClassLoader());
	}

	@Override
	public void testDelegate(final String content) {
		try {
			Parser parser  = new Parser(context.getEnv(), Source.sourceFor("<unknown>", content), new ErrorManager());
			parser.parse();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

}
