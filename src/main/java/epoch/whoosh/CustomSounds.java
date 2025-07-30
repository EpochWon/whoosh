package epoch.whoosh;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class CustomSounds {
    private CustomSounds() {}

    public static final SoundEvent ARROW_WHOOSH = registerSound("arrow_whoosh");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = Identifier.of(Whoosh.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void initialize() {
        Whoosh.LOGGER.info("Registering " + Whoosh.MOD_ID + " Sounds");
    }
}
