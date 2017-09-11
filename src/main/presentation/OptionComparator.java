package presentation;

import java.util.Comparator;

import org.apache.commons.cli.*;

class OptionComparator implements Comparator<Option> {
	private static final String OPTIONS_ORDER = "hsrwo";

	public int compare(Option o1, Option o2) {
		return OPTIONS_ORDER.indexOf(o1.getOpt()) - OPTIONS_ORDER.indexOf(o2.getOpt());
	}
	
	
}
