package epoch.whoosh;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class PlayerWindSoundInstance extends MovingSoundInstance {
    private final Entity entity;
    float velocity = 0.F;

    private float lerp(float current, float target, float factor) {
        return current + (target - current) * factor;
    }

    public PlayerWindSoundInstance(Entity entity, SoundEvent soundEvent, SoundCategory soundCategory, float volume, float pitch) {
        super(soundEvent, soundCategory, SoundInstance.createRandom());

        this.entity = entity;

        this.volume = volume;
        this.pitch = pitch;
        this.repeat = true;
        this.setPositionToEntity();
        this.attenuationType = AttenuationType.NONE;
    }

    @Override
    public void tick() {
        if (this.entity == null || this.entity.isRemoved()) {
            this.setDone();
            return;
        }

        velocity = (float) entity.getVelocity().lengthSquared();

        float targetVolume = velocity * 0.2F;
        float targetPitch = 1.0F + velocity * 0.3F;
        if (velocity < 0.2F || entity.isInFluid()) {
            targetVolume = 0F;
        }

        this.volume = lerp(this.volume, targetVolume, 0.5F);
        this.pitch = lerp(this.pitch, targetPitch, 0.5F);

        this.setPositionToEntity();
    }

    @Override
    public boolean shouldAlwaysPlay() {
        return true;
    }

    private void setPositionToEntity() {
        this.x = this.entity.getEyePos().getX();
        this.y = this.entity.getEyePos().getY();
        this.z = this.entity.getEyePos().getZ();
    }
}
