package epoch.whoosh.mixin.client;

import epoch.whoosh.CustomSounds;
import epoch.whoosh.WhooshSoundInstance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
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
		WhooshSoundInstance whooshInstance = new WhooshSoundInstance(entity, CustomSounds.ARROW_WHOOSH, SoundCategory.NEUTRAL, 0.0f, 1.5f, SoundInstance.AttenuationType.LINEAR);
		WhooshSoundInstance playerWhooshInstance = new WhooshSoundInstance(entity, CustomSounds.ARROW_WHOOSH, SoundCategory.NEUTRAL, 0.0f, 1.5f, SoundInstance.AttenuationType.NONE);

		if (entity instanceof ProjectileEntity || entity instanceof FireballEntity) {
			client.getSoundManager().play(whooshInstance);
		}

		if (entity == client.player) {
			client.getSoundManager().play(playerWhooshInstance);
		}
	}
}