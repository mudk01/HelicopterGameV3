package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

public class River extends Fixed{

    public River(Dimension worldSize) {
        this.color = ColorUtil.rgb(0,52,254);
        this.dimension = new Dimension(worldSize.getWidth(),
                worldSize.getHeight()/9);
        location = new Point(0,
                worldSize.getHeight()/3-this.dimension.getHeight());
    }

    @Override
    public Point getLocation() {
        return super.getLocation();
    }

    @Override
    public Dimension getDimension() {
        return super.getDimension();
    }

//    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(color);
        g.drawRect(containerOrigin.getX() + location.getX(),
                containerOrigin.getY() + location.getY(),
                dimension.getWidth(),
                dimension.getHeight(),5);
    }


}
