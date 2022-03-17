package nl.knokko.core.plugin.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;

import net.minecraft.world.damagesource.EntityDamageSourceIndirect;
import net.minecraft.world.entity.projectile.EntitySmallFireball;

public class EntityDamageHelper {

	public static void causeFakeProjectileDamage(Entity toDamage, Entity responsibleShooter, float damage,
			double projectilePositionX, double projectilePositionY, double projectilePositionZ,
			double projectileMotionX, double projectileMotionY, double projectileMotionZ) {
		
		((CraftEntity) toDamage).getHandle().a(new EntityDamageSourceIndirect("thrown",
				new EntitySmallFireball(((CraftWorld) toDamage.getWorld()).getHandle(), 
				projectilePositionX, projectilePositionY, projectilePositionZ, 
				projectileMotionX, projectileMotionY, projectileMotionZ), 
				((CraftEntity) responsibleShooter).getHandle()), damage);
	}

	public static void causeCustomPhysicalAttack(
			Entity attacker, Entity target, float damage,
			String damageCauseName, boolean ignoresArmor, boolean isFire
	) {
		DamageSource damageSource = new CustomEntityDamageSource(damageCauseName, ((CraftEntity) attacker).getHandle())
				.setIgnoreArmor(ignoresArmor).setFire(isFire);

		((CraftEntity) target).getHandle().a(damageSource, damage);
	}

	private static class CustomEntityDamageSource extends EntityDamageSource {

		public CustomEntityDamageSource(String name, net.minecraft.world.entity.Entity attacker) {
			super(name, attacker);
		}

		public CustomEntityDamageSource setIgnoreArmor(boolean ignoreArmor) {
			if (ignoreArmor) {
				super.m();
			}
			return this;
		}

		public CustomEntityDamageSource setFire(boolean isFire) {
			if (isFire) {
				super.q();
			}
			return this;
		}
	}
}
