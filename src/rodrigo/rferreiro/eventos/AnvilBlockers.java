package rodrigo.rferreiro.eventos;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import rodrigo.rferreiro.Main;

public class AnvilBlockers implements Listener{
	
	@EventHandler(priority = EventPriority.LOW)
	public void aoQuebrarBlock(BlockBreakEvent e) {
		if (Main.bigornas.containsValue(e.getBlock().getLocation())) {
			e.getPlayer().sendMessage("§cVocê não pode quebrar esta bigorna enquanto estiver alguém reparando.");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void aoSair(PlayerQuitEvent e) {
		if (Main.bigornas.containsKey(e.getPlayer())) {
			Player p = e.getPlayer();
			InventoryEvents.fecharinventario.add(p);
			p.getInventory().addItem(Main.reparado.get(p).getItemInHand());
			p.updateInventory();
			Main.reparado.get(p).remove();
			Main.reparado.remove(p);
			Main.bigornas.remove(p);
		}
	}
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent e) {
		if (Main.bigornas.containsKey(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void reciveItem(PlayerPickupItemEvent e) {
		if (Main.bigornas.containsKey(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (Main.bigornas.containsKey(e.getEntity())) {
			Player p = e.getEntity();
			InventoryEvents.fecharinventario.add(p);
			p.getWorld().dropItem(p.getLocation(), Main.reparado.get(p).getItemInHand());
			p.updateInventory();
			Main.reparado.get(p).remove();
			Main.reparado.remove(p);
			Main.bigornas.remove(p);
		}
	}
	
	@EventHandler
	public void aoManipularAmorStand(PlayerArmorStandManipulateEvent e) {
		if (Main.reparado.containsValue(e.getRightClicked())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void aoEquiparArmorStand(PlayerInteractAtEntityEvent e) {
		if (!(e.getRightClicked() instanceof ArmorStand)) return;
		ArmorStand as = (ArmorStand) e.getRightClicked();
		if (Main.reparado.containsValue(as)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void aoDigitarComando(PlayerCommandPreprocessEvent e) {
		if (Main.reparado.containsKey(e.getPlayer())) {
			e.getPlayer().sendMessage("§cVocê não pode digitar comando enquanto estiver reparando.");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void aoDigitarComando(InventoryClickEvent e) {
		if (e.getInventory().getHolder() == null) return;
		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		if (!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		if (Main.reparado.containsKey(p)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void aoBaterArmorStand(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof ArmorStand)) return;
		if (!(e.getDamager() instanceof LivingEntity)) return;
		ArmorStand as = (ArmorStand) e.getEntity();
		if (Main.reparado.containsValue(as)) {
			e.setCancelled(true);
		}
	}
}
