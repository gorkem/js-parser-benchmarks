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

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

@State(Scope.Thread)
public class AcornWithNashorn extends TestBase {
	
	private ScriptEngine engine;
	
	
	@Setup
	public void initScriptEngine(){
		final ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("nashorn");
		final Path currentRelativePath = Paths.get("");
		final String acornPath = currentRelativePath.resolve("./acorn/node_modules/acorn/dist/acorn.js").toAbsolutePath().toString();
		try {
			final String parser = new String(Files.readAllBytes(Paths.get(acornPath)), StandardCharsets.UTF_8);
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
			final Object acorn = engine.get("acorn");
			final ScriptObjectMirror tree = (ScriptObjectMirror) inv.invokeMethod(acorn, "parse", content,"{\"locations\":true, \"range\":true}");
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (ScriptException e) {
			throw new RuntimeException(e);
		}
	}

}
