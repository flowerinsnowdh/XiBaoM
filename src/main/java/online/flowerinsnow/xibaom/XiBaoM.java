package online.flowerinsnow.xibaom;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import online.flowerinsnow.xibaom.config.XiBaoMConfig;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = XiBaoM.MODID,
        name = XiBaoM.NAME,
        version = XiBaoM.VERSION,
        clientSideOnly = true
)
@SideOnly(Side.CLIENT)
public class XiBaoM {
    public static final String MODID = "xibaom";
    public static final String NAME = "Xi Bao Migrated";
    public static final String VERSION = "1.12.2";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Logger logger = event.getModLog();

        logger.info("Loading configuration...");
        XiBaoMConfig.init(event.getSuggestedConfigurationFile());
    }
}
