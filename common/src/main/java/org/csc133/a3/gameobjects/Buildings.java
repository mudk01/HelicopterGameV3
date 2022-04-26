package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Buildings extends GameObjectCollection<Building>{

    public Buildings() {
        super();
        this.color = ColorUtil.rgb(255,0,0);
    }

//    @Override
    public void draw(Graphics g, Point containerOrigin) {
        for(Building building : getGameObjects()) {
            building.draw(g, containerOrigin);
        }
    }

}