package ttdev.superpowers.api.particle;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ttdev.superpowers.Superpowers;

import java.util.Arrays;
import java.util.function.BiConsumer;

public class ParticleLine implements ParticleModel {

	private Location start, end;
	private double distance;
	private double startX, startY, startZ;
	private double endX, endY, endZ;
	private double xDif, yDif, zDif;
	private double xInc, yInc, zInc;
	private double xSpeed, ySpeed, zSpeed;
	private long playSpeed;

	private ParticleProperties properties;
	private ParticleModel lineModel, endModel;

	private BiConsumer<Location, Player[]> endFunc;

	public ParticleLine(Location start, Location end) {
		this.start = start;
		this.end = end;
		distance = start.distance(end);

		properties = new ParticleProperties(0.5f);

		setCoordinates();
		setDifference();
		setIncrements();
		setSpeeds();
	}

	private class ParticleProperties {

		float density;

		private ParticleProperties(float density) {
			this.density = density;
		}

	}

	private void setCoordinates() {
		startX = start.getX();
		startY = start.getY();
		startZ = start.getZ();
		endX = end.getX();
		endY = end.getY();
		endZ = end.getZ();
	}

	private void setDifference() {
		xDif = endX - startX;
		yDif = endY - startY;
		zDif = endZ - startZ;
	}

	private void setIncrements() {
		xInc = xDif / distance;
		yInc = yDif / distance;
		zInc = zDif / distance;
	}

	private void setSpeeds() {
		xSpeed = xInc * properties.density;
		ySpeed = yInc * properties.density;
		zSpeed = zInc * properties.density;
	}

	public ParticleLine density(float density) {
		properties.density = density;
		setSpeeds();
		return this;
	}

	public ParticleLine speed(long playSpeed) {
		this.playSpeed = playSpeed;
		return this;
	}

	public ParticleLine lineModel(ParticleModel lineModel) {
		this.lineModel = lineModel;
		return this;
	}

	public ParticleLine endModel(ParticleModel endModel) {
		this.endModel = endModel;
		return this;
	}

	public ParticleLine onEnd(BiConsumer<Location, Player[]> endFunc) {
		this.endFunc = endFunc;
		return this;
	}

	@Override
	public void play(Location location, Player... players) {
		
		new BukkitRunnable() {

			Location currentLocation = start;
			double x, y, z;

			public void run() {
				/*
				 * While the current location of playing particles isn't equal to the ending
				 * location 'end' continue playing the effect.
				 */
				for (;;) {

					double distance = currentLocation.distance(end);
					if (distance < properties.density) {
						// When the projectile hits the target
						Arrays.stream(players).forEach(player -> endModel.play(currentLocation, players));
						endFunc.accept(end, players);
						break;
					}

					/* Update the x, y, and z to the next particle location */
					x = currentLocation.getX();
					y = currentLocation.getY();
					z = currentLocation.getZ();

					/* Sleep... Zzz */
					try {
						Thread.sleep(playSpeed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					/* Send the packet to the players. */
					Arrays.stream(players).forEach(player -> {
						lineModel.play(currentLocation, players);
					});

					/* Set the next location to play the particle at */
					currentLocation = currentLocation.add(xSpeed, ySpeed, zSpeed);
				}
			}
		}.runTaskAsynchronously(Superpowers.getInstance());
	}

}
