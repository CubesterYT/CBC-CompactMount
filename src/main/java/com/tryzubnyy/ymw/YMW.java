package com.tryzubnyy.ymw;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(YMW.MODID)
public class YMW {
	public static final String MODID = "ymw";
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

	public YMW() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		REGISTRATE.registerEventListeners(modEventBus);

		YMWBlocks.register();
		YMWEntities.register();
		YMWCreativeTabs.register(modEventBus);
	}

	public void onServerStarting(ServerStartingEvent event) {
		LOGGER.info("Вітаю!");
	}
}