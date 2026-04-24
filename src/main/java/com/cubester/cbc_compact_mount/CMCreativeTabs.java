package com.cubester.cbc_compact_mount;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CMCreativeTabs {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister
			.create(Registries.CREATIVE_MODE_TAB, CM.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.cbc_compact_mount.main"))
                    .icon(() -> new ItemStack(CMBlocks.COMPACT_CANNON_MOUNT.get()))
                    .displayItems((parameters, output) -> {
                        //output.accept(CMBlocks.COMPACT_CANNON_MOUNT.get());
                    })
                    .build());

	public static void register(IEventBus eventBus) {
		CREATIVE_TABS.register(eventBus);
	}
}