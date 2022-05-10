package org.csc133.a3.gameobjects.parts;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.gameobjects.GameObject;

public class Rectangle extends GameObject {

    public Rectangle(int color,
                     int width, int height,
                     float tx, float ty,
                     float sx, float sy,
                     float degrees) {
        setColor(color);
        setDimension(new Dimension(width, height));
        translate(tx, ty);
        scale(sx, sy);
        rotate(degrees);
    }

    @Override
    protected void localDraw(Graphics g, Point parentOrigin, Point screenOrigin) {
        g.setColor(getColor());
        containerTranslate(g, parentOrigin);
        cn1ForwardPrimitiveTranslate(g,getDimension());
        g.drawRect(0,0,getDimension().getWidth(), getDimension().getHeight());
    }
}
