package online.flowerinsnow.xibaom.config;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import online.flowerinsnow.xibaom.C;
import online.flowerinsnow.xibaom.object.LocationDimension;
import online.flowerinsnow.xibaom.util.ArgumentUtils;

import java.io.File;

public class XiBaoMConfig {
    public static String displayX; // button.x : string
    public static String displayY; // button.y : string
    public static boolean displayEnable; // button.enable : boolean

    public static float volume; // global.volume : float
    public static boolean music; // global.music : boolean

    
    private static File configFile; // 配置文件
    private static Configuration config; // 配置内容


    private static Property pVolume; // global.volume : float
    private static Property pMusic; // global.music : boolean

    private static Property pDisplayEnable; // button.enable : boolean
    private static Property pDisplayX; // button.x : string
    private static Property pDisplayY; // button.y : string
    
    public static void init(File configFile) {
        // 初始化
        XiBaoMConfig.configFile = configFile;
        load();
    }
    
    public static void load() {
        try {
            load0();
        } catch (IllegalArgumentException e) {
            Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(e, e.getMessage()));
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void load0() {
        config = new Configuration(configFile);

        // 获取属性
        ConfigCategory cGlobal = config.getCategory("global");
        cGlobal.setComment(C.configGlobalComment);
        pVolume = config.get(cGlobal.getName(), "volume", 1.0, "背景音乐音量 (0.0~1.0)");
        pMusic = config.get(cGlobal.getName(), "music", true, "播放音乐");

        ConfigCategory cButton = config.getCategory("button");
        cButton.setComment(C.configButtonComment);
        pDisplayEnable = config.get(cButton.getName(), "enable", true, "显示该按钮");
        pDisplayX = config.get(cButton.getName(), "x", "center");
        pDisplayY = config.get(cButton.getName(), "y", "-20");

        // 读取属性
        volume = ArgumentUtils.checkArgument("volume must be a float value.", () -> Float.parseFloat(pVolume.getString()));
        music = Boolean.parseBoolean(pMusic.getString());

        displayX = ArgumentUtils.checkArgument("x must be 'center' or a int value", () -> {
            String value = pDisplayX.getString();
            if (!value.equalsIgnoreCase("center")) {
                Integer.parseInt(value);
            }
            return value;
        });
        displayY = ArgumentUtils.checkArgument("y must be 'center' or a int value", () -> {
            String value = pDisplayY.getString();
            if (!value.equalsIgnoreCase("center")) {
                Integer.parseInt(value);
            }
            return value;
        });
        displayEnable = Boolean.parseBoolean(pDisplayEnable.getString());

        config.save();
    }
    
    public static void save() {
        // 设置属性
        pVolume.set(volume);
        pMusic.set(music);

        pDisplayX.set(displayX);
        pDisplayY.set(displayY);
        pDisplayEnable.set(displayEnable);
        
        // 写入文件
        config.save();
    }

    public static LocationDimension getLocationOf(int width, int height) {
        int x;
        if (displayX.equals("center")) {
            x = (width - 200) / 2;
        } else {
            int ix = Integer.parseInt(displayX);
            if (ix < 0) {
                x = width + ix;
            } else {
                x = ix;
            }
        }

        int y;
        if (displayY.equals("center")) {
            y = (height - 20) / 2;
        } else {
            int iy = Integer.parseInt(displayY);
            if (iy < 0) {
                y = height + iy;
            } else {
                y = iy;
            }
        }

        return new LocationDimension(x, y);
    }
}
