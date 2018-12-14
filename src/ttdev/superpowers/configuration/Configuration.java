package ttdev.superpowers.configuration;

import net.md_5.bungee.api.ChatColor;
import ttdev.superpowers.EnumPower;
import ttdev.superpowers.Superpowers;
import ttdev.superpowers.api.orb.PowerOrb;

public class Configuration {

	public static String getString(String path, PowerOrb orb) {
		String value = Superpowers.getInstance().getConfig().getString(path);
		value = Placeholder.replacePlaceholder(value, orb);
		value = ChatColor.translateAlternateColorCodes('&', value);
		return value;
	}
	
	public static String getString(String path, EnumPower power) {
		String value = Superpowers.getInstance().getConfig().getString(path);
		value = Placeholder.replacePlaceholder(value, power);
		value = ChatColor.translateAlternateColorCodes('&', value);
		return value;
	}
	
	public static String getString(String path) {
		String value = Superpowers.getInstance().getConfig().getString(path);
		value = Placeholder.replacePlaceholder(value);
		value = ChatColor.translateAlternateColorCodes('&', value);
		return value;
	}
	
}
