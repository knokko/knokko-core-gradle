package nl.knokko.core.plugin.entity;

import net.minecraft.server.v1_13_R2.DamageSource;
import net.minecraft.server.v1_13_R2.EntityDamageSource;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;

import net.minecraft.server.v1_13_R2.EntityDamageSourceIndirect;
import net.minecraft.server.v1_13_R2.EntitySmallFireball;

public class EntityDamageHelper {

	public static void causeFakeProjectileDamage(Entity toDamage, Entity responsibleShooter, float damage,
			double projectilePositionX, double projectilePositionY, double projectilePositionZ,
			double projectileMotionX, double projectileMotionY, double projectileMotionZ) {
		
		((CraftEntity) toDamage).getHandle().damageEntity(new EntityDamageSourceIndirect("thrown", 
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

		((CraftEntity) target).getHandle().damageEntity(damageSource, damage);
	}

	private static class CustomEntityDamageSource extends EntityDamageSource {

		public CustomEntityDamageSource(String name, net.minecraft.server.v1_13_R2.Entity attacker) {
			super(name, attacker);
		}

		public CustomEntityDamageSource setIgnoreArmor(boolean ignoreArmor) {
			if (ignoreArmor) {
				super.setIgnoreArmor();
			}
			return this;
		}

		public CustomEntityDamageSource setFire(boolean isFire) {
			if (isFire) {
				// Ehm... yes... it looks like the deobfuscater made a mistake: setExplosion() should have been called setFire()
				super.setExplosion();
			}
			return this;
		}
	}

}
