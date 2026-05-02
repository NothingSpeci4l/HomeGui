package fr.gregwll.homeGui.utils;

import fr.gregwll.homeGui.HomeGui;

import java.io.File;

public class Constents {

    public static File getSaveDir() {
        return new File(HomeGui.getInstance().getDataFolder(), "/homes/");
    }

    public static String getPrefix() {
        return ("§f[" + HomeGui.getInstance().getConfigManager().getPrefix() + "§f] ");
    }
}
