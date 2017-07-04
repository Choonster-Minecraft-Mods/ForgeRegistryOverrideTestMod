package com.github.choonster.forgeregistryoverridetestmod;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid = ForgeRegistryOverrideTestMod.MODID, name = "Forge Registry Override Test Mod", version = "1.0", acceptableRemoteVersions = "*")
@Mod.EventBusSubscriber
public class ForgeRegistryOverrideTestMod {
	public static final String MODID = "forgeregistryoverridetestmod";

	@ObjectHolder(MODID + ":test_item")
	public static final Item TEST_ITEM = null;

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();

		final String registryName = "test_item";

		// Register the first item.
		final Item item1 = new Item().setRegistryName(registryName).setUnlocalizedName("item1");
		registry.register(item1);

		// Register the second item with the same name, overriding the first.
		//
		// Since both the old and the new Items have the same owner (mod ID), the new OverrideOwner and Item will
		// replace the old ones in the ForgeRegistry#owners BiMap.
		//
		// When ForgeRegistry#sync is called, it will log the following warning for the old value and then crash the game with a RuntimeException:
		//
		// "Registry Item: Override did not have an associated owner object. Name: forgeregistryoverridetestmod:test_item Value: Item@<hash>"
		final Item item2 = new Item().setRegistryName(registryName).setUnlocalizedName("item2");
		registry.register(item2);
	}

	@SubscribeEvent
	public static void registerModels(final ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(TEST_ITEM, 0, new ModelResourceLocation(TEST_ITEM.getRegistryName(), "inventory"));
	}
}
