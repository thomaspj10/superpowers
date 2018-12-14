package ttdev.superpowers.api.particle;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface ParticleModel {

    void play(Location location, Player... players);

}
