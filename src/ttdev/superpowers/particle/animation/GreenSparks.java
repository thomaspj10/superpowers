package ttdev.superpowers.particle.animation;

import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.entity.Player;
import ttdev.superpowers.api.particle.Animation;
import ttdev.superpowers.api.particle.Particle;

public class GreenSparks extends Animation {

    @Override
    public void define(double x, double y, double z, Player... players) {

        Particle greenSpark = new Particle(EnumParticle.VILLAGER_HAPPY)
                .count(1)
                .data(0)
                .distantView(true);

        for (int i = 0; i < 50; i++) {
            greenSpark
                    .xOff((float) Math.random() * 2f)
                    .yOff((float) Math.random() * 0.25f)
                    .zOff((float) Math.random() * 2f);

            greenSpark.play(x, y, z, players);
        }
    }
}
