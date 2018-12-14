package ttdev.superpowers.api.orb;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ttdev.superpowers.EnumPower;

import java.util.Arrays;

public class PowerOrb {

    private static final int MAX_ENERGY = 100;
    private static final int MIN_ENERGY = 0;
    private static final String ENERGY_PREFIX = ChatColor.WHITE + "Energy:";

    private EnumPower power;
    private String name;
    private int energy;

    private ItemStack itemStack;

    private PowerOrb(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        this.name = meta.getDisplayName();
        this.energy = Integer.parseInt(meta.getLore().get(0).split(":")[1].trim());
        this.itemStack = itemStack;
        power = EnumPower.getByDisplayName(name);
    }

    // Synchronize PowerOrb state with ItemStack
    public void update() {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(ENERGY_PREFIX + " " + energy));
        itemStack.setItemMeta(meta);
    }

    public static PowerOrb parseItemStack(ItemStack itemStack) throws NullPointerException {
        if (!itemStack.hasItemMeta()) {
            return null;
        }
        ItemMeta meta = itemStack.getItemMeta();
        String name = meta.getDisplayName();
        Material material = itemStack.getType();
        short data = itemStack.getDurability();

        boolean nameMatch = Arrays
                .stream(EnumPower.values())
                .anyMatch(power -> power.getDisplayName().equals(name));

        boolean materialMatch = Arrays.stream(PowerOrbType.values()).anyMatch(orb -> {
            ItemStack stack = orb.getItemStack();
            boolean typeMatch = stack.getType().equals(material);
            boolean dataMatch = stack.getDurability() == data;
            return typeMatch && dataMatch;
        });

        if (!(nameMatch && materialMatch)) {
            return null;
        }

        return new PowerOrb(itemStack);
    }

    public static PowerOrb find(Inventory inventory, EnumPower power) {
        int handSlot = 0;
        ItemStack itemInHand = inventory.getContents()[handSlot];
        PowerOrb orb = parseItemStack(itemInHand);

        if (orb == null || orb.getPower() != power) {
            return null;
        }

        return orb;
    }

    public static PowerOrb givePowerOrb(EnumPower power, PowerOrbType orbType, Player player) {
        ItemStack itemStack = orbType.getItemStack();
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(power.getDisplayName());
        meta.setLore(Arrays.asList(ENERGY_PREFIX + " " + MAX_ENERGY));
        itemStack.setItemMeta(meta);
        player.getInventory().addItem(itemStack);
        return new PowerOrb(itemStack);
    }

    public int decrementEnergy(int amount) {
        energy -= amount;
        if (energy <= MIN_ENERGY) {
            energy = MIN_ENERGY;
        }
        return energy;
    }

    public int incrementEnergy(int amount) {
        energy += amount;
        if (energy >= MAX_ENERGY) {
            energy = MAX_ENERGY;
        }
        return energy;
    }

    public int setEnergy(int amount) {
        energy = amount;
        return energy;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean hasNoEnergy() {
        return energy == 0;
    }

    public String getName() {
        return name;
    }

    public EnumPower getPower() {
        return power;
    }

}
