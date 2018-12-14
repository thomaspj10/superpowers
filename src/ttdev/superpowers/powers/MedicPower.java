package ttdev.superpowers.powers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;
import ttdev.superpowers.CooldownManager;
import ttdev.superpowers.EnumPower;
import ttdev.superpowers.api.orb.PowerOrb;
import ttdev.superpowers.api.power.Power;
import ttdev.superpowers.configuration.Configuration;

public class MedicPower implements Power<PlayerInteractEvent> {

	private static MedicPower singleton;
	
	static {
		singleton = new MedicPower();
	}
	
    public static MedicPower getInstance() {
        return singleton;
    }
	
	@Override
	public void use(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		
		/* Check for a right click */
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) | e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			
			/* Check if user has a power orb */
			PowerOrb powerOrb = PowerOrb.find(player.getInventory(), EnumPower.MEDIC);
			if (powerOrb == null) {
				player.sendMessage(Configuration.getString("language.noOrbInHand", EnumPower.MEDIC));
				return;
			}

			powerOrb.decrementEnergy(2);
			powerOrb.update();
			
			/* Check if user is still in cooldown */
			if (CooldownManager.contains(player.getUniqueId(), this)) {
				player.sendMessage(Configuration.getString("language.powerInCooldown", EnumPower.MEDIC));
				return;
			}
			
			CooldownManager.add(e.getPlayer().getUniqueId(), this, 5);
			
			/* Loop trough all players */
			for (Player p : Bukkit.getOnlinePlayers()) {
			
				/* Calculate reach */
				double xDif = Math.abs(e.getPlayer().getLocation().getX() - p.getLocation().getX());
				double zDif = Math.abs(e.getPlayer().getLocation().getZ() - p.getLocation().getZ());
				
				double range = (xDif > zDif ? xDif : zDif);
				
				if (range < 5) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 4, 1));
					p.sendMessage(ChatColor.GREEN + e.getPlayer().getName() + " has used his superpower to heal you!");
				}
				
			}
			
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 4, 1));
			
		}
		
	}
	
}
