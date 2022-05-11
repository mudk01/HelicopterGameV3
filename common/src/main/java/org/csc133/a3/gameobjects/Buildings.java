package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Buildings extends GameObjectCollection<Building>{

    public Buildings() {
        super();
    }

    @Override
    protected void localDraw(Graphics g, Point parentOrigin,
                             Point screenOrigin) {
        for(Building building : getGameObjects()) {
            building.draw(g, parentOrigin, screenOrigin);
        }
    }

}