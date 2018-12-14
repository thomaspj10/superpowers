package ttdev.superpowers.powers;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ttdev.superpowers.CooldownManager;
import ttdev.superpowers.EnumPower;
import ttdev.superpowers.api.orb.PowerOrb;
import ttdev.superpowers.api.power.Power;
import ttdev.superpowers.configuration.Configuration;

public class HighjumpPower implements Power<PlayerInteractEvent> {

	private static HighjumpPower singleton;
	
	static {
		singleton = new HighjumpPower();
	}
	
    public static HighjumpPower getInstance() {
        return singleton;
    }

	@Override
	public void use(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		
		/* Check for a right click */
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) | e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			
			/* Check if user has a power orb */
			PowerOrb powerOrb = PowerOrb.find(player.getInventory(), EnumPower.HIGHJUMP);
			if (powerOrb == null) {
				player.sendMessage(Configuration.getString("language.noOrbInHand", EnumPower.HIGHJUMP));
				return;
			}

			powerOrb.decrementEnergy(2);
			powerOrb.update();
			
			/* Check if user is still in cooldown */
			if (CooldownManager.contains(player.getUniqueId(), this)) {
				player.sendMessage(Configuration.getString("language.powerInCooldown", EnumPower.HIGHJUMP));
				return;
			}
			
			CooldownManager.add(e.getPlayer().getUniqueId(), this, 5);
			
			/* Give player jump boost */
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10, 4));
			
		}
		
	}
    
    
	
}
