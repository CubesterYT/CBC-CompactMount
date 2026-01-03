package com.tryzubnyy.ymw.content;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import com.tryzubnyy.ymw.YMWEntities;

public class CompactCannonMountBlock extends KineticBlock implements IBE<CompactCannonMountBlockEntity> {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty ASSEMBLY_POWERED = BooleanProperty.create("assembly_powered");
	public static final BooleanProperty FIRE_POWERED = BooleanProperty.create("fire_powered");

	public CompactCannonMountBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any()
			.setValue(FACING, Direction.NORTH)
			.setValue(ASSEMBLY_POWERED, false)
			.setValue(FIRE_POWERED, false));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, ASSEMBLY_POWERED, FIRE_POWERED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState()
			.setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	public Axis getRotationAxis(BlockState state) {
		return state.getValue(FACING).getAxis() == Axis.X ? Axis.Z : Axis.X;
	}

	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return face.getAxis() == this.getRotationAxis(state);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
	}

	@Override
	public Class<CompactCannonMountBlockEntity> getBlockEntityClass() {
		return CompactCannonMountBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends CompactCannonMountBlockEntity> getBlockEntityType() {
		return YMWEntities.COMPACT_CANNON_MOUNT.get();
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean isMoving) {
		if (!level.isClientSide) {
			if (!level.getBlockTicks().willTickThisTick(pos, this)) {
				level.scheduleTick(pos, this, 0);
			}
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		boolean prevAssemblyPowered = state.getValue(ASSEMBLY_POWERED);
		boolean prevFirePowered = state.getValue(FIRE_POWERED);

		boolean assemblyPowered = this.hasNeighborSignal(level, state, pos, ASSEMBLY_POWERED);
		boolean firePowered = this.hasNeighborSignal(level, state, pos, FIRE_POWERED);

		Direction fireDirection = state.getValue(FACING);
		int firePower = level.getSignal(pos.relative(fireDirection), fireDirection);

		this.withBlockEntityDo(level, pos, cmbe -> cmbe.onRedstoneUpdate(assemblyPowered, prevAssemblyPowered, firePowered, prevFirePowered, firePower));
	}

	private boolean hasNeighborSignal(Level level, BlockState state, BlockPos pos, BooleanProperty property) {
		if (property == FIRE_POWERED) {
			Direction fireDirection = state.getValue(FACING);
			return level.getSignal(pos.relative(fireDirection), fireDirection) > 0;
		}
		if (property == ASSEMBLY_POWERED) {
			Direction assemblyDirection = state.getValue(FACING).getOpposite();
			return level.getSignal(pos.relative(assemblyDirection), assemblyDirection) > 0;
		}
		return false;
	}

	@Override
	public InteractionResult onWrenched(BlockState state, UseOnContext context) {
		InteractionResult resultType = super.onWrenched(state, context);
		if (!context.getLevel().isClientSide && resultType.consumesAction()
			&& context.getLevel().getBlockEntity(context.getClickedPos()) instanceof CompactCannonMountBlockEntity mount) {
			mount.disassemble();
		}
		return resultType;
	}
}