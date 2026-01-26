package com.cubester.cbc_compact_mount.compat;

import javax.annotation.Nullable;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import com.cubester.cbc_compact_mount.CM;
import com.cubester.cbc_compact_mount.CMBlocks;
import com.cubester.cbc_compact_mount.content.CompactCannonMountBlockEntity;

import rbasamoyai.createbigcannons.cannon_control.cannon_mount.CannonMountBlockEntity;
import rbasamoyai.createbigcannons.cannon_control.cannon_mount.ExtendsCannonMount;
import rbasamoyai.createbigcannons.cannon_control.contraption.AbstractMountedCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.MountedAutocannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.MountedBigCannonContraption;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;
import rbasamoyai.createbigcannons.cannons.big_cannons.breeches.quickfiring_breech.CannonMountPoint;

public class CMArmInteractionPointTypes {
  public static void init() {
    Registry.register(CreateBuiltInRegistries.ARM_INTERACTION_POINT_TYPE,
        new ResourceLocation(CM.MODID, "compact_cannon_mount"), new CompactCannonMountType());
  }

  public static class CompactCannonMountType extends ArmInteractionPointType {
    @Override
    public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
      if (!CMBlocks.COMPACT_CANNON_MOUNT.has(state))
        return false;
      return level.getBlockEntity(pos) instanceof CompactCannonMountBlockEntity;
    }

    @Nullable
    @Override
    public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
      return new CompactCannonMountPoint(this, level, pos, state);
    }
  }

  public static class CompactCannonMountPoint extends AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint {
    public CompactCannonMountPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
      super(type, level, pos, state);
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
      BlockEntity be = this.getLevel().getBlockEntity(this.pos);
      if (be instanceof CompactCannonMountBlockEntity mount) {
        return mount.getInteractionLocation();
      }
      if (be instanceof ExtendsCannonMount extendsCannonMount) {
        CannonMountBlockEntity base = extendsCannonMount.getCannonMount();
        if (base != null)
          return base.getInteractionLocation();
      }
      return super.getInteractionPositionVector();
    }

    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
      BlockEntity be = this.getLevel().getBlockEntity(this.pos);
      PitchOrientedContraptionEntity poce = null;

      if (be instanceof CompactCannonMountBlockEntity mount) {
        poce = mount.getContraption();
      } else if (be instanceof ExtendsCannonMount extendsMount) {
        CannonMountBlockEntity base = extendsMount.getCannonMount();
        if (base != null)
          poce = base.getContraption();
      }

      if (poce == null || !(poce.getContraption() instanceof AbstractMountedCannonContraption cannon))
        return stack;

      if (cannon instanceof MountedBigCannonContraption big) {
        return CannonMountPoint.bigCannonInsert(stack, simulate, big, poce);
      }
      if (cannon instanceof MountedAutocannonContraption auto) {
        return CannonMountPoint.autocannonInsert(stack, simulate, auto, poce);
      }
      return stack;
    }
  }
}
