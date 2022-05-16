package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.gameobjects.parts.Arc;
import org.csc133.a3.gameobjects.parts.Rectangle;

import java.util.ArrayList;

public class Helipad extends Fixed {
//    private Point rectangleLocation, centerLocation;
    private Dimension dimension;
    private int boxSize;
    private int circleSize;
    private int radius;
    private HelipadBox helipadBox;
    private HelipadCircle helipadCircle;

    private class HelipadBox extends Rectangle {
        public HelipadBox() {
            super(ColorUtil.rgb(139,139,139),
                    1, 1,
                    0, 0,
                    1, 1, 0);
        }
    }

    private class HelipadCircle extends Arc {
        public HelipadCircle() {
            super(ColorUtil.rgb(139,139,139),
                    1,1,
                    0, 0,
                    1, 1, 0,
                    0, 360);
        }
    }
    private ArrayList<GameObject> helipadParts;

    public Helipad(Dimension worldSize) {
        helipadParts = new ArrayList<>();
        helipadBox = new HelipadBox();
        helipadCircle = new HelipadCircle();
        dimension = new Dimension(worldSize.getWidth(),
                worldSize.getHeight());
        boxSize = (int) (dimension.getHeight()/8.5);
        circleSize = (int) (dimension.getHeight()/11.5);
        helipadBox.setDimension(new Dimension(boxSize, boxSize));
        helipadCircle.setDimension(new Dimension(circleSize, circleSize));
        radius = circleSize/2;
        helipadParts.add(helipadBox);
        helipadParts.add(helipadCircle);
        translate(dimension.getWidth()/2,
                boxSize*1.5);
    }

    @Override
    protected void localDraw(Graphics g, Point parentOrigin,
                             Point screenOrigin) {
        for(GameObject go : helipadParts) {
            go.draw(g, parentOrigin, screenOrigin);
        }
    }

    public Transform getTransform() {
        return myTranslation;
    }

    public int getHelipadBoxSize() {
        return boxSize;
    }

    public int getHelipadSize() {
        return circleSize;
    }
}
