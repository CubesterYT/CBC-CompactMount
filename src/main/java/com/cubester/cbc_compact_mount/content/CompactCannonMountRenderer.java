package com.cubester.cbc_compact_mount.content;

import static com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer.getAngleForBe;
import static com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer.kineticRotationTransform;
import static com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer.shaft;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import org.joml.Quaternionf;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.index.CBCBlockPartials;

public class CompactCannonMountRenderer extends SafeBlockEntityRenderer<CompactCannonMountBlockEntity> {
	public CompactCannonMountRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	protected void renderSafe(CompactCannonMountBlockEntity be, float partialTicks, PoseStack ms,
			MultiBufferSource buffer, int light, int overlay) {
		if (VisualizationManager.supportsVisualization(be.getLevel()))
			return;

		BlockState state = be.getBlockState();
		Direction facing = state.getValue(CompactCannonMountBlock.FACING);
		VertexConsumer solidBuf = buffer.getBuffer(RenderType.solid());

		ms.pushPose();

		Direction.Axis pitchAxis = facing.getClockWise().getAxis();
		SuperByteBuffer pitchShaft = CachedBuffers.block(shaft(pitchAxis));
		kineticRotationTransform(pitchShaft, be, pitchAxis, getAngleForBe(be, be.getBlockPos(), pitchAxis), light)
				.renderInto(ms, solidBuf);

		float pitch = be.getPitchOffset(partialTicks);
		Quaternionf qpitch;

		if (facing.getAxis() == Direction.Axis.Z) {
			qpitch = Axis.XP.rotationDegrees(pitch);
		} else {
			qpitch = Axis.ZP.rotationDegrees(pitch);
		}

		Direction right = facing.getClockWise();
		float tx = right.getStepX();
		float ty = right.getStepY();
		float tz = right.getStepZ();

		CachedBuffers.partialFacing(CBCBlockPartials.CANNON_CARRIAGE_AXLE, state, Direction.NORTH)
				.translate(tx, ty, tz)
				.center()
				.rotate(qpitch)
				.uncenter()
				.light(light)
				.renderInto(ms, solidBuf);

		ms.popPose();
	}
}