package us.timinc.dynamicblocks.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import us.timinc.dynamicblocks.util.IdUtil;
import us.timinc.dynamicblocks.util.MinecraftUtil;

public class DynamicBlock extends Block {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);

	private int stages;
	private boolean dropsItems;
	private String[] handItems;

	public DynamicBlock(int stages, boolean dropsItems, String[] handItems) {
		super(Material.WOOD);
		this.blockHardness = 2;
		this.fullBlock = false;
		this.stages = stages;
		this.dropsItems = dropsItems;
		this.handItems = handItems;
		setDefaultState(blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		int age = this.getAge(state);
		List<ItemStack> drops = super.getDrops(world, pos, state, fortune);
		if (!dropsItems) {
			return drops;
		}
		for (int i = 0; i < age; i++) {
			ItemStack newDrop = IdUtil.createItemStackFrom(handItems[i], 1);
			if (!newDrop.isItemStackDamageable()) {
				drops.add(newDrop);
			}
		}
		return drops;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		if (hand == EnumHand.OFF_HAND)
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);

		int i = ((Integer) state.getValue(AGE)).intValue();
		if (i < getMaxAge() && isValidHandItem(IdUtil.getItemId(playerIn.getHeldItem(hand)), i)) {
			worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(i + 1)), 2);
			MinecraftUtil.damageItemStack(playerIn.getHeldItem(hand), 1, playerIn);
			return true;
		}

		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	private boolean isValidHandItem(String itemId, int i) {
		return IdUtil.itemIdsMatch(handItems[i], itemId);
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { AGE });
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		return this.withAge(meta);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		return this.getAge(state);
	}

	public IBlockState withAge(int age) {
		return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age));
	}

	protected PropertyInteger getAgeProperty() {
		return AGE;
	}

	protected int getAge(IBlockState state) {
		return ((Integer) state.getValue(this.getAgeProperty())).intValue();
	}

	protected int getMaxAge() {
		return stages;
	}
}
