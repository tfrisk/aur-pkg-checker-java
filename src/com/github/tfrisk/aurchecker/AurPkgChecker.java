package com.github.tfrisk.aurchecker;

import java.util.HashMap;

public class AurPkgChecker {

	public static void main(String[] args) {
		/* initialize new AurHandler */
		AurHandler myHandler = new AurHandler();
		
		/* init new hashmap to hold the installed packages */
		HashMap<String, AurVersion> myInstalledPackages =
				myHandler.getInstalledPkgVersions();
		
		System.out.println("myInstalledPackages: " + myInstalledPackages);
		
		/* init new hashmap to hold the latest packages */
		HashMap<String, AurVersion> myLatestPackages =
				myHandler.getLatestPkgVersions(myInstalledPackages);
		
		System.out.println("myLatestPackages: " + myLatestPackages);
	}
}
