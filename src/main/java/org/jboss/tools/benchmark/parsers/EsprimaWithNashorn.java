package org.jboss.tools.benchmark.parsers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.omg.CORBA.SystemException;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

@State(Scope.Thread)
public class EsprimaWithNashorn extends TestBase {

	private ScriptEngine engine;
	
	
	@Setup
	public void initScriptEngine(){
		final ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("nashorn");
		final Path currentRelativePath = Paths.get("");
		final String path = currentRelativePath.resolve("./esprima/node_modules/esprima/esprima.js").toAbsolutePath().toString();
		try {
			String parser= new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
			engine.eval(parser);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ScriptException e) {
			throw new RuntimeException(e);
		}

	}
	
	@Override
	public void testDelegate(final String content) {
		try {
			final Invocable inv = (Invocable) engine;
			final Object esprima = engine.get("esprima");

			final ScriptObjectMirror tree = (ScriptObjectMirror)inv.invokeMethod(esprima, "parse", content,"{\"loc\":true, \"range\":true, \"tolerant\":true}");
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (ScriptException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args){
		EsprimaWithNashorn self = new EsprimaWithNashorn();
		try {
			self.initScriptEngine();
			self.testAngular125();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
