package ttdev.superpowers.powers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

import ttdev.superpowers.CooldownManager;
import ttdev.superpowers.EnumPower;
import ttdev.superpowers.api.orb.PowerOrb;
import ttdev.superpowers.api.power.Power;
import ttdev.superpowers.configuration.Configuration;

public class HulkPower implements Power<PlayerInteractEvent>, Listener {
	
	private static HulkPower singleton;
	
    public static HulkPower getInstance() {
        return singleton;
    }
    
    static {
    	singleton = new HulkPower();
    }

	@Override
	public void use(PlayerInteractEvent e) {

    	Player player = e.getPlayer();
    	
    	/* Check for right click */
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			
			/* Check if user has a power orb */
			PowerOrb powerOrb = PowerOrb.find(player.getInventory(), EnumPower.HULK);
			if (powerOrb == null) {
				player.sendMessage(Configuration.getString("language.noOrbInHand", EnumPower.HULK));
				return;
			}

			powerOrb.decrementEnergy(2);
			powerOrb.update();
			
			/* Check if user is still in cooldown */
			if (CooldownManager.contains(player.getUniqueId(), this)) {
				player.sendMessage(Configuration.getString("language.powerInCooldown", EnumPower.HULK));
				return;
			}
			
			CooldownManager.add(e.getPlayer().getUniqueId(), this, 5);

			/* Create an explosion */
			Block targetBlock = getTargetBlock(player, 5);
			
			player.getWorld().createExplosion(targetBlock.getLocation(), 2, false);
			
		}
		
	}
	
	/* Cancel block explosion damage */
	@EventHandler
	public void onEntityDamage(EntityDamageByBlockEvent e) {
		
		if (e.getCause().equals(DamageCause.BLOCK_EXPLOSION)) {
			
			if (e.getDamager() == null) {
				e.setCancelled(true);
			}
			
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
