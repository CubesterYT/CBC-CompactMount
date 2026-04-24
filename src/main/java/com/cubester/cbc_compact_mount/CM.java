package com.cubester.cbc_compact_mount;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.cubester.cbc_compact_mount.neoforge.CMNeoForgeEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(CM.MODID)
public class CM {
	public static final String MODID = "cbc_compact_mount";
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

	public CM(IEventBus modEventBus) {
		REGISTRATE.registerEventListeners(modEventBus);

		CMBlocks.register();
		CMEntities.register();
		CMCreativeTabs.register(modEventBus);

		modEventBus.addListener(CMNeoForgeEvents::onRegister);
	}

	public void onServerStarting(ServerStartingEvent event) {
		LOGGER.info("Hello from CBC: Compact Mount!");
	}
}