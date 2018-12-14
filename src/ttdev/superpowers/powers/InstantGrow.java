package ttdev.superpowers.powers;

import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;
import org.bukkit.material.NetherWarts;

import ttdev.superpowers.CooldownManager;
import ttdev.superpowers.EnumPower;
import ttdev.superpowers.api.orb.PowerOrb;
import ttdev.superpowers.api.particle.ParticleLine;
import ttdev.superpowers.api.power.Power;
import ttdev.superpowers.configuration.Configuration;
import ttdev.superpowers.particle.animation.GreenSparks;
import ttdev.superpowers.particle.animation.LineFlux;

import java.util.Set;
import java.util.function.BiConsumer;

public class InstantGrow implements Power<PlayerInteractEvent> {

    private static final InstantGrow singleton;

    private final int reach = 50;

    static {
        singleton = new InstantGrow();
    }

    public static InstantGrow getInstance() {
        return singleton;
    }

    @Override
    public void use(PlayerInteractEvent e) {
        Player player = e.getPlayer();
		
		/* Check if user has a power orb */
		PowerOrb powerOrb = PowerOrb.find(player.getInventory(), EnumPower.INSTAGROW);
		if (powerOrb == null) {
			player.sendMessage(Configuration.getString("language.noOrbInHand", EnumPower.INSTAGROW));
			return;
		}

		powerOrb.decrementEnergy(2);
		powerOrb.update();
		
		/* Check if user is still in cooldown */
		if (CooldownManager.contains(player.getUniqueId(), this)) {
			player.sendMessage(Configuration.getString("language.powerInCooldown", EnumPower.INSTAGROW));
			return;
		}
		
		CooldownManager.add(e.getPlayer().getUniqueId(), this, 3);

		/* Get block locations */
        final int width = 4;
        final int widthHalved = width / 2;
        
        BiConsumer<Location, Player[]> doPower = (loc, players) -> {
        	loc.subtract(widthHalved, widthHalved, widthHalved);
        	for (int x = 0; x < width; x++) {
        		for (int y = 0; y < width; y++) {
        			for (int z = 0; z < width; z++) {
        				grow(loc.clone().add(x, y, z).getBlock());
        			}
        		}
        	}
        };

        // Casting 'null' to 'set' in this case, otherwise the method call would be ambiguous
        // This is the target block (where the effect will finish playing)
        Block block = player.getTargetBlock((Set<Material>) null, reach);
        Location start = player.getEyeLocation();
        Location end = block.getLocation();

        new ParticleLine(start, end)
                .lineModel(new LineFlux())
                .endModel(new GreenSparks())
                .density(0.2f)
                .speed(10)
                .onEnd(doPower)
                .play(start, player);

    }

    private void grow(Block block) {
    	final byte maxData = 7;
        BlockState state = block.getState();
        MaterialData data = state.getData();
        if (data instanceof Crops) {
            ((Crops) data).setState(CropState.RIPE);
        } else if (data instanceof NetherWarts) {
            ((NetherWarts) data).setState(NetherWartsState.RIPE);
        } else if (block.getType() == Material.MELON_STEM) {
        	data.setData(maxData);
        } else if (block.getType() == Material.PUMPKIN_STEM) {
        	data.setData(maxData);
        } else if (block.getType() == Material.POTATO) {
        	data.setData(maxData);
        } else if (block.getType() == Material.CARROT) {
        	data.setData(maxData);
        }
        
        state.update();
    }

}
