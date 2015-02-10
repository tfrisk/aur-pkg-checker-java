package com.github.tfrisk.aurchecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AurHandler {
	/* AUR package base URL */
	private static String baseUrl = "https://aur.archlinux.org/packages/";
	
	/* convert from raw pacman output string to TreeMap */
	/* pretty hacky stuff, does not utilize modern java syntaxes */
	TreeMap<String, AurVersion> convertStringToMap(String input) {
		String lines[] = input.split("\n");
		TreeMap<String, AurVersion> map = new TreeMap<>();
		for (int i=0; i<lines.length; i++) {
			String[] pair = lines[i].split(" ");
			map.put(pair[0], new AurVersion(pair[1]));
		}
		return map;
	}
	
	/* read the current list of installed package versions */
	TreeMap<String, AurVersion> getInstalledPkgVersions() {
		/* read pacman */
		String pacmanRaw = ExecuteCmd.executeCmd("pacman -Qm");
		
		return convertStringToMap(pacmanRaw);
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
	AurVersion getLatestVersionFromAur(String pkgName) {
		StringBuilder html = new StringBuilder();
		html = readRawHtmlFromAur(pkgName);
		
		/* parse latest package version from the raw html */
		Pattern exp = Pattern.compile(
				"^.*Package Details: " + pkgName + " ([^\"]*)</h2>");
		Matcher matcher = exp.matcher(html.toString());
		if (matcher.find()) {
			/* return parsed version as string */
			return new AurVersion(matcher.group(1).toString());
		} else {
			return new AurVersion("0.0.0");
		}
	}

	/* read the latest versions for a list of packages */
	TreeMap<String, AurVersion> getLatestPkgVersions(TreeMap<String, AurVersion> pkgList) {
		TreeMap<String, AurVersion> latest = new TreeMap<>();
		/* iterate given map */
		for (Map.Entry<String, AurVersion> entry: pkgList.entrySet()) {
			String name = entry.getKey(); /* read current name */
			latest.put(name, getLatestVersionFromAur(name)); /* update version */
		}
		return latest; /* return map with latest versions */
	}
}
