package com.github.tfrisk.aurchecker;

import static org.junit.Assert.assertEquals;

import java.util.TreeMap;

import org.junit.Ignore;
import org.junit.Test;

public class AurHandlerTest {
	
	@Test
	public void testConvertStringToMap() {
		AurHandler testHandler = new AurHandler();
		String testString = "foo 1.1\nbar 2.2\nbaz 3.3";
		TreeMap<String, AurVersion> testMap = new TreeMap<>();
		testMap.put("foo", new AurVersion("1.1"));
		testMap.put("bar", new AurVersion("2.2"));
		testMap.put("baz", new AurVersion("3.3"));
		assertEquals("should convert the string to map correctly",
				testMap,
				testHandler.convertStringToMap(testString));
	}
	
	/* requires mocking */
	@Ignore
	public void testGetInstalledPkgVersions() {}
	
	/* requires mocking */
	@Ignore
	public void testReadRawHtmlFromAur() {}
	
	@Test
	public void testGetLatestVersionFromAur() {
		AurHandler testHandler = new AurHandler();
		StringBuilder html = new StringBuilder();
		html.append("<div id='pkgdetails' class='box'>\n"
				+ "<h2>Package Details: blueman 1.99.alpha1-2</h2>\n"
				+ "<div id='detailslinks' class='listing'>\n"
				+ "<div id='actionlist'>\n"
				+ "<h4>Package Actions</h4>");
		assertEquals("should read the version number from raw html",
				"1.99.alpha1-2",
				testHandler.getLatestVersionFromAur("blueman").toString());
	}
	
	@Ignore
	public void getLatestPkgVersions() {}
}
