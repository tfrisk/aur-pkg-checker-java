package com.github.tfrisk.aurchecker;

import static org.junit.Assert.*;

import org.junit.Test;

public class AurVersionTest {

	@Test
	public void testAurVersion() {
		AurVersion testVersion = new AurVersion("1.0.1");
		assertEquals("version should be set at constructor",
				"1.0.1", testVersion.toString());
	}

	@Test
	public void testCompareTo() {
		AurVersion ver1 = new AurVersion("1.99.alpha1-2");
		AurVersion ver2 = new AurVersion("1.99.alpha2-0");
		AurVersion ver3 = new AurVersion("1.99.alpha1-2");
		assertEquals("first should be smaller than the second",
				-1, ver1.compareTo(ver2));
		assertEquals("first should be bigger than the second",
				1, ver2.compareTo(ver1));
		assertEquals("first should be equal to the second",
				0, ver1.compareTo(ver3));
	}

}
