package rodrigo.rferreiro;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import rodrigo.rferreiro.eventos.AnvilBlockers;
import rodrigo.rferreiro.eventos.InventoryEvents;
import rodrigo.rferreiro.eventos.PlayerInteract;

public class Main extends JavaPlugin{
	
	public static HashMap<Player, ArmorStand> reparado = new HashMap<>();
	public static HashMap<Player, Location> bigornas = new HashMap<Player, Location>();
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryEvents(), this);
		Bukkit.getPluginManager().registerEvents(new AnvilBlockers(), this);
	}
	
	@Override
	public void onDisable() {
		bigornas.clear();
		for (Player pl : reparado.keySet()) {
			pl.getInventory().addItem(reparado.get(pl).getItemInHand());
			reparado.get(pl).remove();
			reparado.remove(pl);
		}
	}

}
