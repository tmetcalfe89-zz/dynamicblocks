package us.timinc.dynamicblocks.block;

import com.google.gson.annotations.SerializedName;

import net.minecraft.creativetab.CreativeTabs;

public class DynamicBlockSerializer {
	public String name = "";
	public String stages = "";
	public String dropsItems = "true";
	public String[] handItems = {};

	private transient DynamicBlock cachedBlock;

	public DynamicBlock getBlock() {
		if (cachedBlock == null) {
			cachedBlock = new DynamicBlock(Integer.parseInt(stages), Boolean.parseBoolean(dropsItems), handItems);
			cachedBlock.setCreativeTab(CreativeTabs.DECORATIONS);
		}
		return cachedBlock;
	}
}
