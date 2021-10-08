package com.gamesense.client.module;

import com.gamesense.client.module.modules.combat.*;
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
        addMod(new KillAura());
        addMod(new AutoAnvil());
        addMod(new AutoArmor());
        addMod(new AutoClicker());
        addMod(new AutoCrystal());
        addMod(new AutoLog());
        addMod(new AutoSkull());
        addMod(new AutoTrap());
        addMod(new AutoTotem());
        addMod(new AutoWeb());
        addMod(new BedAura());
        addMod(new Blocker());
        addMod(new FastProjectile());
        addMod(new CevBreaker());
        addMod(new Criticals());
        addMod(new FastBow());
        addMod(new HoleFill());
        addMod(new OffHand());
        addMod(new PistonCrystal());
        addMod(new Quiver());
        addMod(new SelfTrap());
        addMod(new SelfWeb());
        addMod(new Surround());
        //Exploits
        addMod(new AntiDesync());
        addMod(new AntiHunger());
        addMod(new EntityAlert());
        addMod(new LiquidInteract());
        addMod(new MultiTask());
        addMod(new NoInteract());
        addMod(new NoSwing());
        addMod(new Reach());
        addMod(new PacketUse());
        addMod(new PacketXP());
        addMod(new PortalGodmode());
        addMod(new FastBreak());
        addMod(new Timer());
        //Movement
        addMod(new Anchor());
        addMod(new AntiLevitation());
        addMld(new AntiVoid());
        addMod(new AntiWeb());
        addMod(new AutoWalk());
        addMod(new EntityControl());
        addMod(new EntitySpeed());
        addMod(new Blink());
        addMod(new FastSwim());
        addMod(new HoleTP());
        addMod(new IceSpeed());
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
        addMod(new AutoGear());
        addMod(new AutoGG());
        addMod(new AutoReply());
        addMod(new AutoRespawn());
        addMod(new AutoTame());
        addMod(new AutoTool());
        addMod(new AutoMine()); 
        addMod(new ChatModifier());
        addMod(new ChatSuffix());
        addMod(new CoordLeakerReal());
        addMod(new FastPlace());
        addMod(new FakePlayer());
        addMod(new HotbarRefill());
        addMod(new MCF());
        addMod(new MCP());
        addMod(new NoEntityTrace());
        addMod(new PhysicsSpammer());
        addMod(new PvPInfo());
        addMod(new Scaffold());
        addMod(new SortInventory());
        addMod(new XCarry());
        //Render
        addMod(new BlockHighlight());
        addMod(new BreakESP());
        addMod(new Bucked());
        addMod(new CapesModule());
        addMod(new Chams());
        addMod(new CityESP());
        addMod(new ESP());
        addMod(new Freecam());
        addMod(new Fullbright());
        addMod(new HoleESP());
        addMod(new LogoutSpots());
        addMod(new Nametags());
        addMod(new NoRender());
        addMod(new RenderTweaks());
        addMod(new ShulkerViewer());
        addMod(new SkyColor());
        addMod(new SmallShield());
        addMod(new Tracers());
        addMod(new ViewModel());
        addMod(new VoidESP());
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
