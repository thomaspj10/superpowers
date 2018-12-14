package ttdev.superpowers.particle.animation;

import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.entity.Player;
import ttdev.superpowers.api.particle.Animation;
import ttdev.superpowers.api.particle.ColoredParticle;

public class LineFlux extends Animation {

    double step = 0.2;
    double theta = 0;
    double radius = 0.4;

    @Override
    public void define(double x, double y, double z, Player... players) {
        ColoredParticle purpleParticle =
                new ColoredParticle(EnumParticle.REDSTONE)
                        .red(255)
                        .green(10)
                        .blue(255)
                        .distantView(true);

        double xx = (Math.cos(theta) * radius) + x;
        double yy = (Math.sin(theta) * radius) + y;
        double zz = (Math.cos(theta) * radius) + z;
        purpleParticle.play(xx, yy, zz, players);
        theta += step;

        if (theta > 360) {
            theta = 0;
        }

    }

}
