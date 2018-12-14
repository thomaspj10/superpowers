package ttdev.superpowers.powers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

import ttdev.superpowers.CooldownManager;
import ttdev.superpowers.EnumPower;
import ttdev.superpowers.Superpowers;
import ttdev.superpowers.api.orb.PowerOrb;
import ttdev.superpowers.api.power.Power;
import ttdev.superpowers.configuration.Configuration;

import java.util.Arrays;
import java.util.Random;

public class BlacksmithPower implements Power<CraftItemEvent> {
	
	private static BlacksmithPower singleton;
	
	private enum MaterialType {
		WOOD, STONE, IRON, GOLD, DIAMOND
	}
	
	static {
		singleton = new BlacksmithPower();
	}
	
	private BlacksmithPower() {
		ShapelessRecipe woodRecipe = new ShapelessRecipe(applyRandomDurability(Material.WOOD_PICKAXE, MaterialType.WOOD));
		ShapelessRecipe stoneRecipe = new ShapelessRecipe(applyRandomDurability(Material.STONE_PICKAXE, MaterialType.STONE));
		ShapelessRecipe ironRecipe = new ShapelessRecipe(applyRandomDurability(Material.IRON_PICKAXE, MaterialType.IRON));
		ShapelessRecipe goldRecipe = new ShapelessRecipe(applyRandomDurability(Material.GOLD_PICKAXE, MaterialType.GOLD));
		ShapelessRecipe diamondRecipe = new ShapelessRecipe(applyRandomDurability(Material.DIAMOND_PICKAXE, MaterialType.DIAMOND));
		
		
		ShapelessRecipe[] recipes = addIngredients(Material.WOOD_PICKAXE, woodRecipe, stoneRecipe, ironRecipe, goldRecipe, diamondRecipe);
		Arrays.stream(recipes).forEach(Superpowers.getInstance().getServer()::addRecipe);
		
		woodRecipe = new ShapelessRecipe(applyRandomDurability(Material.WOOD_AXE, MaterialType.WOOD));
		stoneRecipe = new ShapelessRecipe(applyRandomDurability(Material.STONE_AXE, MaterialType.STONE));
		ironRecipe = new ShapelessRecipe(applyRandomDurability(Material.IRON_AXE, MaterialType.IRON));
		goldRecipe = new ShapelessRecipe(applyRandomDurability(Material.GOLD_AXE, MaterialType.GOLD));
		diamondRecipe = new ShapelessRecipe(applyRandomDurability(Material.DIAMOND_AXE, MaterialType.DIAMOND));
		
		recipes = addIngredients(Material.WOOD_AXE, woodRecipe, stoneRecipe, ironRecipe, goldRecipe, diamondRecipe);
		Arrays.stream(recipes).forEach(Superpowers.getInstance().getServer()::addRecipe);
		
		woodRecipe = new ShapelessRecipe(applyRandomDurability(Material.WOOD_SPADE, MaterialType.WOOD));
		stoneRecipe = new ShapelessRecipe(applyRandomDurability(Material.STONE_SPADE, MaterialType.STONE));
		ironRecipe = new ShapelessRecipe(applyRandomDurability(Material.IRON_SPADE, MaterialType.IRON));
		goldRecipe = new ShapelessRecipe(applyRandomDurability(Material.GOLD_SPADE, MaterialType.GOLD));
		diamondRecipe = new ShapelessRecipe(applyRandomDurability(Material.DIAMOND_SPADE, MaterialType.DIAMOND));
		
		recipes = addIngredients(Material.WOOD_SPADE, woodRecipe, stoneRecipe, ironRecipe, goldRecipe, diamondRecipe);
		Arrays.stream(recipes).forEach(Superpowers.getInstance().getServer()::addRecipe);
		
		woodRecipe = new ShapelessRecipe(applyRandomDurability(Material.WOOD_SWORD, MaterialType.WOOD));
		stoneRecipe = new ShapelessRecipe(applyRandomDurability(Material.STONE_SWORD, MaterialType.STONE));
		ironRecipe = new ShapelessRecipe(applyRandomDurability(Material.IRON_SWORD, MaterialType.IRON));
		goldRecipe = new ShapelessRecipe(applyRandomDurability(Material.GOLD_SWORD, MaterialType.GOLD));
		diamondRecipe = new ShapelessRecipe(applyRandomDurability(Material.DIAMOND_SWORD, MaterialType.DIAMOND));
		
		recipes = addIngredients(Material.WOOD_SWORD, woodRecipe, stoneRecipe, ironRecipe, goldRecipe, diamondRecipe);
		Arrays.stream(recipes).forEach(Superpowers.getInstance().getServer()::addRecipe);
		
		woodRecipe = new ShapelessRecipe(applyRandomDurability(Material.WOOD_HOE, MaterialType.WOOD));
		stoneRecipe = new ShapelessRecipe(applyRandomDurability(Material.STONE_HOE, MaterialType.STONE));
		ironRecipe = new ShapelessRecipe(applyRandomDurability(Material.IRON_HOE, MaterialType.IRON));
		goldRecipe = new ShapelessRecipe(applyRandomDurability(Material.GOLD_HOE, MaterialType.GOLD));
		diamondRecipe = new ShapelessRecipe(applyRandomDurability(Material.DIAMOND_HOE, MaterialType.DIAMOND));
		
		recipes = addIngredients(Material.WOOD_HOE, woodRecipe, stoneRecipe, ironRecipe, goldRecipe, diamondRecipe);
		Arrays.stream(recipes).forEach(Superpowers.getInstance().getServer()::addRecipe);
	}
	
