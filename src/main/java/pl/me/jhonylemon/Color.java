package pl.me.jhonylemon;

import com.diogonunes.jcolor.Attribute;

import static com.diogonunes.jcolor.Attribute.TEXT_COLOR;

public enum Color {

    LIGHT_GREEN(TEXT_COLOR(156, 215, 0)),
    VIOLET(TEXT_COLOR(129, 47, 210)),
    LIGHT_PINK(TEXT_COLOR(255, 152, 243)),
    LIGHT_BLUE(TEXT_COLOR(2, 242, 203)),
    ORANGE(TEXT_COLOR(253, 177, 3)),
    DARK_PINK(TEXT_COLOR(224, 48, 131)),
    RED(TEXT_COLOR(225, 64, 56)),
    GREEN(TEXT_COLOR(0, 170, 61)),
    BLUE(TEXT_COLOR(17, 126, 227)),
    YELLOW(TEXT_COLOR(254, 242, 0)),
    GRAY(TEXT_COLOR(210, 210, 212)),
    GRAY_PINK(TEXT_COLOR(209, 176, 255));

    private final Attribute attribute;

    Color(Attribute attribute) {
        this.attribute = attribute;
    }

    public Attribute getColor() {
        return attribute;
    }
}
