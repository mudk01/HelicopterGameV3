package org.csc133.a3.gameobjects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Fires extends GameObjectCollection<Fire>{

    public Fires() {
        super();
    }

    @Override
    public void localDraw(Graphics g, Point parentOrigin, Point screenOrigin) {
        for(Fire fire : getGameObjects()) {
            if(fire.getCurrentState() == BurningFire.instance())
                fire.draw(g, parentOrigin, screenOrigin);
        }
    }

}
