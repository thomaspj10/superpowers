package ttdev.superpowers;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ttdev.superpowers.api.orb.PowerOrb;
import ttdev.superpowers.api.orb.PowerOrbType;
import ttdev.superpowers.configuration.Configuration;
import ttdev.superpowers.powers.BlacksmithPower;
import ttdev.superpowers.powers.Fieldify;
import ttdev.superpowers.powers.HulkPower;
import ttdev.superpowers.powers.InstantGrow;
import ttdev.superpowers.powers.MiningPower;
import ttdev.superpowers.powers.TeleportPower;

public class Superpowers extends JavaPlugin implements Listener {

    private static Superpowers singleton;

    // Used for switching between different powers
    private EnumPower activePower = EnumPower.INSTAGROW;
    private boolean interactMode = true;
    
    @Override
    public void onEnable() {
    	/* Initialize instance */
        singleton = this;
        
        /* Register events */
        Server server = getServer();
        PluginManager manager = server.getPluginManager();

        manager.registerEvents(this, this);
        manager.registerEvents(HulkPower.getInstance(), this);
        
        /* Create configuration */
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        super.onEnable();
    }

    @Override
    public void onDisable() {
    	/* Save configuration */
    	this.saveConfig();
    	
        super.onDisable();
    }

    public static JavaPlugin getInstance() {
        return singleton;
    }
    
    /* TODO */

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
    	BlacksmithPower.getInstance().use(event);
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (!interactMode) {
            return;
        }

        switch(activePower) {
            case INSTAGROW:
                InstantGrow.getInstance().use(event);
                break;
            case MINING:
                MiningPower.getInstance().use(event);
                break;
            case HULK:
            	HulkPower.getInstance().use(event);
            	break;
            case TELEPORT:
            	TeleportPower.getInstance().use(event);;
            	break;
            case FIELDIFY:
            	Fieldify.getInstance().use(event);
            	break;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to perform this command.");
            return true;
        }

        Player player = (Player) sender;

        if (label.equalsIgnoreCase("power")) {
        	if (args.length == 1) {
        		return runPowerCommand(player, args);
        	} else {
        		player.sendMessage(Configuration.getString("language.unknownPower"));
        	}
        }

        if (label.equalsIgnoreCase("orb")) {

            if (args.length == 0 || args.length > 1) {
                return false;
            }

            String name = args[0];
            EnumPower enumPower = EnumPower.getById(name);
            if (enumPower == null) {
            	player.sendMessage(Configuration.getString("language.unknownPower"));
            	return true;
            }

            PowerOrb orb = PowerOrb.givePowerOrb(enumPower, PowerOrbType.GRAY, player);
            player.sendMessage(Configuration.getString("language.powerReceived", orb));
        }

        return true;

    }

    private boolean runPowerCommand(Player player, String... args) {
        switch (args[0].toLowerCase()) {
            /* Toggle whether to shoot particles when the player interacts */
            case "toggleinteract":
                interactMode = !interactMode;
                player.sendMessage("Interact mode: "+ interactMode);
                break;
            case "blacksmith":
                activePower = EnumPower.BLACKSMITH;
                player.sendMessage(Configuration.getString("language.powerTypeSelected", activePower));
                break;
            case "fieldify":
            	activePower = EnumPower.FIELDIFY;
            	player.sendMessage(Configuration.getString("language.powerTypeSelected", activePower));
            	break;
            case "hulk":
            	activePower = EnumPower.HULK;
            	player.sendMessage(Configuration.getString("language.powerTypeSelected", activePower));
            	break;
            case "instagrow":
                activePower = EnumPower.INSTAGROW; 
                player.sendMessage(Configuration.getString("language.powerTypeSelected", activePower));
                break;
            case "mining":
                activePower = EnumPower.MINING;
                player.sendMessage(Configuration.getString("language.powerTypeSelected", activePower));
                break;
            case "teleport":
                activePower = EnumPower.TELEPORT;
                player.sendMessage(Configuration.getString("language.powerTypeSelected", activePower));
                break;
            case "cloak":
                activePower = EnumPower.CLOAK;
                player.sendMessage(Configuration.getString("language.powerTypeSelected", activePower));
                break;
            case "speed":
            	activePower = EnumPower.SPEED;
            	player.sendMessage(Configuration.getString("language.powerTypeSelected", activePower));
            	break;
            case "medic":
                activePower = EnumPower.MEDIC;
                player.sendMessage(Configuration.getString("language.powerTypeSelected", activePower));
                break;
            case "lightning":
                activePower = EnumPower.LIGHTNING;
                player.sendMessage(Configuration.getString("language.powerTypeSelected", activePower));
                break;
        }

        return true;
    }

}
