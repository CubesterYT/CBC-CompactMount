package com.cubester.cbc_compact_mount.forge;

import com.cubester.cbc_compact_mount.compat.CMArmInteractionPointTypes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegisterEvent;

public class CMForgeEvents {
    @SubscribeEvent
    public static void onRegister(RegisterEvent evt) {
        CMArmInteractionPointTypes.init();
    }
}
