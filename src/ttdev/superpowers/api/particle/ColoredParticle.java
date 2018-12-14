package ttdev.superpowers.api.particle;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ColoredParticle {

    private EnumParticle particle;
    private boolean distantView;
    private float red;
    private float green;
    private float blue;

    /**
     * Creates a particle of the given <code>EnumParticle</code>.
     * This particle won't exist until the particle invokes
     * the {@link #play(double, double, double, Player[])}.
     * @param particle Type of the particle.
     */
    public ColoredParticle(EnumParticle particle) {
        this.particle = particle;
    }

    /**
     * Sets the particle type.
     * @param particle
     * @return
     */
    public ColoredParticle particle(EnumParticle particle) {
        this.particle = particle;
        return this;
    }

    /**
     * Sets whether this particle will be viewed up to 256 blocks
     * or 65536 blocks away.
     * @param distantView
     * @return
     */
    public ColoredParticle distantView(boolean distantView) {
        this.distantView = distantView;
        return this;
    }

    public ColoredParticle red(float value) {
        red = value;
        return this;
    }

    public ColoredParticle red(int value) {
        if (value == 0) {
            red = 0.001f;
        } else {
            red = value / 255f;
        }
        return this;
    }

    public ColoredParticle green(float value) {
        green = value;
        return this;
    }

    public ColoredParticle green(int value) {
        green = value / 255f;
        return this;
    }

    public ColoredParticle blue(float value) {
        blue = value;
        return this;
    }

    public ColoredParticle blue(int value) {
        blue = value / 255f;
        return this;
    }

    /**
     * Sends the particle packet to the specified list of players.
     * @param players Players to send the packet to.
     */
    public void play(double x, double y, double z, Player... players) {
        float xx = (float) x + 0.5f;
        float yy = (float) y + 0.5f;
        float zz = (float) z + 0.5f;
        Packet<?> packet = new PacketPlayOutWorldParticles(particle, distantView, xx, yy, zz, red, green, blue, 1, 0);
        Arrays.stream(players).forEach(player -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet));
    }

    public void play(Location location, Player... players) {
        play(location.getX(), location.getY(), location.getZ(), players);
    }

}
