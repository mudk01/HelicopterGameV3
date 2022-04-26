package org.csc133.a3.gameobjects;

abstract class Moveable extends GameObject {
    int speed;
    int heading;

    public Moveable() {
        super();
    }

    public void move() {
    }

    public int getSpeed() {
        return speed;
    }

    public int getHeadingAngle() {
        return heading;
    }

    public void setHeading(int heading){
        this.heading = heading;
    }

}
