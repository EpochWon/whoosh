package epoch.whoosh;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Whoosh implements ModInitializer {
	public static final String MOD_ID = "whoosh";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CustomSounds.initialize();

		LOGGER.info("Whoosh Initialized!");
	}
}