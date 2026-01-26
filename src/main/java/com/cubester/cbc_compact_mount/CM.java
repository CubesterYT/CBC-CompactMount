package com.cubester.cbc_compact_mount;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.cubester.cbc_compact_mount.forge.CMForgeEvents;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(CM.MODID)
public class CM {
	public static final String MODID = "cbc_compact_mount";
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

	public CM() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		REGISTRATE.registerEventListeners(modEventBus);

		CMBlocks.register();
		CMEntities.register();
		CMCreativeTabs.register(modEventBus);

		modEventBus.addListener(CMForgeEvents::onRegister);
	}

	public void onServerStarting(ServerStartingEvent event) {
		LOGGER.info("Hello from CBC: Compact Mount!");
	}
}