package ttdev.superpowers.powers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import ttdev.superpowers.CooldownManager;
import ttdev.superpowers.EnumPower;
import ttdev.superpowers.api.orb.PowerOrb;
import ttdev.superpowers.api.power.Power;
import ttdev.superpowers.configuration.Configuration;

public class Fieldify implements Power<PlayerInteractEvent> {

    private static final Fieldify singleton;

    static {
        singleton = new Fieldify();
    }

    public static Fieldify getInstance() {
        return singleton;
    }

    @Override
    public void use(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        
		/* Check if user has a power orb */
		PowerOrb powerOrb = PowerOrb.find(player.getInventory(), EnumPower.FIELDIFY);
		if (powerOrb == null) {
			player.sendMessage(Configuration.getString("language.noOrbInHand", EnumPower.FIELDIFY));
			return;
		}

		powerOrb.decrementEnergy(2);
		powerOrb.update();
		
		/* Check if user is still in cooldown */
		if (CooldownManager.contains(player.getUniqueId(), this)) {
			player.sendMessage(Configuration.getString("language.powerInCooldown", EnumPower.FIELDIFY));
			return;
		}
		
		CooldownManager.add(player.getUniqueId(), this, 3);
        
		/* Plant crops in radius */
        final int width = 10;
        Location location = player.getLocation().subtract(width / 2, 0, width / 2);

        for (int x = 0; x < width; x++) {
            for (int z = 0; z < width; z++) {
                plantCrops(location.clone().add(x, 0, z));
            }
        }

    }

    private void plantCrops(Location location) {
        Block block = location.getBlock();
        block.setType(Material.SOIL);
        location.add(0, 1, 0).getBlock().setType(Material.CROPS);
    }

}
