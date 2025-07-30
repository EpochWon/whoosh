package epoch.whoosh;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;

public class WhooshSoundInstance extends MovingSoundInstance {
    private final Entity entity;
    private Vec3d lastPos = null;

    public WhooshSoundInstance(Entity entity, SoundEvent soundEvent, SoundCategory soundCategory, float volume, float pitch, AttenuationType attenuation) {
        super(soundEvent, soundCategory, SoundInstance.createRandom());

        this.entity = entity;

        this.volume = volume;
        this.pitch = pitch;
        this.repeat = true;
        this.setPositionToEntity();
        this.attenuationType = attenuation;
    }

    @Override
    public void tick() {
        if (this.entity == null || this.entity.isRemoved()) {
            this.setDone();
            return;
        }

        float velocity;
        if (this.entity instanceof ArrowEntity arrow) {
            // arrow velocity is wack and isn't reliable so calculate it manually

            Vec3d currentPos = new Vec3d(entity.getX(), entity.getY(), entity.getZ());

            if (lastPos != null) {
                Vec3d delta = currentPos.subtract(lastPos);
                velocity = (float) delta.lengthSquared() * 20.F;
            } else {
                velocity = 0.F;
            }

            lastPos = currentPos;

            if (arrow.isOnGround() || arrow.isInsideWall()) {
                this.volume = 0.F;
            } else {
                this.volume = Math.min(0.6F, velocity * 0.2F);
            }
        } else {
            velocity = (float) entity.getVelocity().lengthSquared();

            if (velocity < 0.2F || entity.isInFluid()) {
                this.volume = 0F;
            } else {
                this.volume = Math.min(1.0F, velocity * 0.2F);
            }
        }

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
