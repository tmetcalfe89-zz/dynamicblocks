package us.timinc.dynamicblocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * A util class for turning game objects into ID strings.
 * 
 * @author Tim
 *
 */
public class IdUtil {
	/**
	 * Gets a block ID from a block state.
	 * 
	 * @param blockState
	 *            The block state
	 * @return The block ID
	 */
	public static String getBlockId(IBlockState blockState) {
		Block block = blockState.getBlock();
		return block.getRegistryName().toString() + ":" + block.getMetaFromState(blockState);
	}

	/**
	 * Gets an item ID from an item stack.
	 * 
	 * @param itemStack
	 *            The item stack
	 * @return The item ID
	 */
	public static String getItemId(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item.getRegistryName().toString() + ":" + itemStack.getMetadata();
	}

	/**
	 * Creates an item stack from the given item ID.
	 * 
	 * @param itemId
	 *            The item ID.
	 * @param count
	 *            The number of items.
	 * @return The item stack.
	 */
	public static ItemStack createItemStackFrom(String itemId, int count) {
		String[] splitDropItemId = itemId.split(":");
		ItemStack newItemStack = new ItemStack(Item.getByNameOrId(splitDropItemId[0] + ":" + splitDropItemId[1]),
				count);
		newItemStack.setItemDamage(Integer.parseInt(splitDropItemId[2]));
		return newItemStack;
	}

	/**
	 * Gets a block state from the given block ID.
	 * 
	 * @param blockId
	 *            The block ID.
	 * @return The block state.
	 */
	public static IBlockState getBlockStateFrom(String blockId) {
		String[] splitIntoBlockId = blockId.split(":");
		return Block.getBlockFromName(splitIntoBlockId[0] + ":" + splitIntoBlockId[1])
				.getStateFromMeta(Integer.parseInt(splitIntoBlockId[2]));
	}

	/**
	 * Determines whether two item IDs match. Does not take meta into account if
	 * the item stack is damageable.
	 * 
	 * @param itemId1
	 *            The first item ID.
	 * @param itemId2
	 *            The second item ID.
	 * @return Whether the two item IDs match.
	 */
	public static boolean itemIdsMatch(String itemId1, String itemId2) {
		if (IdUtil.createItemStackFrom(itemId1, 1).isItemStackDamageable()) {
			String[] split1 = itemId1.split(":");
			String[] split2 = itemId2.split(":");
			return split1[0].equals(split2[0]) && split1[1].equals(split2[1]);
		} else {
			return itemId1.equals(itemId2);
		}
	}
}
