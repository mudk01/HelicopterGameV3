package org.csc133.a3.gameobjects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class AddString extends GameObject{
    private String string;
    private int stringColor;

    public AddString(String text, int color, Point location) {
        scale(1,-1);
        translate(location.getX(), location.getY());
        string = text;
        stringColor = color;

    }
    @Override
    protected void localDraw(Graphics g, Point parentOrigin,
                             Point screenOrigin) {
        containerTranslate(g, parentOrigin);
        g.drawString("" + string,0,0);
    }
}
