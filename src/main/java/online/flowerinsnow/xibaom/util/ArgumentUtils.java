package online.flowerinsnow.xibaom.util;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;

import java.util.function.Supplier;

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
