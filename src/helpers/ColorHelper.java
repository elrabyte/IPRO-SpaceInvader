package helpers;

import java.awt.Color;

public class ColorHelper {
    
    public Color getColorForHp(Color baseColor, int currentHp, int maxHp) {
        int alpha = (currentHp * 255) / maxHp;
        return new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), alpha);
    }
}
