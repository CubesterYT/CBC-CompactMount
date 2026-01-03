package com.tryzubnyy.ymw.content;

import java.util.List;

import javax.annotation.Nullable;

import com.simibubi.create.foundation.utility.CreateLang;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.MountedAutocannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.MountedBigCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.config.CBCConfigs;

public interface ExtendsCompactCannonMount {
	MutableComponent noCannonPresent = Component.translatable("createbigcannons.goggles.cannon_mount.no_cannon_present");
	MutableComponent cannonPitchComponent = Component.translatable("createbigcannons.goggles.cannon_mount.pitch");
	MutableComponent bigCannonStrengthComponent = Component.translatable("createbigcannons.goggles.cannon_mount.cannon_strength");
	String bigCannonStrengthValueKey = "createbigcannons.goggles.cannon_mount.cannon_strength.value";
	MutableComponent autocannonRPMComponent = Component.translatable("createbigcannons.goggles.cannon_mount.autocannon_rate_of_fire");
	String autocannonRPMValueKey = "createbigcannons.goggles.cannon_mount.autocannon_rate_of_fire.value";

    CompactCannonMountBlockEntity getCannonMount();

    static void addCannonInfoToTooltip(List<Component> tooltip, @Nullable PitchOrientedContraptionEntity mountedContraption) {
		if (mountedContraption != null && mountedContraption.getContraption() instanceof AbstractMountedCannonContraption cannon) {
			Direction dir = mountedContraption.getInitialOrientation();
			boolean flag = (dir.getAxisDirection() == Direction.AxisDirection.POSITIVE) == (dir.getAxis() == Direction.Axis.X);
			float pitch = flag ? mountedContraption.pitch : -mountedContraption.pitch;
			if (Math.abs(pitch) < 1e-1f) pitch = 0;

			String precision = CBCConfigs.client().cannonMountAngleGoggleTooltipPrecision.get().toString();
			String format = "%." + precision + "f\u00ba";
			
			CreateLang.builder().add(cannonPitchComponent.copy().withStyle(ChatFormatting.GRAY)
					.append(Component.literal(String.format(format, Mth.wrapDegrees(pitch))).withStyle(ChatFormatting.WHITE)))
				.forGoggles(tooltip);
			if (cannon instanceof MountedBigCannonContraption bigCannon) {
				CreateLang.builder().add(bigCannonStrengthComponent.copy().withStyle(ChatFormatting.GRAY)
						.append(Component.translatable(bigCannonStrengthValueKey, bigCannon.getMaxSafeCharges()).withStyle(ChatFormatting.WHITE)))
					.forGoggles(tooltip);
			} else if (cannon instanceof MountedAutocannonContraption autocannon) {
				CreateLang.builder().add(autocannonRPMComponent.copy().withStyle(ChatFormatting.GRAY)
						.append(Component.translatable(autocannonRPMValueKey, autocannon.getReferencedFireRate()).withStyle(ChatFormatting.WHITE)))
					.forGoggles(tooltip);
			}
		} else {
			CreateLang.builder().add(noCannonPresent.copy()).forGoggles(tooltip);
		}
	}
}