package dev.keziumo.fomcreconfigfix;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fomcreconfigfix implements ModInitializer {
	public static final String MOD_ID = "FOMC-Reconfig-Fix";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("[FOMC-Reconfig-Fix] Mod initialized!");
	}
}
