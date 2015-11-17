package org.jboss.tools.benchmark.parsers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;

@State(Scope.Thread)
public class AcornWithJ2V8 extends TestBase{
	private V8 v8;
	
	@Setup
	public void initV8(){
		v8 = V8.createV8Runtime();
		final Path currentRelativePath = Paths.get("");
		final String path = currentRelativePath.resolve("./acorn/node_modules/acorn/dist/acorn.js").toAbsolutePath().toString();
		try {
			final String parser = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
			v8.executeScript(parser);
			v8.executeScript("function parser(content, options){ return JSON.stringify(acorn.parse(content,options));}");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public void testDelegate(final String content) {
		final V8Array parameters = new V8Array(v8);
		parameters.push(content);
		parameters.push("{\"locations\":true, \"range\":true}");
		final String s = v8.executeStringFunction("parser", parameters);
		parameters.release();
	}
	
	@TearDown
	public void releaseV8(){
		if(v8 != null){
			v8.release();
		}
	}
	
	public static void main(String[] args){
		AcornWithJ2V8 self = new AcornWithJ2V8();
		try {
			self.initV8();
			self.testAngular125();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			self.releaseV8();
		}
		
	}

}
