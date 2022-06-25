package nl.knokko.core.plugin.entity;

import net.minecraft.world.phys.Vec3D;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.Optional;

public class EntityLineIntersection {

	public static double distanceToStart(
			Entity entity, Location lineStartLocation, 
			Vector direction, double safeUpperBound
	) {
		net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) entity).getHandle();
		Vec3D lineStart = new Vec3D(
				lineStartLocation.getX(), 
				lineStartLocation.getY(), 
				lineStartLocation.getZ()
		);
		Vec3D lineEnd = new Vec3D(
				lineStartLocation.getX() + safeUpperBound * direction.getX(),
				lineStartLocation.getY() + safeUpperBound * direction.getY(),
				lineStartLocation.getZ() + safeUpperBound * direction.getZ()
		);


		Optional<Vec3D> intersection = nmsEntity.cA().b(lineStart, lineEnd);
		if (intersection.isPresent()) {
			return Math.sqrt(intersection.get().g(lineStart));
		} else {
			return Double.POSITIVE_INFINITY;
		}
	}

}