	public static BlacksmithPower getInstance() {
		return singleton;
	}
	
	private ItemStack applyRandomDurability(Material material, MaterialType type) {
		
		ItemStack itemStack = new ItemStack(material);
		
		switch (type) {
			case WOOD:
				itemStack.setDurability((short) new Random().nextInt(60 + 1));
				break;
			case STONE:
				itemStack.setDurability((short) new Random().nextInt(132 + 1));
				break;
			case IRON:
				itemStack.setDurability((short) new Random().nextInt(251 + 1));
				break;
			case GOLD:
				itemStack.setDurability((short) new Random().nextInt(33 + 1));
				break;
			case DIAMOND:
				itemStack.setDurability((short) new Random().nextInt(1561 + 1));
				break;
		}
		
		return itemStack;
	}
	
	private ShapelessRecipe[] addIngredients(Material template, ShapelessRecipe... recipes) {
		
		recipes[0].addIngredient(Material.WOOD);
		recipes[0].addIngredient(Material.STICK);
		
		recipes[1].addIngredient(Material.COBBLESTONE);
		recipes[1].addIngredient(Material.STICK);
		recipes[1].addIngredient(template);
		
		recipes[2].addIngredient(Material.IRON_INGOT);
		recipes[2].addIngredient(Material.STICK);
		recipes[2].addIngredient(template);
		
		recipes[3].addIngredient(Material.GOLD_INGOT);
		recipes[3].addIngredient(Material.STICK);
		recipes[3].addIngredient(template);
		
		recipes[4].addIngredient(Material.DIAMOND);
		recipes[4].addIngredient(Material.STICK);
		recipes[4].addIngredient(template);
		
		return recipes;
	}

	@Override
	public void use(CraftItemEvent event) {

		/* Check if user has a power orb */
		Player player = (Player) event.getWhoClicked();
		PowerOrb powerOrb = PowerOrb.find(player.getInventory(), EnumPower.BLACKSMITH);
		if (powerOrb == null) {
			player.sendMessage(Configuration.getString("language.noOrbInHand", EnumPower.BLACKSMITH));
			return;
		}

		powerOrb.decrementEnergy(2);
		powerOrb.update();
		
		/* Check if user is still in cooldown */
		if (CooldownManager.contains(player.getUniqueId(), this)) {
			player.sendMessage(Configuration.getString("language.powerInCooldown", EnumPower.BLACKSMITH));
			return;
		}
		
		CooldownManager.add(player.getUniqueId(), this, 3);

		//TODO Do this differently
		Superpowers.getInstance().getServer().resetRecipes();

		new BlacksmithPower();
		
	}

}
