package online.flowerinsnow.xibaom.util;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Supplier;

@SideOnly(Side.CLIENT)
public final class ArgumentUtils {
    private ArgumentUtils() {
    }

    public static <T> T checkArgument(String message, Supplier<T> accept) {
        try {
            return accept.get();
        } catch (Exception e) {
            Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(e, message));
            throw e;
        }
    }
}
