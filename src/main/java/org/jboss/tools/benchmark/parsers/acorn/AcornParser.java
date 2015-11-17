package org.jboss.tools.benchmark.parsers.acorn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public final class AcornParser {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$

	private Process nodeProcess;
    private BufferedReader consoleOut;
    private PrintWriter consoleIn;
	
	public AcornParser(){
		initParser();
	}
    
	
	public String parse(String content)throws IOException{
		this.consoleIn.println(content + "\nEND");
        String resultJson = null; 
        do{
        	String line = this.consoleOut.readLine();
        	if (line == null) {
                throw new IllegalStateException("The node process has crashed.");
            } else if (line.startsWith("ERROR:")) {
                // remove prefix
                line = line.substring("ERROR".length(), line.length());
                // put newlines back
                line = line.replaceAll("\\\\n", LINE_SEPARATOR); // put newlines back
                // replace soft tabs with hardtabs to match Java's error stack trace.
                line = line.replaceAll("    ", "\t");

                throw new RuntimeException("The following request caused an error to be thrown:" + LINE_SEPARATOR
                        + content + LINE_SEPARATOR
                        + line);
            } else if (line.startsWith("RESULT")) {
                resultJson = line.substring("RESULT".length());
            }
            else{
            	System.out.println(line);
            }
        }while (resultJson == null);
        
        return resultJson;
	}
	
    private void initParser(){
		String nodePath = "/usr/local/bin/node";
		
		Path currentRelativePath = Paths.get("");
		String acornPath = currentRelativePath.resolve("./acorn/parser.js").toAbsolutePath().toString();
		
        
		List<String> args = new ArrayList<String>();
		args.add(nodePath);
		args.add(acornPath);
		
		ProcessBuilder pb = new ProcessBuilder(args.toArray(new String[args.size()]));
		try {
			this.nodeProcess = pb.start();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.consoleOut = new BufferedReader(new InputStreamReader(
					this.nodeProcess.getInputStream(),
					Charset.forName("UTF-8") ));
		this.consoleIn = new PrintWriter(new OutputStreamWriter(
					this.nodeProcess.getOutputStream(), 
					Charset.forName("UTF-8")), true);

		
	}

}
