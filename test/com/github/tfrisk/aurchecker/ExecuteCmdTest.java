package com.github.tfrisk.aurchecker;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExecuteCmdTest {
	/* capture System.err */
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	@Before
	public void setErrOut() {
		System.setErr(new PrintStream(errContent));
	}
	
	@After
	public void cleanErrOut() {
		System.setErr(null);
	}
	
	@Test
	public void testSuccessfulCommandRun() {
		/* this might not be a good test */
		assertNotNull("must be able to run commands", ExecuteCmd.executeCmd("uname"));
	}
	
	@Test
	public void testFailingCommandRun() {
		ExecuteCmd.executeCmd("this.is.an.invalid.command");
		assertEquals("must get error from invalid commands",
				"Error: Could not run command 'this.is.an.invalid.command'\n",
				errContent.toString());
	}
	
}
