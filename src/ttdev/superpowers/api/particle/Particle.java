package ttdev.superpowers.api.particle;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Particle {

    private EnumParticle particle;
    private boolean distantView;
    private float xOff;
    private float yOff;
    private float zOff;
    private int data;
    private int count;

    /**
     * Creates a particle of the given <code>EnumParticle</code>.
     * This particle won't exist until the particle invokes
     * the {@link #play(double, double, double, Player[])}.
     * @param particle Type of the particle.
     */
    public Particle(EnumParticle particle) {
        this.particle = particle;
    }

    /**
     * Sets the particle type.
     * @param particle
     * @return
     */
    public Particle particle(EnumParticle particle) {
        this.particle = particle;
        return this;
    }

    /**
     * Sets whether this particle will be viewed up to 256 blocks
     * or 65536 blocks away.
     * @param distantView
     * @return
     */
    public Particle distantView(boolean distantView) {
        this.distantView = distantView;
        return this;
    }

    /**
     * Sets the X offset of the particle.
     * Note that when the particle color can be modified
     * and the data is set to 1 and count is set to 0,
     * this becomes the <b>red</b> channel for creating colored particles.
     * @param xOff X offset.
     * @return
     */
    public Particle xOff(float xOff) {
        this.xOff = xOff;
        return this;
    }

    /**
     * Sets the Y offset of the particle.
     * Note that when the particle color can be modified
     * and the data is set to 1 and count is set to 0,
     * this becomes the <b>green</b> channel for creating colored particles.
     * @param yOff Y offset.
     * @return
     */
    public Particle yOff(float yOff) {
        this.yOff = yOff;
        return this;
    }

    /**
     * Sets the Z offset of the particle.
     * Note that when the particle color can be modified
     * and the data is set to 1 and count is set to 0,
     * this becomes the <b>blue</b> channel for creating colored particles.
     * @param zOff Z offset.
     * @return
     */
    public Particle zOff(float zOff) {
        this.zOff = zOff;
        return this;
    }

    /**
     * Some particles can utilize an <b>exclusive</b> data value
     * that will change its appearance or behavior.
     * @param data Particle data.
     * @return
     */
    public Particle data(int data) {
        this.data = data;
        return this;
    }

    /**
     * Sets the number of particles to spawn to the
     * specified value.
     * @param count Number of particles.
     * @return
     */
    public Particle count(int count) {
        this.count = count;
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
        Packet<?> packet = new PacketPlayOutWorldParticles(particle, distantView, xx, yy, zz, xOff, yOff, zOff, data, count);
        Arrays.stream(players).forEach(player -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet));
    }

    public void play(Location location, Player... players) {
        play(location.getX(), location.getY(), location.getZ(), players);
    }

}