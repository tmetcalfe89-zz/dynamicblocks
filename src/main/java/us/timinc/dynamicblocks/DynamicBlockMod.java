package us.timinc.dynamicblocks;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import us.timinc.dynamicblocks.block.DynamicBlocks;

/**
 * The core mod file. Not to be confused with coremods.
 * 
 * @author Tim
 *
 */
@Mod(modid = DynamicBlockMod.MODID, name = DynamicBlockMod.NAME, version = DynamicBlockMod.VERSION)
@Mod.EventBusSubscriber
public class DynamicBlockMod {
	/**
	 * The internal ID of the mod.
	 */
	public static final String MODID = "dynamicblocks";
	/**
	 * The human-readable name of the mod.
	 */
	public static final String NAME = "Dynamic Blocks";
	/**
	 * The version of the mod. I'm sure I won't forget to update this. Probably.
	 */
	public static final String VERSION = "1.0.1";

	/**
	 * An instance of the mod. I forget why this is here.
	 */
	@Mod.Instance(DynamicBlockMod.MODID)
	public static DynamicBlockMod instance;

	public static DynamicBlocks blocks;

	/**
	 * The logger for the mod. Output stuff here, not to System.
	 */
	public Logger logger;

	/**
	 * Handles preinitialization stuff. Fires up the real event handler.
	 * 
	 * @param event
	 *            The preinit event.
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		blocks = new DynamicBlocks();
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		blocks.registerBlocks(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		blocks.registerItems(event.getRegistry());
	}
}
