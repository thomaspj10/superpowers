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
import ttdev.superpowers.particle.animation.BlackPuff;

import java.util.Set;

public class TeleportPower implements Power<PlayerInteractEvent> {
	
	private final int teleportDistance = 20;
	
	private static TeleportPower singleton;
	
	static {
		singleton = new TeleportPower();
	}
	
	public static TeleportPower getInstance() {
		return singleton;
	}
	
	@Override
	public void use(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		
		/* Get target block */
		final Set<Material> ignoredBlocks = null;
		Block targetBlock = player.getTargetBlock(ignoredBlocks, teleportDistance);
		
		if (targetBlock.getType() == Material.AIR) {
			player.sendMessage("You can't teleport that far.");
			return;
		}
		
		/* Check if user has a power orb */
		PowerOrb powerOrb = PowerOrb.find(player.getInventory(), EnumPower.TELEPORT);
		if (powerOrb == null) {
			player.sendMessage(Configuration.getString("language.noOrbInHand", EnumPower.TELEPORT));
			return;
		}

		powerOrb.decrementEnergy(2);
		powerOrb.update();
		
		/* Check if user is still in cooldown */
		if (CooldownManager.contains(player.getUniqueId(), this)) {
			player.sendMessage(Configuration.getString("language.powerInCooldown", EnumPower.TELEPORT));
			return;
		}
		
		CooldownManager.add(e.getPlayer().getUniqueId(), this, 5);
		
		/* Teleport player to target location */
		Location teleportLocation = targetBlock.getLocation().add(0, 1, 0);
		Location playerLocation = player.getLocation();
		teleportLocation.setPitch(playerLocation.getPitch());
		teleportLocation.setYaw(playerLocation.getYaw());
		
		player.teleport(teleportLocation);
		
		/* Play particles effect */
		new BlackPuff().play(teleportLocation, player);
	}

}
