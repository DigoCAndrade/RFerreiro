package rodrigo.rferreiro.eventos;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import rodrigo.rferreiro.Main;
import rodrigo.rferreiro.apis.ItemBuilder;

public class PlayerInteract implements Listener {

	@EventHandler
	public void aoInteragirBigorna(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null || e.getClickedBlock().getType() == Material.AIR || e.getClickedBlock().getType() != Material.ANVIL) return;
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		e.setCancelled(true);
		Player p = e.getPlayer();
		if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR)
			return;
		if (!p.getItemInHand().getType().toString().contains("_SWORD") && p.getItemInHand().getType() != Material.BOW
				&& !p.getItemInHand().getType().toString().contains("_PICKAXE")
				&& !p.getItemInHand().getType().toString().contains("_AXE")
				&& !p.getItemInHand().getType().toString().contains("_SPADE")
				&& !p.getItemInHand().getType().toString().contains("_HOE")
				&& !p.getItemInHand().getType().toString().contains("_HELMET")
				&& !p.getItemInHand().getType().toString().contains("_CHESTPLATE")
				&& !p.getItemInHand().getType().toString().contains("_LEGGINGS")
				&& !p.getItemInHand().getType().toString().contains("_BOOTS")) {
			p.sendMessage("§cEste item não pode ser reparado.");
			return;
		}

		if (p.getItemInHand().getDurability() == 0) {
			p.sendMessage("§cEste item não esta quebrado.");
			return;
		}

		if (Main.bigornas.containsValue(e.getClickedBlock().getLocation())) {
			p.sendMessage("§cAlguém já esta utilizando esta bigorna.");
			return;
		}
		
		Main.bigornas.put(p, e.getClickedBlock().getLocation());

		if (p.getItemInHand().getType().toString().contains("_HELMET")
				|| p.getItemInHand().getType().toString().contains("_CHESTPLATE")
				|| p.getItemInHand().getType().toString().contains("_LEGGINGS")
				|| p.getItemInHand().getType().toString().contains("_BOOTS")) {
			ArmorStand reparando = (ArmorStand) e.getClickedBlock().getWorld().spawnEntity(e.getClickedBlock().getLocation().add(0.5, -0.9, 0.55), EntityType.ARMOR_STAND);
			reparando.setCustomNameVisible(false);
			reparando.setVisible(false);
			reparando.setBasePlate(false);
			reparando.setArms(true);
			reparando.setGravity(false);
			reparando.setRightArmPose(new EulerAngle(Math.toRadians(180f), Math.toRadians(95f), Math.toRadians(0)));
			reparando.setItemInHand(p.getItemInHand());
			Main.reparado.put(p, reparando);
		} else {
			ArmorStand reparando = (ArmorStand) e.getClickedBlock().getWorld().spawnEntity(e.getClickedBlock().getLocation().add(0.4, -0.4, 0), EntityType.ARMOR_STAND);
			reparando.setCustomNameVisible(false);
			reparando.setVisible(false);
			reparando.setBasePlate(false);
			reparando.setArms(true);
			reparando.setGravity(false);
			reparando.setRightArmPose(new EulerAngle(Math.toRadians(260f), Math.toRadians(0f), Math.toRadians(90f)));
			reparando.setItemInHand(p.getItemInHand());
			Main.reparado.put(p, reparando);
		}
		Inventory confirmar = Bukkit.createInventory(null, 3 * 9, "Deseja reparar seu item?");
		
		confirmar.setItem(11, new ItemBuilder(Material.STAINED_CLAY).dyeColor(DyeColor.MAGENTA).name("§aConfirmar").lore("§7Clique para iniciar o processo ", "§7de reparação do seu item.").build());
		confirmar.setItem(13, p.getItemInHand().clone());
		confirmar.setItem(15, new ItemBuilder(Material.STAINED_CLAY).dyeColor(DyeColor.ORANGE).name("§cRecusar").lore("§7Clique para cancelar o processo ", "§7de reparação do seu item.").build());
		
		p.setItemInHand(new ItemStack(Material.AIR));
		new BukkitRunnable() {
			
			@Override
			public void run() {
				p.openInventory(confirmar);
			}
		}.runTaskLater(Main.getPlugin(Main.class), 20);
	}

}
