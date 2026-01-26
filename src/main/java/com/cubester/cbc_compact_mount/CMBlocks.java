package com.cubester.cbc_compact_mount;

import com.cubester.cbc_compact_mount.content.CompactCannonMountBlock;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.util.entry.BlockEntry;

public class CMBlocks {
	public static final BlockEntry<CompactCannonMountBlock> COMPACT_CANNON_MOUNT = CM.REGISTRATE
			.block("compact_cannon_mount", CompactCannonMountBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.properties(p -> p.noOcclusion())
			.blockstate((c, p) -> p.horizontalBlock(c.get(), AssetLookup.standardModel(c, p)))
			.simpleItem()
			.register();

	public static void register() {
	}
}