package org.csc133.a3.gameobjects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

import java.util.ArrayList;

public abstract class FlightPath {
    //continue video

    public class Traversal extends BezierCurve {
        double t;
        boolean active = false;
        NonPlayerHelicopter nph;
    }
    public static class BezierCurve extends GameObject {
        ArrayList<Point> controlPoints;
        int curveID;
        boolean activePath = false;

        @Override
        protected void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {

        }
    }
}
