package com.github.tfrisk.aurchecker;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

public class CmdlineParser {
	@Parameter
	private List<String> parameters = new ArrayList<>();
	
	@Parameter(names = {"-c", "--check-only"},
			description = "Check only, do not download",
			required = false)
	boolean check_only;
	
	@Parameter(names = {"-h","--help"},
			description = "Display help",
			help = true)
	boolean help_only;
}
