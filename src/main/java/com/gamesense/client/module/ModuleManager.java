package com.gamesense.client.module;

import com.gamesense.client.module.modules.combat.*;
import com.gamesense.client.module.modules.combat.Spammer;
import com.gamesense.client.module.modules.exploits.*;
import com.gamesense.client.module.modules.gui.ClickGuiModule;
import com.gamesense.client.module.modules.gui.ColorMain;
import com.gamesense.client.module.modules.gui.HUDEditor;
import com.gamesense.client.module.modules.hud.*;
import com.gamesense.client.module.modules.misc.*;
import com.gamesense.client.module.modules.movement.*;
import com.gamesense.client.module.modules.render.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;

public class ModuleManager {
    private static final LinkedHashMap<Class<? extends Module>, Module> modulesClassMap = new LinkedHashMap<>();
    private static final LinkedHashMap<String, Module> modulesNameMap = new LinkedHashMap<>();

    public static void init() {
        //Combat
        addMod(new KillAura()); // rework
        addMod(new AutoAnvil());
        addMod(new AutoArmor());
        addMod(new AutoCrystal());
        addMod(new AutoLog());
        addMod(new AutoMend());
        addMod(new Surround());
        addMod(new AutoTrap());
        addMod(new AutoWeb());
        addMod(new BedAura());
        addMod(new BowAim());
        addMod(new CevBreaker());
        addMod(new Criticals());
        addMod(new HoleFill());
        addMod(new OffHand());
        addMod(new PistonCrystal());
        addMod(new Quiver()); // rewrite
        addMod(new SelfFill());
        addMod(new SelfWeb());
        //Exploits
        addMod(new AntiHunger());
        addMod(new EntityAlert());
        addMod(new MultiTask());
        addMod(new NoInteract());
        addMod(new PingSpoof());
        addMod(new PortalChat());
        addMod(new FastBreak());
        addMod(new TickShift());
        addMod(new Timer());
        //Movement
        addMod(new Anchor());
        addMod(new AntiLevitation());
        addMod(new AntiVoid());
        addMod(new AntiWeb());
        addMod(new AutoWalk());
        addMod(new BoatFly());
        addMod(new ElytraFly());
        addMod(new EntityControl());
        addMod(new EntitySpeed());
        addMod(new Blink());
        addMod(new FastSwim());
        addMod(new NoFall());
        addMod(new NoSlow());
        addMod(new ReverseStep());
        addMod(new Speed());
        addMod(new Sprint());
        addMod(new Step());
        addMod(new Velocity());
        //Misc
        addMod(new Announcer());
        addMod(new AntiAFK());
        addMod(new AutoEat());
        addMod(new AutoGG());
        addMod(new AutoRespawn());
        addMod(new AutoTool());
        addMod(new ChatModifier());
        addMod(new ChatSuffix());
        addMod(new FastUse());
        addMod(new FakePlayer());
        addMod(new HotbarRefill());
        addMod(new MCF()); // merge -> MiddleClick
        addMod(new MCP()); // merge -> MiddleClick
        addMod(new NoEntityTrace());
        addMod(new PvPInfo());
        addMod(new Scaffold());
        addMod(new Spammer());
        addMod(new XCarry());
        //Render
        addMod(new BreakESP());
        addMod(new CapesModule());
        addMod(new Chams());
        addMod(new CityESP());
        addMod(new ESP());
        addMod(new Freecam());
        addMod(new Fullbright());
        addMod(new HoleESP());
        addMod(new LogoutSpots());
        addMod(new Nametags());
        addMod(new NoBob());
        addMod(new NoRender());
        addMod(new RenderTweaks());
        addMod(new ShulkerViewer());
        addMod(new SmallShield());
        addMod(new Tracers());
        addMod(new ViewModel());
        addMod(new VoidESP());
        addMod(new Xray());
        //HUD
        addMod(new ArmorHUD());
        addMod(new ArrayListModule());
        addMod(new CombatInfo());
        addMod(new Coordinates());
        addMod(new InventoryViewer());
        addMod(new Notifications());
        addMod(new PotionEffects());
        addMod(new Radar());
        addMod(new Speedometer());
        addMod(new TabGUIModule());
        addMod(new TargetHUD());
        addMod(new TargetInfo());
        addMod(new TextRadar());
        addMod(new Watermark());
        addMod(new Welcomer());
        //GUI
        addMod(new ClickGuiModule());
        addMod(new ColorMain());
        addMod(new HUDEditor());
    }

    public static void addMod(Module module) {
        modulesClassMap.put(module.getClass(), module);
        modulesNameMap.put(module.getName().toLowerCase(Locale.ROOT), module);
    }

    public static Collection<Module> getModules() {
        return modulesClassMap.values();
    }

    public static ArrayList<Module> getModulesInCategory(Category category) {
        ArrayList<Module> list = new ArrayList<>();

        for (Module module : modulesClassMap.values()) {
            if (!module.getCategory().equals(category)) continue;
            list.add(module);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Module> T getModule(Class<T> clazz) {
        return (T) modulesClassMap.get(clazz);
    }

    public static Module getModule(String name) {
        if (name == null) return null;
        return modulesNameMap.get(name.toLowerCase(Locale.ROOT));
    }

    public static boolean isModuleEnabled(Class<? extends Module> clazz) {
        Module module = getModule(clazz);
        return module != null && module.isEnabled();
    }

    public static boolean isModuleEnabled(String name) {
        Module module = getModule(name);
        return module != null && module.isEnabled();
    }
}
