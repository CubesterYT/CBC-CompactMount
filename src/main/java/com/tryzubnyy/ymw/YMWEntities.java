package com.tryzubnyy.ymw;

import com.simibubi.create.foundation.data.CreateBlockEntityBuilder;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tryzubnyy.ymw.content.CompactCannonMountBlockEntity;
import com.tryzubnyy.ymw.content.CompactCannonMountRenderer;
import com.tryzubnyy.ymw.content.CompactCannonMountVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class YMWEntities {
	public static final BlockEntityEntry<CompactCannonMountBlockEntity> COMPACT_CANNON_MOUNT = ((CreateBlockEntityBuilder<CompactCannonMountBlockEntity, CreateRegistrate>) YMW.REGISTRATE
		.blockEntity("compact_cannon_mount", CompactCannonMountBlockEntity::new)
		.validBlocks(YMWBlocks.COMPACT_CANNON_MOUNT)
		.renderer(() -> CompactCannonMountRenderer::new))
		.visual(() -> CompactCannonMountVisual::new)
		.register();

	public static void register() {}
}