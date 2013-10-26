package com.forgeessentials.core.modules;

import com.forgeessentials.core.CoreConfig;
import com.forgeessentials.core.ForgeEssentials;
import com.forgeessentials.core.modules.FMLevents.FMLEventHandler;
import com.forgeessentials.core.modules.FMLevents.IPreInit;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.event.FMLEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import static com.forgeessentials.core.ForgeEssentials.LOGGER;

public class ModuleLoader
{
    private static final HashMap<String, ModuleContainer> MODULES_MAP               = new HashMap<String, ModuleContainer>();
    private static final HashSet<String>                  LOADED_MODULES            = new HashSet<String>();

    /**
     * Gets called to load modules and do config and pass on the FMLPreInitializationEvent.
     *
     * @param event used for its ASM data
     */
    public static void init(FMLPreInitializationEvent event)
    {
        LOGGER.info("Discovering all modules...");
        String description = event.getModMetadata().description;
        description += "\n" + EnumChatFormatting.UNDERLINE + "Modules:" + EnumChatFormatting.RESET + "\n";

        for (ASMDataTable.ASMData data : event.getAsmData().getAll(IFEModule.LoadMe.class.getName()))
        {
            try
            {
                ModuleContainer container = new ModuleContainer(data);
                description += "\n" + (container.loaded ? EnumChatFormatting.DARK_GREEN : EnumChatFormatting.DARK_RED) + container.name + EnumChatFormatting.RESET;
                if (container.loaded)
                {
                    LOADED_MODULES.add(container.name);
                    FMLEventHandler.checkInterfaces(container.module);
                }
                else
                {
                    LOGGER.warn("Not loading {}", container.name);
                }

                MODULES_MAP.put(container.name, container);
            }
            catch (Exception e)
            {
                LOGGER.error("An error occurred while trying to load a module from class {}", data.getClassName());
                e.printStackTrace();
            }
        }

        event.getModMetadata().description = description;

        doConfig();

        FMLEventHandler.passEvent(event);
    }

    public static void enable(MinecraftServer server)
    {
        for (String module : LOADED_MODULES)
        {
            MODULES_MAP.get(module).module.enable(server);
            MODULES_MAP.get(module).state = ModuleState.ENABLED;
        }
    }

    public static void disable()
    {
        for (String module : LOADED_MODULES)
        {
            MODULES_MAP.get(module).module.disable();
            MODULES_MAP.get(module).state = ModuleState.DISABLED;
        }
    }

    public static void reload()
    {
        doConfig();
        for (String module : LOADED_MODULES)
        {
            MODULES_MAP.get(module).module.reload();
        }
    }

    public static void doConfig()
    {
        for (String module : LOADED_MODULES)
        {
            Configuration configuration = CoreConfig.INSTANCE.useOneConfig ? CoreConfig.INSTANCE.getConfiguration() : new Configuration(new File(ForgeEssentials.SETTINGS_DIR, module + ".cfg"));
            MODULES_MAP.get(module).module.doConfig(configuration);
            configuration.save();
        }
    }

    public static ModuleContainer getModule(String name)
    {
        return MODULES_MAP.get(name);
    }

    public static boolean isModulePresent(String name)
    {
        return MODULES_MAP.containsKey(name);
    }

    public static boolean isModuleLoaded(String name)
    {
        return LOADED_MODULES.contains(name);
    }
}
