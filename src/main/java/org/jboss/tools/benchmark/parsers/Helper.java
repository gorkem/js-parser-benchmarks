package org.jboss.tools.benchmark.parsers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import com.google.common.io.Resources;

public final class Helper {
	
	public static String readResource(final String resource ) throws IOException, URISyntaxException{
		return Resources.toString(Helper.class.getResource(resource).toURI().toURL(), StandardCharsets.UTF_8);
	}

}
