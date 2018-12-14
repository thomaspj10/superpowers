package ttdev.superpowers.api.particle;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ttdev.superpowers.Superpowers;

public abstract class Animation implements ParticleModel {

    /**
     * Contains the instructions for the particle animation. Override
     * this method in a subclass of <code>Animation</code> to create your
     * own animations.
     * @param players The players that will see the animation.
     * @param x The x location of the animation.
     * @param y The y location of the animation.
     * @param z The z location of the animation.
     */
    public abstract void define(double x, double y, double z, Player... players);

    /**
     * Handles playing the animation in a separate thread. Overriding this method
     * usually won't be necessary.
     * @param location Location of the animation.
     * @param players Players that will see the animation.
     */
    public void play(Location location, Player... players) {
        new BukkitRunnable() {
            @Override
            public void run() {
                define(location.getX(), location.getY(), location.getZ(), players);
            }
        }.runTask(Superpowers.getInstance());
    }

    /**
     * Causes the animation thread to sleep for the specified amount of time
     * in milliseconds.
     * @param millis Time to sleep in milliseconds.
     */
    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}