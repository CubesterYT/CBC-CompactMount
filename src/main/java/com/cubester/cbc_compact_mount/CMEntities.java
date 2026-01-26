package com.cubester.cbc_compact_mount;

import com.simibubi.create.foundation.data.CreateBlockEntityBuilder;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.cubester.cbc_compact_mount.content.CompactCannonMountBlockEntity;
import com.cubester.cbc_compact_mount.content.CompactCannonMountRenderer;
import com.cubester.cbc_compact_mount.content.CompactCannonMountVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class CMEntities {
	public static final BlockEntityEntry<CompactCannonMountBlockEntity> COMPACT_CANNON_MOUNT = ((CreateBlockEntityBuilder<CompactCannonMountBlockEntity, CreateRegistrate>) CM.REGISTRATE
			.blockEntity("compact_cannon_mount", CompactCannonMountBlockEntity::new)
			.validBlocks(CMBlocks.COMPACT_CANNON_MOUNT)
			.renderer(() -> CompactCannonMountRenderer::new))
			.visual(() -> CompactCannonMountVisual::new)
			.register();

	public static void register() {
	}
}