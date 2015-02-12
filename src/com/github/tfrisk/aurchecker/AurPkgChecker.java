package com.github.tfrisk.aurchecker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import com.beust.jcommander.JCommander;

public class AurPkgChecker {

	public static void main(String[] args) {
		/* command line parameter parsing */
		CmdlineParser cmdParser = new CmdlineParser();
		JCommander jc = new JCommander(cmdParser, args);
		jc.setProgramName("aur-pkg-checker-java");

		if (cmdParser.help_only) {
			jc.usage();
			System.exit(0);
		}

		/* initialize new AurHandler */
		AurHandler myHandler = new AurHandler();
		
		/* init new map to hold the installed packages */
		TreeMap<String, AurVersion> myInstalledPackages =
				myHandler.getInstalledPkgVersions();
		
		/* init new map to hold the latest packages */
		TreeMap<String, AurVersion> myLatestPackages =
				myHandler.getLatestPkgVersions(myInstalledPackages);
		
		String timestamp =
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").
				format(Calendar.getInstance().getTime());
		System.out.println("Current time is " + timestamp);
		System.out.println("Checking package versions");
		
		/* iterate installed packages and check for updates */
		for (Map.Entry<String, AurVersion> entry: myInstalledPackages.entrySet()) {
			String name = entry.getKey();
			AurVersion current = entry.getValue();
			AurVersion latest = myLatestPackages.getOrDefault(name, new AurVersion("0.0.0"));
			String statusline = new String(name + ": " + current + " => ");
			if (current.equals(latest)) {
				statusline = statusline.concat("OK");
			} else if (current.compareTo(latest) < 0) {
				statusline = statusline.concat("new version available: " + latest);
				/* download updated packages if not in check-only mode */
				if (!cmdParser.check_only) {
					myHandler.downloadTarball(name);
				}
			} else if (current.compareTo(latest) > 0) {
				statusline = statusline.concat("newer version installed: " + latest);
			}
			System.out.println(statusline);
		}
	}
}
