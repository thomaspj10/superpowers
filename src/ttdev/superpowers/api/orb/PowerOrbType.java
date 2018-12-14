package ttdev.superpowers.api.orb;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum PowerOrbType {

    PURPLE(new ItemStack(Material.INK_SACK, 1, (short) 5)),
    CYAN(new ItemStack(Material.INK_SACK, 1, (short) 6)),
    LIGHT_GRAY(new ItemStack(Material.INK_SACK, 1, (short) 7)),
    GRAY(new ItemStack(Material.INK_SACK, 1, (short) 8)),
    PINK(new ItemStack(Material.INK_SACK, 1, (short) 9)),
    LIME(new ItemStack(Material.INK_SACK, 1, (short) 10)),
    LIGHT_BLUE(new ItemStack(Material.INK_SACK, 1, (short) 12)),
    MAGENTA(new ItemStack(Material.INK_SACK, 1, (short) 13)),
    ORANGE(new ItemStack(Material.INK_SACK, 1, (short) 14));

    private ItemStack itemStack;

    PowerOrbType(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }

}
