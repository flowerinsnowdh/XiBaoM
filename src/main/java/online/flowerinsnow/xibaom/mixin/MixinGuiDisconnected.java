package online.flowerinsnow.xibaom.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import online.flowerinsnow.xibaom.C;
import online.flowerinsnow.xibaom.config.XiBaoMConfig;
import online.flowerinsnow.xibaom.object.LocationDimension;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiDisconnected.class)
@SideOnly(Side.CLIENT)
public class MixinGuiDisconnected extends GuiScreen {
    private final GuiDisconnected THIS = (GuiDisconnected) (Object) this;
    private ISound playingSound;

    @Inject(method = "initGui", at = @At("RETURN"))
    public void addButton(CallbackInfo ci) {
        if (XiBaoMConfig.displayEnable) { // 开启了按钮显示
            LocationDimension dimension = XiBaoMConfig.getLocationOf(this.width, this.height); // 获取在当前配置下应该绘制的按钮位置
            LogManager.getLogger().info(dimension.toString());
            this.buttonList.add(new GuiButton(9962, dimension.x, dimension.y, 200, 20, I18n.format(
                    XiBaoMConfig.music ? C.langPlayingSound : C.langStopingSound // 开关状态下两种不同的按钮显示文字
            )));

        }
        if (XiBaoMConfig.music) {
            startMusic();
        }
    }

    @Inject(method = "actionPerformed", at = @At("RETURN"))
    public void addListener(GuiButton button, CallbackInfo ci) {
        if (button.id == 0) { // 即将关闭Gui
            XiBaoMConfig.save(); // 将音乐的开关保存进配置文件
            stopMusic(); // 停止播放音乐
        }
        if (button.id == 9962) { // 开关音乐按钮
            XiBaoMConfig.music = !XiBaoMConfig.music; // 切换音乐开关
            if (XiBaoMConfig.music) { // 如果切换到了开
                button.displayString = I18n.format(C.langPlayingSound); // 切换按钮显示文字
                startMusic(); // 开始播放音乐
            } else { // 如果切换到了关
                button.displayString = I18n.format(C.langStopingSound); // 切换按钮显示文字
                stopMusic(); // 停止播放音乐
            }
        }
    }

    @Redirect(method = "drawScreen",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiDisconnected;drawDefaultBackground()V"))
    public void redirectDefaultBackground(GuiDisconnected instance) {
        // 绘制新背景
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer wr = tessellator.getWorldRenderer();
        Minecraft.getMinecraft().getTextureManager().bindTexture(C.disconnectBackground);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        wr.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        wr.pos(0.0D, this.height, 0.0D).tex(0F, 1F).color(255, 255, 255, 255).endVertex();
        wr.pos(this.width, this.height, 0.0D).tex(1F, 1F).color(255, 255, 255, 255).endVertex();
        wr.pos(this.width, 0.0D, 0.0D).tex(1F, 0F).color(255, 255, 255, 255).endVertex();
        wr.pos(0.0D, 0.0D, 0.0D).tex(0F, 0F).color(255, 255, 255, 255).endVertex();
        tessellator.draw();
        // 调用原本该调用的事件，防止不兼容其它mod
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent(THIS));
    }



    private void startMusic() {
        stopMusic(); // 先停止播放音乐，避免重复播放
        playingSound = new PositionedSoundRecord(new ResourceLocation("xibaom:xibao"), XiBaoMConfig.volume, 1.0F, 0.0F, 0.0F, 0.0F); // 标记正在播放的音乐
        Minecraft.getMinecraft().getSoundHandler().playSound(playingSound); // 开始播放
    }

    private void stopMusic() {
        if (playingSound != null) { // 如果有正在播放的音乐
            Minecraft.getMinecraft().getSoundHandler().stopSound(playingSound); // 将其停止
            playingSound = null; // 取消标记
        }
    }
}
