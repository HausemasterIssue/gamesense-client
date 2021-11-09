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
        addMod(new KillAura()); // rework
        addMod(new AutoAnvil());
        addMod(new AutoArmor()); // improve (more customizability)
        addMod(new AutoCrystal()); // dechinesify, fix, and improve
        addMod(new AutoLog());
        addMod(new AutoMend()); // rewrite/merge with autoarmor
        addMod(new AutoTotem()); // idfk if this will even work, anyways, u need to fix it and clean it up
        addMod(new Surround()); // improve (get silent swap to work and just make it better)
        addMod(new AutoTrap()); // fix (fix rotations)
        addMod(new AutoWeb());
        addMod(new BedAura());
        addMod(new BowAim()); // improve (improve rotations)
        addMod(new CevBreaker());
        addMod(new Criticals());
        addMod(new HoleFill());
        addMod(new OffHand()); // rewrite / merge with autototem
        addMod(new PistonCrystal());
        addMod(new Quiver()); // rewrite
        addMod(new SelfFill()); // thats cap, i tested it and it worked fine. idk how the hell you run into unknown issues with code that has nothing wrong with it
        addMod(new SelfWeb());
        //Exploits
        addMod(new AntiHunger());
        addMod(new EntityAlert()); // debatable, useless but he insists
        addMod(new MultiTask()); // rewrite
        addMod(new NoInteract());
        addMod(new PingSpoof()); // rewrite
        addMod(new PortalChat());
        addMod(new FastBreak()); // improve (better renders)
        addMod(new TickShift());
        addMod(new Timer());
        //Movement
        addMod(new Anchor());
        addMod(new AntiLevitation());
        addMod(new AntiVoid());
        addMod(new AntiWeb());
        addMod(new AutoWalk()); // this isnt needed, but whatever
        addMod(new BoatFly());
        addMod(new ElytraFly()); // improve (stop the player from constantly falling + add boost mode)
        addMod(new EntityControl()); // fix entitycontrol mixins
        addMod(new Blink()); // rewrite, probably shit code + no modes
        addMod(new FastSwim()); // current code works okay but could use a lot of rewriting and improving
        addMod(new NoFall());
        addMod(new NoSlow());
        addMod(new ReverseStep()); // merge with holetp to make fastfall + improve
        addMod(new Speed()); // improve, add more settings
        addMod(new Sprint());
        addMod(new Step());
        addMod(new Velocity()); // improve (bobbers, nopushblocks, etc.)
        //Misc
        addMod(new Announcer()); // improve (add join/leave msgs)
        addMod(new AntiAFK()); // rewrite
        addMod(new AutoEat()); // wtf, you cant hold down right click?
        addMod(new AutoGG());
        addMod(new AutoRespawn()); // improve (save death coords)
        addMod(new AutoTool()); // why
        addMod(new ChatModifier());
        addMod(new ChatSuffix());
        addMod(new FastUse()); // packetuse != fastuse, keep them seperate
        addMod(new FakePlayer()); // moving fakeplayer (its already in renosense have a look at it)
        addMod(new HotbarRefill());
        addMod(new MiddleClick());
        addMod(new NoEntityTrace());
        addMod(new PvPInfo()); // rename or merge/rewrite
        addMod(new Scaffold()); // eh, your scaffold worked just as bad, it needs a serious serious rewrite
        addMod(new Spammer());
        addMod(new XCarry());
        //Render
        addMod(new BreakESP()); // improve (make breaking anims smoother, perrys europa src does it)
        addMod(new CapesModule());
        addMod(new Chams());
        addMod(new CityESP());
        addMod(new ESP());
        addMod(new Freecam());
        addMod(new Fullbright());
        addMod(new HoleESP());
        addMod(new LogoutSpots());
        addMod(new Nametags());
        addMod(new NoBob()); // rewrite?? no fucking clue
        addMod(new NoRender());
        addMod(new ShulkerViewer());
        addMod(new SmallShield()); // stupid asf, but hause insists
        addMod(new Tracers());
        addMod(new ViewClip());
        addMod(new NoBob()); // rewrite (make it look like futures/momentums)
        addMod(new NoRender()); // improve (wither skulls etc.)
        addMod(new ShulkerViewer());
        addMod(new SmallShield()); // stupid asf, but hause insists
        addMod(new Tracers()); // improve (thin out tracers a bit)
        addMod(new ViewModel());
        addMod(new VoidESP());
        addMod(new Xray());
        //HUD
        addMod(new ArmorHUD());
        addMod(new ArrayListModule());
        addMod(new CombatInfo());
        addMod(new Coordinates()); // rewrite (make them look more like phobos's or futures)
        addMod(new InventoryViewer());
        addMod(new Notifications()); // rewrite (make its own seperate module in chat or something)
        addMod(new PotionEffects()); // improve (make the colors look like the actual potion effects, similar to future or xulu)
        addMod(new Radar());
        addMod(new Speedometer());
        addMod(new TabGUIModule());
        addMod(new TargetHUD());
        addMod(new TargetInfo());
        addMod(new TextRadar());
        addMod(new Watermark());
        addMod(new Welcomer()); // improve (add more welcomers)
        //GUI
        addMod(new ClickGuiModule()); // improve (add little dots n shit to the clickgui)
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
