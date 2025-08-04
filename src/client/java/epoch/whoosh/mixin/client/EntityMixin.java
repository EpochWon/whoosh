package epoch.whoosh.mixin.client;

import epoch.whoosh.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
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
		FireworkFlySoundInstance fireworkFlyInstance = new FireworkFlySoundInstance(
				entity,
				CustomSounds.FIREWORK_FLY,
				SoundCategory.NEUTRAL,
				0.0f,
				10
		);
		FireworkWhistleSoundInstance fireworkWhistleInstance = new FireworkWhistleSoundInstance(
				entity,
				CustomSounds.FIREWORK_WHISTLE,
				SoundCategory.NEUTRAL,
				0.0f,
				10
		);

		if (entity instanceof FireballEntity || entity instanceof SmallFireballEntity) {
			client.getSoundManager().play(fireballFlyInstance);
		}
		else if (entity == client.player) {
			client.getSoundManager().play(PlayerWindSoundInstance);
		}
		else if (entity instanceof FireworkRocketEntity) {
			client.getSoundManager().play(fireworkFlyInstance);
			client.getSoundManager().play(fireworkWhistleInstance);
		}
		else if (entity instanceof ProjectileEntity) {
			client.getSoundManager().play(whooshInstance);
		}

	}
}