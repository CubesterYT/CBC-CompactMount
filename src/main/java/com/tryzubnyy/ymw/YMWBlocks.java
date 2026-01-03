package com.tryzubnyy.ymw;

import com.tryzubnyy.ymw.content.CompactCannonMountBlock;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.util.entry.BlockEntry;

public class YMWBlocks {
	public static final BlockEntry<CompactCannonMountBlock> COMPACT_CANNON_MOUNT = YMW.REGISTRATE
		.block("compact_cannon_mount", CompactCannonMountBlock::new)
		.initialProperties(SharedProperties::softMetal)
		.properties(p -> p.noOcclusion())
		.blockstate((c, p) -> p.horizontalBlock(c.get(), AssetLookup.standardModel(c, p)))
		.simpleItem()
		.register();

	public static void register() {}
}