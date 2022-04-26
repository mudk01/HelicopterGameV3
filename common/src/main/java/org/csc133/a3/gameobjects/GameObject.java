package org.csc133.a3.gameobjects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

public abstract class GameObject {
    int color;
    Dimension dimension;
    Point location;

    public GameObject() {

    }

    public GameObject(Point location, Dimension dimension, int color) {
        this.location = location;
        this.dimension = dimension;
        this.color = color;
    }

    public Point getLocation() {
        return location;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setLocation(Point location) {}

    public void draw(Graphics g, Point point) {
    }
}

