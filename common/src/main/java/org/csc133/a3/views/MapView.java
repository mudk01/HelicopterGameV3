package org.csc133.a3.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.GameWorld;
import org.csc133.a3.gameobjects.GameObject;


public class MapView extends Container {
    private GameWorld gw;
    private float winLeft, winBottom, winRight, winTop, winWidth, winHeight;
    Transform worldToND, ndToDisplay, theVTM;

    public MapView(GameWorld gw) {
        this.gw = gw;
    }


    @Override
    public void laidOut() {
        gw.setDimension(new Dimension(this.getWidth(), this.getHeight()));
        gw.init();
    }

    @Override
    public void paint(Graphics g) {
        this.getStyle().setBgColor(ColorUtil.rgb(0, 0, 0));
        super.paint(g);
        Point parentOrigin = new Point(this.getX(), this.getY());
        Point screenOrigin = new Point(getAbsoluteX(), getAbsoluteY());
        setupVTM(g);
        for (GameObject go : gw.getGameObjectCollection()) {
            go.draw(g, parentOrigin, screenOrigin);
            go.updateLocalTransforms();
        }
        g.resetAffine();
    }

    private Transform buildWorldToNDXform(float winWidth, float winHeight,
                                          float winLeft, float winBottom) {
        Transform tmpXform = Transform.makeIdentity();
        tmpXform.scale(1 / winWidth, 1 / winHeight);
        tmpXform.translate(-winLeft, -winBottom);
        return tmpXform;
    }

    private Transform buildNDToDisplayXform(float displayWidth,
                                            float displayHeight) {
        Transform tmpXform = Transform.makeIdentity();
        tmpXform.translate(0, displayHeight);
        tmpXform.scale(displayWidth, -displayHeight);
        return tmpXform;
    }

    private void setupVTM(Graphics g) {
        Transform worldToND, ndToDisplay, theVTM;

        winLeft = winBottom = 0;
        winRight = this.getWidth();
        winTop = this.getHeight();

        float winHeight = winTop - winBottom;
        float winWidth = winRight - winLeft;

        worldToND = buildWorldToNDXform(winWidth, winHeight, winLeft,
                winBottom);
        ndToDisplay = buildNDToDisplayXform(this.getWidth(), this.getHeight());
        theVTM = ndToDisplay.copy();
        theVTM.concatenate(worldToND);

        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        gXform.translate(getAbsoluteX(), getAbsoluteY());
        gXform.concatenate(theVTM);
        gXform.translate(-getAbsoluteX(), -getAbsoluteY());
        g.setTransform(gXform);
    }

    public void displayTransform(Graphics g) {
        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        gXform.translate(getAbsoluteX(), getAbsoluteY());

        // apply display mapping
        //
        gXform.translate(0, getHeight());
        gXform.scale(1f, -1f);

        // move the drawing coordinates as part of the "local origin"
        // transformations
        //
        gXform.translate(-getAbsoluteX(), -getAbsoluteY());
        g.setTransform(gXform);
    }
}

