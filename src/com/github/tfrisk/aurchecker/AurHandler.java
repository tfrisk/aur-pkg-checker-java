package com.github.tfrisk.aurchecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AurHandler {
	/* AUR package base URL */
	private static String baseUrl = "https://aur.archlinux.org/packages/";
	
	/* read the current list of installed package versions */
	HashMap<String, String> getInstalledPkgVersions() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("otf-font-awesome", "4.2.0-1");
		return map;
	}
	
	/* read raw html from AUR */
	/* https://stackoverflow.com/questions/31415/quick-way-to-find-a-value-in-html-java */
	StringBuilder readRawHtmlFromAur(String pkgName) {
		StringBuilder html = new StringBuilder();
		try {
			URL url = new URL(baseUrl + pkgName);
			BufferedReader input = null;
			input = new BufferedReader(
					new InputStreamReader(url.openStream()));
			String htmlLine;
			while ((htmlLine = input.readLine()) != null) {
				html.append(htmlLine);
			}
			input.close();
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL: " + baseUrl + pkgName);
		} catch (IOException e) {
			System.out.println("URL not found: " + baseUrl + pkgName);
		}
		return html;
	}

	/* read the latest version for one package */
	String getLatestVersionFromAur(String pkgName) {
		StringBuilder html = new StringBuilder();
		html = readRawHtmlFromAur(pkgName);
		
		/* parse latest package version from the raw html */
		Pattern exp = Pattern.compile(
				"^.*Package Details: " + pkgName + " ([^\"]*)</h2>");
		Matcher matcher = exp.matcher(html.toString());
		if (matcher.find()) {
			/* return parsed version as string */
			return matcher.group(1).toString();
		} else {
			return "not-found";
		}
	}

	/* read the latest versions for a list of packages */
	HashMap<String, String> getLatestPkgVersions(HashMap<String, String> pkgList) {
		/* iterate given hashmap */
		for (Map.Entry<String, String> entry: pkgList.entrySet()) {
			String name = entry.getKey(); /* read current name */
			pkgList.put(name, getLatestVersionFromAur(name)); /* update version */
		}
		return pkgList; /* return updated list with latest versions */
	}
}
