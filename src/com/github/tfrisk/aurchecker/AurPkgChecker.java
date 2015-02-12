package com.github.tfrisk.aurchecker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

public class AurPkgChecker {

	public static void main(String[] args) {
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
				myHandler.downloadTarball(name); /* download updated packages */
			} else if (current.compareTo(latest) > 0) {
				statusline = statusline.concat("newer version installed: " + latest);
			}
			System.out.println(statusline);
		}
	}
}
