package epoch.whoosh;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class FireworkFlySoundInstance extends MovingSoundInstance {
    private final Entity entity;
    float velocity = 0.F;
    private final int fadeInTicks;
    private int elapsedTicks = 0;

    public FireworkFlySoundInstance(Entity entity, SoundEvent soundEvent, SoundCategory soundCategory, float volume, int fadeInTicks) {
        super(soundEvent, soundCategory, SoundInstance.createRandom());

        this.entity = entity;

        this.volume = 0.0F;
        this.pitch = 0.8F + (random.nextFloat() * 0.4F);
        this.repeat = true;
        this.setPositionToEntity();
        this.attenuationType = AttenuationType.LINEAR;
        this.fadeInTicks = fadeInTicks;
    }

    @Override
    public void tick() {
        if (this.entity == null || this.entity.isRemoved()) {
            this.setDone();
            return;
        }

        elapsedTicks++;

            velocity = (float) entity.getVelocity().lengthSquared();

        this.volume = Math.min(0.8F, velocity * 0.2F);
        if (velocity < 0.2F || entity.isInFluid()) {
            this.volume = 0F;
        }

        float fadeMultiplier = fadeInTicks > 0 ? Math.min(1.0F, elapsedTicks / (float) fadeInTicks) : 1.0F;
        this.volume = getVolume() * fadeMultiplier;

        this.setPositionToEntity();
    }

    @Override
    public boolean shouldAlwaysPlay() {
        return true;
    }

    private void setPositionToEntity() {
        this.x = this.entity.getX();
        this.y = this.entity.getY();
        this.z = this.entity.getZ();
    }
}
