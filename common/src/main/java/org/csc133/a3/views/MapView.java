package org.csc133.a3.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Shape;
import org.csc133.a3.GameWorld;
import org.csc133.a3.gameobjects.GameObject;


public class MapView extends Container {
    private GameWorld gw;
    private float winLeft, winBottom, winRight, winTop, winWidth, winHeight;
    Transform worldToND, ndToDisplay, theVTM;

    public MapView(GameWorld gw) {
        this.gw = gw;
        winLeft = 0;
        winBottom = 0;
        winRight = this.getWidth()/2;
        winTop = this.getHeight()/2;
        winWidth = winRight - winLeft;
        winHeight = winTop - winBottom;

        myTriangle = new Triangle((int)(winHeight/5), (int)(winHeight/5));
        myTriange.translate(winWidth/2, winHeight/2);
        myTriange.rotate(45);
        myTriangle.scale(1,2);
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
        Point pCmpRelPrnt = new Point(this.getX(), this.getY());
        Point pCmpRelScrn = new Point(getAbsoluteX(), getAbsoluteY());
        for(Shape s : shapeCollection)
            s.draw(g, pCmpRelPrnt, pCmpRelScrn);
        g.resetAffine();
//        for(GameObject go: gw.getGameObjectCollection()) {
//            go.draw(g, new Point(this.getX(), this.getY()));
//        }
    }
    private Transform buildWorldToNDXform(float winWidth, float winHeight,
                                          float winLeft, float winBottom) {
        Transform tmpXfrom = Transform.makeIdentity();
        tmpXfrom.scale((1 / winWidth), (1 / winHeight));
        tmpXfrom.translate(-winLeft, -winBottom);
        return tmpXfrom;
    }

    private Transform buildNDToDisplayXform (float displayWidth, float displayHeight) {
        Transform tmpXfrom = Transform.makeIdentity();
        tmpXfrom.translate(0, displayHeight);
        tmpXfrom.scale(displayWidth, -displayHeight);
        return tmpXfrom;
    }

    private void setupVTM(Graphics g) {
        Transform worldToND, ndToDisplay, theVTM;

        winLeft=winBottom=0;
        winRight = this.getWidth();
        winTop = this.getHeight();

        float winHeight = winTop-winBottom;
        float winWidth = winRight-winLeft;

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
        gXform.concatenate(theVTM);
    }

    public void displayTransform(Graphics g) {
        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        gXform.translate(getAbsoluteX(), getAbsoluteY());

        //apply display mapping
        //
        gXform.translate(0, getHeight());
        g.setTransform(gXform);

        //move the drawing coordinates as part of the "local origin"
        // transformations
        //
        gXform.translate(-getAbsoluteX(), -getAbsoluteY());
        g.setTransform(gXform);

    }

    }
