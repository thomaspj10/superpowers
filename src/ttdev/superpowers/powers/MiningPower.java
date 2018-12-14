package ttdev.superpowers.powers;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.md_5.bungee.api.ChatColor;
import ttdev.superpowers.CooldownManager;
import ttdev.superpowers.EnumPower;
import ttdev.superpowers.api.orb.PowerOrb;
import ttdev.superpowers.api.power.Power;
import ttdev.superpowers.configuration.Configuration;
import ttdev.superpowers.particle.animation.GreenSparks;

public class MiningPower implements Power<PlayerInteractEvent> {

    private static MiningPower singleton;

    private static HashMap<Material, Material> oreList = new HashMap<Material, Material>();

    static {
        singleton = new MiningPower();
        oreList.put(Material.COAL_ORE, Material.IRON_ORE);
        oreList.put(Material.IRON_ORE, Material.GOLD_ORE);
        oreList.put(Material.GOLD_ORE, Material.EMERALD_ORE);
        oreList.put(Material.EMERALD_ORE, Material.DIAMOND_ORE);
    }

    public static MiningPower getInstance() {
        return singleton;
    }

    @Override
    public void use(PlayerInteractEvent e) {
    	Player player = e.getPlayer();
        
        /* Check if player right clicks */
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
		
			/* Check if user has a power orb */
			PowerOrb powerOrb = PowerOrb.find(player.getInventory(), EnumPower.MINING);
			if (powerOrb == null) {
				player.sendMessage(Configuration.getString("language.noOrbInHand", EnumPower.MINING));
				return;
			}

			powerOrb.decrementEnergy(2);
			powerOrb.update();
			
			/* Check if user is still in cooldown */
			if (CooldownManager.contains(player.getUniqueId(), this)) {
				player.sendMessage(Configuration.getString("language.powerInCooldown", EnumPower.MINING));
				return;
			}

            /* Block diamond ores */
            if (e.getClickedBlock().getType().equals(Material.DIAMOND_ORE)) {
                e.getPlayer().sendMessage(ChatColor.RED + "You can't upgrade a Diamond Ore!");
                return;
            }

            /* Check if oreList contains the clicked block */
            if (oreList.containsKey(e.getClickedBlock().getType())) {
            	
            	/* Set in cooldown */
    			CooldownManager.add(e.getPlayer().getUniqueId(), this, 3);

                /* Upgrade the ore to the next level */
                String oreName = e.getClickedBlock().getType().toString().toLowerCase();
                oreName = oreName.replaceAll("_", " ");
                e.getPlayer().sendMessage(ChatColor.GREEN + "Upgraded the " + oreName + " to the next level!");
                e.getClickedBlock().setType(oreList.get(e.getClickedBlock().getType()));

                /* Play upgrade particles */
                new GreenSparks().play(e.getClickedBlock().getLocation(), e.getPlayer());

            }
        }

    }

}