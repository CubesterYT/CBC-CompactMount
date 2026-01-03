package com.tryzubnyy.ymw;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class YMWCreativeTabs {

	public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, YMW.MODID);

	public static final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_TABS.register("main",
		() -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup.ymw.main"))
			.icon(() -> new ItemStack(YMWBlocks.COMPACT_CANNON_MOUNT.get()))
			.displayItems((parameters, output) -> {
				output.accept(YMWBlocks.COMPACT_CANNON_MOUNT.get());
			})
			.build());

	public static void register(IEventBus eventBus) {
		CREATIVE_TABS.register(eventBus);
	}
}