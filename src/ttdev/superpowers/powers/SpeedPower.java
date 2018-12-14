package ttdev.superpowers.powers;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import ttdev.superpowers.CooldownManager;
import ttdev.superpowers.EnumPower;
import ttdev.superpowers.Superpowers;
import ttdev.superpowers.api.orb.PowerOrb;
import ttdev.superpowers.api.power.Power;
import ttdev.superpowers.configuration.Configuration;

public class SpeedPower implements Power<PlayerInteractEvent> {

	private static SpeedPower singleton;
	
	static {
		singleton = new SpeedPower();
	}
	
    public static SpeedPower getInstance() {
        return singleton;
    }
	
	@Override
	public void use(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		
		/* Check if the user right clicks */
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) | e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			
			/* Check if user has a power orb */
			PowerOrb powerOrb = PowerOrb.find(player.getInventory(), EnumPower.SPEED);
			if (powerOrb == null) {
				player.sendMessage(Configuration.getString("language.noOrbInHand", EnumPower.SPEED));
				return;
			}

			powerOrb.decrementEnergy(2);
			powerOrb.update();
			
			/* Check if user is still in cooldown */
			if (CooldownManager.contains(player.getUniqueId(), this)) {
				player.sendMessage(Configuration.getString("language.powerInCooldown", EnumPower.SPEED));
				return;
			}
			
			CooldownManager.add(e.getPlayer().getUniqueId(), this, 5);
			
			/* Set the player speed */
			setSpeed(e.getPlayer());
			
		}
		
	}

    public void setSpeed(final Player player) {
        
    	player.setWalkSpeed(2);
    	
        new BukkitRunnable() {
            @Override
            public void run() {
            	player.setWalkSpeed(1);
            }

        }.runTaskLater(Superpowers.getInstance(), 60); //20 = 1s
    }
	
}
