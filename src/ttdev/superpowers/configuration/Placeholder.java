package ttdev.superpowers.configuration;

import java.util.Arrays;

import ttdev.superpowers.EnumPower;
import ttdev.superpowers.PowerUtil;
import ttdev.superpowers.api.orb.PowerOrb;

public enum Placeholder {

	POWERS("powerList", "{powers}"),
	ORBNAME("orbName", "{orbName}"),
	ORBENERGY("orbEnergy", "{orbEnergy}"),
	ORBPOWER("powerName", "{powerName}");
	
	private String id;
	private String display;
	
	private Placeholder(String id, String display) {
		this.id = id;
		this.display = display;
	}
	
	public String getId() {
		return id;
	}

	public String getDisplay() {
		return display;
	}
	
	public String getOutput(PowerOrb orb) {
		switch(this.id) {
			case "powerList":
				return PowerUtil.powersAsList("", "", ", ");
			case "orbName":
				return orb.getName();
			case "orbEnergy":
				return String.valueOf(orb.getEnergy());
			case "powerName":
				return orb.getPower().getDisplayName();
				
		}
		return "";
	}
	
	public String getOutput(EnumPower power) {
		switch (this.id) {
			case "powerName":
				return power.getDisplayName();
		}
		return "";
	}
	
	public String getOutput() {
		switch (this.id) {
			case "powerList":
				return PowerUtil.powersAsList("", "", ", ");
		}
		return "";
	}
	
	public static Placeholder getByDisplay(String display) {
        return Arrays
                .stream(values())
                .filter(value -> value.getDisplay().equalsIgnoreCase(display))
                .findAny()
                .orElse(null);
	}
	
	public static String replacePlaceholder(String line, PowerOrb orb) {
		String l = line;
		for (Placeholder placeholder : values()) {
			if (l.contains(placeholder.getDisplay())) {
				l = l.replace(placeholder.getDisplay(), placeholder.getOutput(orb));
			}
		}
		return l;
	}
	
	public static String replacePlaceholder(String line, EnumPower power) {
		String l = line;
		for (Placeholder placeholder : values()) {
			if (l.contains(placeholder.getDisplay())) {
				l = l.replace(placeholder.getDisplay(), placeholder.getOutput(power));
			}
		}
		return l;
	}
	
	public static String replacePlaceholder(String line) {
		String l = line;
		for (Placeholder placeholder : values()) {
			if (l.contains(placeholder.getDisplay())) {
				l = l.replace(placeholder.getDisplay(), placeholder.getOutput());
			}
		}
		return l;
	}
	
}
