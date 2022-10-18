package online.flowerinsnow.xibaom;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface C {
    ResourceLocation disconnectBackground = new ResourceLocation("xibaom", "textures/xibao.png");

    String configGlobalComment = "全局设定";
    String configButtonComment = "停止音乐按钮和显示的坐标：正数=绝对坐标，负数=倒数绝对坐标，center=屏幕中间";
    String langPlayingSound = "gui.playingsound";
    String langStopingSound = "gui.stopingsound";
}
