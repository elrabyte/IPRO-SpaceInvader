package helpers;

import java.awt.Color;

public class ColorHelper {
    
    public Color getColorForHp(int maxHp, int currentHp) {
        int hpColor = Math.max(0, Math.min(255, currentHp * 60 + 60));
        return new Color(hpColor, hpColor / 2, 30);
    }
}
