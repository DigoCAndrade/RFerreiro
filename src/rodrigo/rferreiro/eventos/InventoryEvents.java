package rodrigo.rferreiro.eventos;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import rodrigo.rferreiro.Main;

public class InventoryEvents implements Listener {
	
	public static ArrayList<Player> fecharinventario = new ArrayList<Player>();

	@EventHandler
	public void aoClicarInventario(InventoryClickEvent e) {
		if (!e.getInventory().getTitle().equals("Deseja reparar seu item?") || e.getInventory().getHolder() != null) return;
		if (!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		e.setCancelled(true);
		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		if (e.getSlot() != 11 && e.getSlot() != 15) return;
		if (e.getSlot() == 11) {
			fecharinventario.add(p);
			p.closeInventory();
			startAnimation(p, Main.bigornas.get(p).getBlock().getLocation().add(0.5, 1.1, 0.5));
			p.sendMessage("§aReparando...");
		} else if (e.getSlot() == 15) {
			p.closeInventory();
		}
	}
	
	@EventHandler
	public void aoFecharInventario(InventoryCloseEvent e) {
		if (!e.getInventory().getTitle().equals("Deseja reparar seu item?") || e.getInventory().getHolder() != null) return;
		if (!(e.getPlayer() instanceof Player)) return;
		Player p = (Player) e.getPlayer();
		if (fecharinventario.contains(p)) {
			fecharinventario.remove(p);
			return;
		}
		p.getInventory().addItem(Main.reparado.get(p).getItemInHand());
		p.updateInventory();
		Main.reparado.get(p).remove();
		Main.reparado.remove(p);
		Main.bigornas.remove(p);
		p.sendMessage("§cVocê cancelou a reparação com sucesso.");
	}
	
	
	@SuppressWarnings("deprecation")
	public static void startAnimation(Player p, Location l) {
		Location lcert = new Location(l.getWorld(), l.getX() - 0.5, l.getY() - 1.1, l.getZ() - 0.5);
		
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new BukkitRunnable() {
			
			@Override
			public void run() {
				if (p == null) return;
				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.LAVA, true, (float) l.getX(), (float) l.getY(), (float) l.getZ(), 0, 0, 0, 10, 10, null);
				for (Entity e : p.getNearbyEntities(16, 32, 16)) {
					if (e instanceof Player) {
						Player pl = (Player) e;
						((CraftPlayer) pl).getHandle().playerConnection.sendPacket(packet);
						pl.playSound(p.getLocation(), Sound.IRONGOLEM_HIT, 10f, 0.5f);
					}
				}
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
				p.playSound(p.getLocation(), Sound.IRONGOLEM_HIT, 10f, 0.5f);
			}
		}, 15);
		
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new BukkitRunnable() {
			
			@Override
			public void run() {
				if (p == null) return;
				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.LAVA, true, (float) l.getX(), (float) l.getY(), (float) l.getZ(), 0, 0, 0, 10, 10, null);
				for (Entity e : p.getNearbyEntities(16, 32, 16)) {
					if (e instanceof Player) {
						Player pl = (Player) e;
						((CraftPlayer) pl).getHandle().playerConnection.sendPacket(packet);
						pl.playSound(p.getLocation(), Sound.IRONGOLEM_HIT, 10f, 0.5f);
					}
				}
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
				p.playSound(p.getLocation(), Sound.IRONGOLEM_HIT, 10f, 0.5f);
			}
		}, 30);
		
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new BukkitRunnable() {
			
			@Override
			public void run() {
				if (p == null) return;
				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.LAVA, true, (float) l.getX(), (float) l.getY(), (float) l.getZ(), 0, 0, 0, 10, 10, null);
				for (Entity e : p.getNearbyEntities(16, 32, 16)) {
					if (e instanceof Player) {
						Player pl = (Player) e;
						((CraftPlayer) pl).getHandle().playerConnection.sendPacket(packet);
						pl.playSound(p.getLocation(), Sound.IRONGOLEM_HIT, 10f, 0.5f);
					}
				}
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
				p.playSound(p.getLocation(), Sound.IRONGOLEM_HIT, 10f, 0.5f);
			}
		}, 45);
		
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new BukkitRunnable() {
			
			@Override
			public void run() {
				if (p == null) return;
				l.getWorld().strikeLightningEffect(lcert);
			}
		}, 60);
		
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new BukkitRunnable() {
			
			@Override
			public void run() {
				if (p == null) return;
				l.getWorld().strikeLightningEffect(lcert);
			}
		}, 65);
		
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new BukkitRunnable() {
			
			@Override
			public void run() {
				if (p == null) return;
				l.getWorld().strikeLightningEffect(lcert);
			}
		}, 70);
		
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new BukkitRunnable() {
			
			@Override
			public void run() {
				if (p == null) return;
				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, true, (float) l.getX(), (float) l.getY(), (float) l.getZ(), 0, 0, 0, 10, 1, null);
				for (Entity e : p.getNearbyEntities(16, 32, 16)) {
					if (e instanceof Player) {
						Player pl = (Player) e;
						((CraftPlayer) pl).getHandle().playerConnection.sendPacket(packet);
						pl.playSound(p.getLocation(), Sound.EXPLODE, 1f, 1f);
					}
				}
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
				p.playSound(p.getLocation(), Sound.EXPLODE, 1f, 1f);
				ItemStack item = Main.reparado.get(p).getItemInHand().clone();
				item.setDurability((short) 0);
				p.getInventory().addItem(item);
				Main.reparado.get(p).remove();
				Main.reparado.remove(p);
				Main.bigornas.remove(p);
				p.updateInventory();
				p.sendMessage("§aVocê reparou seu item com sucesso.");
			}
		}, 80);
	}
}
