package me.zyouime.zakohealthindicator.command;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import me.shedaniel.autoconfig.AutoConfig;
import me.zyouime.zakohealthindicator.config.ModConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class ModCommand {

    private final ModConfig config;

    public ModCommand(ModConfig config) {
        this.config = config;
        this.register();
    }

    private void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(ClientCommandManager.literal("zakohealthindicator")
                    .then(ClientCommandManager.literal("toggle")
                            .executes(context -> {
                                config.enabled = !config.enabled;
                                String text = config.enabled ? "§aМод включён" : "§cМод выключен";
                                sendText(context, text);
                                return 1;
                            }))
                    .then(ClientCommandManager.literal("relativeX")
                            .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                                    .executes(context -> {
                                        config.relativeX = context.getArgument("value", Float.class);
                                        sendText(context, setValueText(config.relativeX));
                                        return 1;
                                    })))
                    .then(ClientCommandManager.literal("relativeY")
                            .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                                    .executes(context -> {
                                        config.relativeY = context.getArgument("value", Float.class);
                                        sendText(context, setValueText(config.relativeY));
                                        return 1;
                                    })))
                    .then(ClientCommandManager.literal("normalScale")
                            .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg())
                                    .executes(context -> {
                                        config.normalScale = context.getArgument("value", Float.class);
                                        sendText(context, setValueText(config.normalScale));
                                        return 1;
                                    })))
                    .then(ClientCommandManager.literal("lowHpScale")
                            .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg())
                                    .executes(context -> {
                                        config.lowHpScale = context.getArgument("value", Float.class);
                                        sendText(context, setValueText(config.lowHpScale));
                                        return 1;
                                    })))
                    .then(ClientCommandManager.literal("targetDelay")
                            .then(ClientCommandManager.argument("value", LongArgumentType.longArg())
                                    .executes(context -> {
                                        config.targetDelay = context.getArgument("value", Long.class);
                                        sendText(context, setValueText(config.targetDelay));
                                        return 1;
                                    })))
                    .then(ClientCommandManager.literal("help")
                            .executes(context -> {
                                String prefix = "* ";
                                String message = prefix + "toggle - вкл/выкл мод;" + "\n" +
                                        prefix + "relativeX <float value> - относительные координаты по ширине, на которых будет отображаться индикатор." +
                                        "\n" + " Например: если установить значение 0.5 - это будет центр экрана по ширине, 0.0 - это левый край экрана" + "\n" +
                                        prefix + "relativeY <float value> - относительные координаты по высоте, на которых будет отображаться индикатор." +
                                        "\n" + " Например: если установить значение 0.5 - это будет центр экрана по высоте, 0.0 - это верхняя точка экрана" + "\n" +
                                        prefix + "normalScale <float value> - это масштаб индикатора когда у игрока > 10 HP." + "\n" +
                                        prefix + "lowHpScale <float value> - это соответственно масштаб индикатора когда у игрока < 10 HP." + "\n" +
                                        prefix + "targetDelay <long value> - это время отображения индикатора игрока после нанесения удара. Указывается в миллисекундах. (10000 миллисекунд - это 10 сек.)";
                                sendText(context, message);
                                return 1;
                            }))
            );
            dispatcher.register(ClientCommandManager.literal("zhi")
                    .redirect(dispatcher.getRoot().getChild("zakohealthindicator")));
        });
    }

    private String setValueText(float value) {
        return "Установлено значение: " + value;
    }

    private void sendText(CommandContext<FabricClientCommandSource> context, String text) {
        context.getSource().sendFeedback(Text.literal(text));
        saveConfig();
    }

    private void saveConfig() {
        AutoConfig.getConfigHolder(ModConfig.class).save();
    }
}
