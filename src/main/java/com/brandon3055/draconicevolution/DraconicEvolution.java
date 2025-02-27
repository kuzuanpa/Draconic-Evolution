package com.brandon3055.draconicevolution;

import com.brandon3055.draconicevolution.client.creativetab.DETab;
import com.brandon3055.draconicevolution.common.CommonProxy;
import com.brandon3055.draconicevolution.common.lib.OreDoublingRegistry;
import com.brandon3055.draconicevolution.common.lib.References;
import com.brandon3055.draconicevolution.common.utills.LogHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;

@Mod(modid = References.MODID, name = References.MODNAME, version = References.VERSION, canBeDeactivated = false, guiFactory = References.GUIFACTORY, dependencies = "after:NotEnoughItems;" +
        "after:NotEnoughItems;" +
        "after:ThermalExpansion;" +
        "after:ThermalFoundation;" +
        "required-after:BrandonsCore@[1.0.0.11,);")
public class DraconicEvolution {

    @Mod.Instance(References.MODID)
    public static DraconicEvolution instance;

    @SidedProxy(clientSide = References.CLIENTPROXYLOCATION, serverSide = References.SERVERPROXYLOCATION)
    public static CommonProxy proxy;

    public static final CreativeTabs tabToolsWeapons = new DETab(CreativeTabs.getNextID(), References.MODID, "toolsAndWeapons", 0);
    public static final CreativeTabs tabBlocksItems = new DETab(CreativeTabs.getNextID(), References.MODID, "blocksAndItems", 1);

    public static final String networkChannelName = "DEvolutionNC";
    public static SimpleNetworkWrapper network;

    public static final boolean debug = false;//todo

    public static Enchantment reaperEnchant;

    public DraconicEvolution() {
        LogHelper.info("Hello Minecraft!!!");
    }

    @Mod.EventHandler
    public static void preInit(final FMLPreInitializationEvent event) {
        if (debug) LogHelper.info("Initialization");

//		event.getModMetadata().autogenerated = false;
//		event.getModMetadata().credits = "";
//		event.getModMetadata().description = "This is a mod originally made for the Tolkiencraft mod pack";
//		event.getModMetadata().authorList = Arrays.asList("brandon3055");
//		event.getModMetadata().logoFile = "banner.png";
//		event.getModMetadata().url = "http://www.brandon3055.com/";
//		event.getModMetadata().version = References.VERSION + "-MC1.7.10";

        proxy.preInit(event);

    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        if (debug) System.out.println("init()");
        proxy.init(event);

        //	===

        //	===
    }

    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        if (debug) System.out.println("postInit()");

        proxy.postInit(event);
    }

    //FMLInterModComms.sendMessage("DraconicEvolution", "addChestRecipe:item.coal", new ItemStack(Items.diamond, 2));
    @Mod.EventHandler
    public void processMessage(FMLInterModComms.IMCEvent event) {
        for (FMLInterModComms.IMCMessage m : event.getMessages()) {
            LogHelper.info(m.key);
            if (m.isItemStackMessage() && m.key.contains("addChestRecipe:")) {
                String s = m.key.substring(m.key.indexOf("addChestRecipe:") + 15);
                OreDoublingRegistry.resultOverrides.put(s, m.getItemStackValue());
                LogHelper.info("Added Chest recipe override: " + s + " to " + m.getItemStackValue());
            }
        }
    }
}