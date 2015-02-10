package com.github.tfrisk.aurchecker;

import java.util.HashMap;

public class AurPkgChecker {

	public static void main(String[] args) {
		/* initialize new AurHandler */
		AurHandler myHandler = new AurHandler();
		
		/* init new hashmap to hold the installed packages */
		HashMap<String, String> myInstalledPackages =
				new HashMap<String, String>(myHandler.getInstalledPkgVersions());
	
		System.out.println("myInstalledPackages: " + myInstalledPackages);
		
		/* init new hashmap to hold the latest packages */
		HashMap<String, String> myLatestPackages =
				new HashMap<String, String>(myHandler.getLatestPkgVersions());
	
		System.out.println("myLatestPackages: " + myLatestPackages);
		
		myHandler.getLatestVersionFromAur("blueman");
	}
}
