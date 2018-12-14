package ttdev.superpowers.powers;

import org.bukkit.Bukkit;
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

public class CloakPower implements Power<PlayerInteractEvent> {

	private static CloakPower singleton;
	
	static {
		singleton = new CloakPower();
	}
	
    public static CloakPower getInstance() {
        return singleton;
    }
	
	@Override
	public void use(PlayerInteractEvent e) {
		
		/* Check if the player right clicked */
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) | e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			
			/* Check if user has a power orb */
			Player player = (Player) e.getPlayer();
			PowerOrb powerOrb = PowerOrb.find(player.getInventory(), EnumPower.CLOAK);
			if (powerOrb == null) {
				player.sendMessage(Configuration.getString("language.noOrbInHand", EnumPower.CLOAK));
				return;
			}

			powerOrb.decrementEnergy(2);
			powerOrb.update();
			
			/* Check if user is still in cooldown */
			if (CooldownManager.contains(player.getUniqueId(), this)) {
				player.sendMessage(Configuration.getString("language.powerInCooldown", EnumPower.CLOAK));
				return;
			}
			
			CooldownManager.add(player.getUniqueId(), this, 3);
			
			/* Enable the cloak for player */
			setCloak(e.getPlayer());
			
		}
		
	}
	
    public void setCloak(final Player player) {
       
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.hidePlayer(player);
		}
    	
        new BukkitRunnable() {
            @Override
            public void run() {
    			for (Player player : Bukkit.getOnlinePlayers()) {
    				player.showPlayer(player);
    			}
            }

        }.runTaskLater(Superpowers.getInstance(), 60); //20 = 1s
    }

	
}
