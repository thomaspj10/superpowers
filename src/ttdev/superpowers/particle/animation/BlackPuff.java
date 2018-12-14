package ttdev.superpowers.particle.animation;

import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.entity.Player;
import ttdev.superpowers.api.particle.Animation;
import ttdev.superpowers.api.particle.Particle;

public class BlackPuff extends Animation {

    @Override
    public void define(double x, double y, double z, Player... players) {
        Particle smokeParticle = new Particle(EnumParticle.SMOKE_LARGE);
        for (int i = 0; i < 20; i++) {
            smokeParticle
                    .count(2)
                    .data(0)
                    .distantView(true)
                    .xOff(0.25f)
                    .yOff(0.25f)
                    .zOff(0.25f)
                    .play(x, y, z, players);

            smokeParticle.play(x, y + 1, z, players);
        }
    }

}
