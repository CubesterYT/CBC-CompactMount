package com.cubester.cbc_compact_mount.neoforge;

import com.cubester.cbc_compact_mount.compat.CMArmInteractionPointTypes;
import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

public class CMNeoForgeEvents {
    @SubscribeEvent
    public static void onRegister(RegisterEvent evt) {
        evt.register(
                CreateBuiltInRegistries.ARM_INTERACTION_POINT_TYPE.key(),
                helper -> CMArmInteractionPointTypes.init());
    }
}
