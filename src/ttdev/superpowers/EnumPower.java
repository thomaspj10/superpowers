package ttdev.superpowers;

import org.bukkit.ChatColor;

import java.util.Arrays;

public enum EnumPower {

    BLACKSMITH("Blacksmith", ChatColor.GRAY + "Blacksmith" + ChatColor.RESET),
    FIELDIFY("Fieldify", ChatColor.YELLOW + "Fieldify" + ChatColor.RESET),
    HULK("Hulk", ChatColor.DARK_GREEN + "Hulk" + ChatColor.RESET),
    INSTAGROW("Instagrow", ChatColor.GREEN + "Instagrow" + ChatColor.RESET),
    MINING("Mining", ChatColor.GRAY + "Mining" + ChatColor.RESET),
    TELEPORT("Teleport", ChatColor.BLUE + "Teleport" + ChatColor.RESET),
	CLOAK("Cloak", ChatColor.BLUE + "Cloak" + ChatColor.RESET),
	SPEED("Speed", ChatColor.BLUE + "Speed" + ChatColor.RESET),
	MEDIC("Medic", ChatColor.BLUE + "Medic" + ChatColor.RESET),
	LIGHTNING("Lightning", ChatColor.BLUE + "Lightning" + ChatColor.RESET),
	HIGHJUMP("Highjump", ChatColor.BLUE + "HighJump" + ChatColor.RESET);

    private String id;
    private String displayName;

    EnumPower(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static EnumPower getById(String name) {
        return Arrays
                .stream(values())
                .filter(value -> value.getId().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);
    }

    public static EnumPower getByDisplayName(String name) {
        return Arrays
                .stream(values())
                .filter(value -> value.getDisplayName().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);
    }

}
