package com.gamesense.client;

import com.gamesense.api.config.LoadConfig;
import com.gamesense.api.setting.SettingsManager;
import com.gamesense.api.util.font.CFontRenderer;
import com.gamesense.api.util.player.social.SocialManager;
import com.gamesense.api.util.render.CapeUtil;
import com.gamesense.client.clickgui.GameSenseGUI;
import com.gamesense.client.command.CommandManager;
import com.gamesense.client.manager.ManagerLoader;
import com.gamesense.client.module.ModuleManager;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.awt.*;

@Mod(modid = GameSense.MODID, name = GameSense.MODNAME, version = GameSense.MODVER)
public class GameSense {

    public static final String MODNAME = "SpiderSense";
    public static final String MODID = "spidersense";
    public static final String MODVER = "v2.3.9";

    public static final Logger LOGGER = LogManager.getLogger(MODNAME);
    public static final EventBus EVENT_BUS = new EventManager();

    @Mod.Instance
    public static GameSense INSTANCE;

    public GameSense() {
        INSTANCE = this;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Display.setTitle(MODNAME + " " + MODVER);

        LOGGER.info("Starting up " + MODNAME + " " + MODVER + "!");
        startClient();
        LOGGER.info("Finished initialization for " + MODNAME + " " + MODVER + "!");
        LOGGER.info("OwO how did you find this?");
    }

    public CFontRenderer cFontRenderer;
    public GameSenseGUI gameSenseGUI;

    private void startClient() {
        //VersionChecker.init(MODVER);
        //LOGGER.info("Version checked!");

        cFontRenderer = new CFontRenderer(new Font("Verdana", Font.PLAIN, 18), true, true); // no custom font on startup
        LOGGER.info("Custom font initialized!");

        SettingsManager.init();
        LOGGER.info("Settings initialized!");

        SocialManager.init();
        LOGGER.info("Friends/Enemies initialized!");

        ModuleManager.init();
        LOGGER.info("Modules initialized!");

        CommandManager.init();
        LOGGER.info("Commands initialized!");

        ManagerLoader.init();
        LOGGER.info("Managers initialized!");

        gameSenseGUI = new GameSenseGUI();
        LOGGER.info("GUI initialized!");

        CapeUtil.init();
        LOGGER.info("Capes initialized!");

        LoadConfig.init();
        LOGGER.info("Config initialized!");
    }
}
