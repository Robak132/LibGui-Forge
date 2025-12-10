package io.github.robak132.libgui_forge;

public abstract class Utilities {
    public static int clampInt(int pValue, int pMin, int pMax) {
        return Math.min(Math.max(pValue, pMin), pMax);
    }
}
