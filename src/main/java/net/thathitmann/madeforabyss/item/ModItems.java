package net.thathitmann.madeforabyss.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thathitmann.madeforabyss.MadeForAbyss;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MadeForAbyss.MOD_ID);


    //Charcoal Filter
    public static final RegistryObject<Item> CHARCOAL_FILTER = ITEMS.register("charcoal_filter",
            () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> GAS_MASK = ITEMS.register("gas_mask",
            () -> new ArmorItem(ModArmorMaterials.HAZMAT, ArmorItem.Type.HELMET, new Item.Properties()));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
