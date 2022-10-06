package online.flowerinsnow.xibaom;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import online.flowerinsnow.xibaom.config.XiBaoMConfig;
import org.apache.logging.log4j.Logger;

@Mod(modid = XiBaoM.MODID, version = XiBaoM.VERSION)
public class XiBaoM {
    public static final String MODID = "xibaom";
    public static final String VERSION = "@VERSION%";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Logger logger = event.getModLog();

        logger.info("Loading configuration...");
        XiBaoMConfig.init(event.getSuggestedConfigurationFile());
    }
}
