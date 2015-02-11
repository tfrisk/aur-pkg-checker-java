package com.github.tfrisk.aurchecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecuteCmd {
	static String executeCmd(String command) {
		StringBuffer output = new StringBuffer();
		
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			p.destroy();
			reader.close();
		} catch (IOException e) {
			System.err.println("Error: Could not run command '" + command +"'");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output.toString();
	}
}
