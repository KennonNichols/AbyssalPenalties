package net.thathitmann.madeforabyss.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thathitmann.madeforabyss.MadeForAbyss;
import net.thathitmann.madeforabyss.entity.ModEntities;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MadeForAbyss.MOD_ID);


    //Charcoal Filter
    public static final RegistryObject<Item> CHARCOAL_FILTER = ITEMS.register("charcoal_filter",
            () -> new CharcoalFilter(new Item.Properties()));
    //Toxin Amulet
    public static final RegistryObject<Item> TOXIN_AMULET = ITEMS.register("toxin_amulet",
            () -> new ToxinAmulet(new Item.Properties()));

    //Toxin Amulet
    public static final RegistryObject<Item> MEMENTO = ITEMS.register("memento",
            () -> new Memento(new Item.Properties().rarity(Rarity.EPIC).fireResistant().stacksTo(1)));

    //Gas Mask
    public static final RegistryObject<Item> GAS_MASK = ITEMS.register("gas_mask",
            () -> new GasMask(ModArmorMaterials.HAZMAT, ArmorItem.Type.HELMET, new Item.Properties()));
    //Golem Spawn Egg
    public static final RegistryObject<Item> GOLEM_SPAWN_EGG = ITEMS.register("golem_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.DEEPSLATE_GOLEM, 0x515151, 0x3e3d43,
                    new Item.Properties()));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
