package dev.keziumo.fomcreconfigfix.mixin.client;

import dev.keziumo.fomcreconfigfix.Fomcreconfigfix;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ReconfiguringScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReconfiguringScreen.class)
public abstract class ReconfiguringScreenMixin extends Screen {

    @Unique private boolean buttonAdded = false;

    protected ReconfiguringScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addForceReconnectButton(CallbackInfo ci) {
        if (buttonAdded) return;

        Fomcreconfigfix.LOGGER.info("[Fomc-Reconfig-Fix] Adding Force Reconnect button");

        MinecraftClient client = MinecraftClient.getInstance();
        ServerInfo last = client.getCurrentServerEntry();
        if (last == null) return;

        ButtonWidget reconnectButton = ButtonWidget.builder(Text.of("Force Reconnect"), btn -> {
            Fomcreconfigfix.LOGGER.info("[Fomc-Reconfig-Fix] Force Reconnect button clicked");

            // Kill current stuck connection
            if (client.getNetworkHandler() != null) {
                client.getNetworkHandler().getConnection().disconnect(Text.of("Force reconnect"));
            }

            // Reconnect instantly
            net.minecraft.client.network.ServerAddress addr =
                    net.minecraft.client.network.ServerAddress.parse(last.address);

            net.minecraft.client.gui.screen.multiplayer.ConnectScreen.connect(
                    this, client, addr, last, false, null
            );
        }).dimensions(this.width / 2 - 100, this.height - 40, 200, 20).build();

        this.addDrawableChild(reconnectButton);
        buttonAdded = true;
    }
}