package us.timinc.dynamicblocks.block;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;
import us.timinc.dynamicblocks.DynamicBlockMod;

public class DynamicBlocks {
	private ArrayList<DynamicBlockSerializer> blockSerializers;
	private Gson gson;

	/**
	 * Creates a new instance.
	 */
	public DynamicBlocks() {
		this.blockSerializers = new ArrayList<>();
		this.gson = new Gson();
		loadBlocks();
	}

	private void loadBlocks() {
		File globalDir = new File("objects");
		if (!globalDir.exists()) {
			globalDir.mkdirs();
		}
		String[] files = globalDir.list();
		files = Arrays.stream(files).filter(x -> x.endsWith(".json")).toArray(String[]::new);
		for (String file : files) {
			try {
				addBlocksFrom(new File(globalDir, file));
			} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Adds blocks from a file.
	 * 
	 * @param file
	 *            The file to load from.
	 * @throws JsonSyntaxException
	 *             If the JSON syntax is invalid.
	 * @throws JsonIOException
	 *             If there is a JSON-related IO issue.
	 * @throws FileNotFoundException
	 *             If the file is not found.
	 */
	private void addBlocksFrom(File file) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		DynamicBlockSerializer[] newBlocks = gson.fromJson(new FileReader(file), DynamicBlockSerializer[].class);
		for (int i = 0; i < newBlocks.length; i++) {
			add(newBlocks[i]);
		}
	}

	/**
	 * Adds a single recipe to the recipe list.
	 * 
	 * @param recipe
	 *            The recipe to add.
	 */
	private void add(DynamicBlockSerializer block) {
		blockSerializers.add(block);
	}

	public void registerBlocks(IForgeRegistry<Block> registry) {
		for (DynamicBlockSerializer blockSerializer : blockSerializers) {
			registry.register(blockSerializer.getBlock().setRegistryName(blockSerializer.name)
					.setUnlocalizedName(DynamicBlockMod.MODID + "." + blockSerializer.name));
		}
	}

	public void registerItems(IForgeRegistry<Item> registry) {
		for (DynamicBlockSerializer blockSerializer : blockSerializers) {
			registry.register(new ItemBlock(blockSerializer.getBlock())
					.setRegistryName(blockSerializer.getBlock().getRegistryName()));
		}
	}
}
