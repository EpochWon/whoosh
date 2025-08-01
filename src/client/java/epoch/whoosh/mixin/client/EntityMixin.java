package epoch.whoosh.mixin.client;

import epoch.whoosh.CustomSounds;
import epoch.whoosh.PlayerWindSoundInstance;
import epoch.whoosh.WhooshSoundInstance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class EntityMixin {
	@Inject(method = "addEntity", at = @At("TAIL"))
	public void addEntity(Entity entity, CallbackInfo ci) {
		MinecraftClient client = MinecraftClient.getInstance();

		WhooshSoundInstance whooshInstance = new WhooshSoundInstance(
				entity,
				CustomSounds.ARROW_WHOOSH,
				SoundCategory.NEUTRAL,
				0.0f,
				1.5f,
				SoundInstance.AttenuationType.LINEAR,
				10
		);
		PlayerWindSoundInstance PlayerWindSoundInstance = new PlayerWindSoundInstance(
				entity,
				CustomSounds.ARROW_WHOOSH,
				SoundCategory.NEUTRAL,
				0.0f,
				1.5f
		);
		WhooshSoundInstance fireballFlyInstance = new WhooshSoundInstance(
				entity,
				CustomSounds.FIREBALL_FLY,
				SoundCategory.NEUTRAL,
				0.0f,
				1.0f,
				SoundInstance.AttenuationType.LINEAR,
				5
		);

		if (entity instanceof ProjectileEntity && !(entity instanceof FireballEntity || entity instanceof SmallFireballEntity)) {
			client.getSoundManager().play(whooshInstance);
		}

		if (entity instanceof FireballEntity || entity instanceof SmallFireballEntity) {
			client.getSoundManager().play(fireballFlyInstance);
		}

		if (entity == client.player) {
			client.getSoundManager().play(PlayerWindSoundInstance);
		}
	}
}