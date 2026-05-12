package hw.zako.zakohealthindicator.client.ui;

import com.mojang.blaze3d.vertex.PoseStack;
import hw.zako.zakohealthindicator.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class HealthBarGUI {

    private final Minecraft client;
    private long lastAttack = 0;
    private Player player = null;

    public HealthBarGUI(Minecraft client) {
        this.client = client;
    }

    public void onAttack(Player target) {
        this.lastAttack = System.currentTimeMillis();
        this.player = target;
    }

    public void reset() {
        this.lastAttack = 0;
    }

    private long getLeft() {
        return System.currentTimeMillis() - this.lastAttack;
    }

    public void render(PoseStack context) {
        if (getLeft() > 10000) return;
        if (this.player == null) return;
        int health = (int) player.getHealth();

        String text = ColorUtil.getColor(health) + "" + health;

        final int textW = client.font.width(text);
        context.pushPose();
        float scale = health < 10 ? 2 : 1;
        final int h = (int) (client.getWindow().getGuiScaledHeight() / (scale * 2));
        final int w = (int) (client.getWindow().getGuiScaledWidth() / (scale * 2) - textW / 2);

        if (scale > 1)
            context.scale(scale, scale, scale);
        client.font.drawShadow(context, text, w, h + 4, 0xFFFF0000);
        context.popPose();
    }
}
