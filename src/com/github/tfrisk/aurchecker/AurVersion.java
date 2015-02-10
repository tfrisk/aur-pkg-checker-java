package com.github.tfrisk.aurchecker;

/* Arch version number, do comparison with external vercmp command */
public class AurVersion implements Comparable<AurVersion> {
	String version;
	
	public AurVersion(String version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		return version.toString();
	}
	
	@Override
	public int compareTo(AurVersion o) {
		// compareTo should return < 0 if this is supposed to be
        // less than other, > 0 if this is supposed to be greater than 
        // other and 0 if they are supposed to be equal
		String pacmanRaw =
				ExecuteCmd.executeCmd("vercmp " + this.version + " " + o.version);
		/* return the vercmp output without newline */
		return Integer.parseInt(pacmanRaw.trim());
	}
}
