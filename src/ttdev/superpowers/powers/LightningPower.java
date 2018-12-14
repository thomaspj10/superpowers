package ttdev.superpowers.powers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

import ttdev.superpowers.CooldownManager;
import ttdev.superpowers.EnumPower;
import ttdev.superpowers.api.orb.PowerOrb;
import ttdev.superpowers.api.power.Power;
import ttdev.superpowers.configuration.Configuration;

public class LightningPower implements Power<PlayerInteractEvent> {

	private static LightningPower singleton;
	
    public static LightningPower getInstance() {
        return singleton;
    }
    
    static {
    	singleton = new LightningPower();
    }
	
	@Override
	public void use(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		
		/* Check for a right click */
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) | e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			
			/* Check if user has a power orb */
			PowerOrb powerOrb = PowerOrb.find(player.getInventory(), EnumPower.LIGHTNING);
			if (powerOrb == null) {
				player.sendMessage(Configuration.getString("language.noOrbInHand", EnumPower.LIGHTNING));
				return;
			}

			powerOrb.decrementEnergy(2);
			powerOrb.update();
			
			/* Check if user is still in cooldown */
			if (CooldownManager.contains(player.getUniqueId(), this)) {
				player.sendMessage(Configuration.getString("language.powerInCooldown", EnumPower.LIGHTNING));
				return;
			}
			
			CooldownManager.add(e.getPlayer().getUniqueId(), this, 5);
			
			/* Spawn a lightning strike on target block */
			Block targetBlock = getTargetBlock(e.getPlayer(), 10);
			
			Bukkit.getWorld(e.getPlayer().getWorld().getName()).strikeLightning(targetBlock.getLocation());
			
		}
		
	}
	
	/* Get player's target block */
    public final Block getTargetBlock(Player player, Integer range) {
        BlockIterator bi= new BlockIterator(player, range);
        Block lastBlock = bi.next();
        while (bi.hasNext()) {
            lastBlock = bi.next();
            if (lastBlock.getType() == Material.AIR)
                continue;
            break;
        }
        return lastBlock;
    }
	
}
