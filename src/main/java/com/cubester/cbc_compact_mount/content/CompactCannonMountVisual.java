package com.cubester.cbc_compact_mount.content;

import java.util.function.Consumer;
import org.joml.Quaternionf;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LightLayer;
import rbasamoyai.createbigcannons.index.CBCBlockPartials;

public class CompactCannonMountVisual extends KineticBlockEntityVisual<CompactCannonMountBlockEntity>
		implements SimpleDynamicVisual {

	private OrientedInstance rotatingMountShaft;
	private RotatingInstance pitchShaft;

	public CompactCannonMountVisual(VisualizationContext ctx, CompactCannonMountBlockEntity tile, float partialTick) {
		super(ctx, tile, partialTick);

		int blockLight = this.blockEntity.getLevel().getBrightness(LightLayer.BLOCK, this.pos);
		int skyLight = this.blockEntity.getLevel().getBrightness(LightLayer.SKY, this.pos);

		Direction facing = tile.getBlockState().getValue(CompactCannonMountBlock.FACING);
		Direction.Axis pitchAxis = facing.getClockWise().getAxis();

		this.rotatingMountShaft = instancerProvider()
				.instancer(InstanceTypes.ORIENTED, Models.partial(CBCBlockPartials.CANNON_CARRIAGE_AXLE)).createInstance();

		Direction right = facing.getClockWise();
		this.rotatingMountShaft.position(getVisualPosition().relative(right));

		Direction shaftOrientation = Direction.get(Direction.AxisDirection.POSITIVE, pitchAxis);

		this.pitchShaft = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT))
				.createInstance()
				.rotateToFace(shaftOrientation)
				.setup(blockEntity)
				.setColor(blockEntity)
				.setPosition(getVisualPosition());

		this.pitchShaft.light(blockLight, skyLight).setChanged();

		this.transformModels();
	}

	@Override
	public void _delete() {
		this.rotatingMountShaft.delete();
		this.pitchShaft.delete();
	}

	private void transformModels() {
		Direction facing = this.blockState.getValue(CompactCannonMountBlock.FACING);
		Direction.Axis pitchAxis = facing.getClockWise().getAxis();
		float bspeed = this.blockEntity.getSpeed() * 6;
		this.updateRotation(this.pitchShaft, pitchAxis, bspeed);
	}

	protected void updateRotation(RotatingInstance instance, Direction.Axis axis, float speed) {
		instance.setRotationAxis(axis)
				.setRotationalSpeed(speed)
				.setRotationOffset(rotationOffset(blockState, axis, pos))
				.setColor(this.blockEntity)
				.setChanged();
	}

	@Override
	public void beginFrame(DynamicVisual.Context ctx) {
		this.transformModels();
		float partialTicks = ctx.partialTick();
		Direction facing = this.blockState.getValue(CompactCannonMountBlock.FACING);

		float pitch = this.blockEntity.getPitchOffset(partialTicks);

		Quaternionf finalRot = new Quaternionf();

		float angle = facing.toYRot();
		finalRot.rotationY((float) Math.toRadians(angle));

		if (facing.getAxis() == Direction.Axis.Z) {
			finalRot.rotateLocalX((float) Math.toRadians(-pitch));
		} else {
			finalRot.rotateLocalZ((float) Math.toRadians(-pitch));
		}

		this.rotatingMountShaft.rotation(finalRot);
		this.rotatingMountShaft.setChanged();
	}

	@Override
	public void updateLight(float partialTicks) {
		Direction right = this.blockState.getValue(CompactCannonMountBlock.FACING).getClockWise();
		this.relight(this.pos.relative(right), this.rotatingMountShaft);
		this.relight(this.pos, this.pitchShaft);
	}

	@Override
	public void collectCrumblingInstances(Consumer<Instance> consumer) {
		consumer.accept(rotatingMountShaft);
		consumer.accept(pitchShaft);
	}
}