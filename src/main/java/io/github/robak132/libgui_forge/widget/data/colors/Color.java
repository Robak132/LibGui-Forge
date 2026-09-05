package io.github.robak132.libgui_forge.widget.data.colors;

public abstract class Color {

    public static final Color WHITE = rgb(0xFF_FFFFFF);
    public static final Color BLACK = rgb(0xFF_000000);
    public static final Color RED = rgb(0xFF_FF0000);
    public static final Color GREEN = rgb(0xFF_00FF00);
    public static final Color BLUE = rgb(0xFF_0000FF);

    public static final Color WHITE_DYE = rgb(0xFF_F9FFFE);
    public static final Color ORANGE_DYE = rgb(0xFF_F9801D);
    public static final Color MAGENTA_DYE = rgb(0xFF_C74EBD);
    public static final Color LIGHT_BLUE_DYE = rgb(0xFF_3AB3DA);
    public static final Color YELLOW_DYE = rgb(0xFF_FED83D);
    public static final Color LIME_DYE = rgb(0xFF_80C71F);
    public static final Color PINK_DYE = rgb(0xFF_F38BAA);
    public static final Color GRAY_DYE = rgb(0xFF_474F52);
    public static final Color LIGHT_GRAY_DYE = rgb(0xFF_9D9D97);
    public static final Color CYAN_DYE = rgb(0xFF_169C9C);
    public static final Color PURPLE_DYE = rgb(0xFF_8932B8);
    public static final Color BLUE_DYE = rgb(0xFF_3C44AA);
    public static final Color BROWN_DYE = rgb(0xFF_835432);
    public static final Color GREEN_DYE = rgb(0xFF_5E7C16);
    public static final Color RED_DYE = rgb(0xFF_B02E26);
    public static final Color BLACK_DYE = rgb(0xFF_1D1D21);

    protected static final Color[] DYE_COLORS = {
            WHITE_DYE, ORANGE_DYE, MAGENTA_DYE, LIGHT_BLUE_DYE, YELLOW_DYE, LIME_DYE, PINK_DYE, GRAY_DYE,
            LIGHT_GRAY_DYE, CYAN_DYE, PURPLE_DYE, BLUE_DYE, BROWN_DYE, GREEN_DYE, RED_DYE, BLACK_DYE
    };

    protected int alpha;

    public static Color rgb(int value) {
        return new RGB(value);
    }

    public static Color rgb(int alpha, int red, int green, int blue) {
        return new RGB(alpha, red, green, blue);
    }

    public static Color opaqueRgb(int value) {
        return new RGB(value | 0xFF_000000);
    }

    public static Color create(ColorModel model, Number value1, Number value2, Number value3) {
        return create(model, 255, value1, value2, value3);
    }

    public static Color create(ColorModel model, int alpha, Number value1, Number value2, Number value3) {
        return switch (model) {
            case RGB -> new RGB(alpha, value1.intValue(), value2.intValue(), value3.intValue());
            case HSV -> new HSV(alpha, value1.intValue(), value2.intValue(), value3.intValue());
            case HSL -> new HSL(alpha, value1.intValue(), value2.intValue(), value3.intValue());
            case LAB -> new OkLAB(alpha, value1.floatValue(), value2.floatValue(), value3.floatValue());
        };
    }

    public String toHexString() {
        RGB rgb = toRGB();
        return String.format("#%02X%02X%02X", rgb.red(), rgb.green(), rgb.blue());
    }

    public int argb() {
        RGB rgb = toRGB();
        return (rgb.alpha() << 24) | (rgb.red() << 16) | (rgb.green() << 8) | rgb.blue();
    }

    public Color toModel(ColorModel model) {
        switch (model) {
            case RGB -> {
                return toRGB();
            }
            case HSV -> {
                return toHSV();
            }
            case HSL -> {
                return toHSL();
            }
            case LAB -> {
                return toOkLAB();
            }
            default -> throw new IllegalArgumentException("Unknown color model: " + model);
        }
    }

    public abstract RGB toRGB();

    public HSL toHSL() {
        return toRGB().toHSL();
    }

    public HSV toHSV() {
        return toRGB().toHSV();
    }

    public OkLAB toOkLAB() {
        return toRGB().toOkLAB();
    }

    public int alpha() {
        return alpha;
    }

    public abstract Number ch0();

    public abstract Number ch1();

    public abstract Number ch2();

    public enum ColorModel {
        RGB,
        HSV,
        HSL,
        LAB
    }
}
