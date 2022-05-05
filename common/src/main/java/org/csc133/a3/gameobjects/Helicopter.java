package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.Game;
import org.csc133.a3.interfaces.Steerable;

import java.util.ArrayList;

public class Helicopter extends Moveable implements Steerable {
    private final static int BUBBLE_RADIUS = 125;

    private int size, hRadius, centerX, centerY, fuel, water;
    private Point helipadCenterLocation, heliLocation;
    private int endHeadX, endHeadY, padSize;
    private double angle;
    private final int MAX_SPEED = 10;
    private boolean riverCollision;

    private static class HeloBubble extends GameObject {

        public HeloBubble() {
            setColor(ColorUtil.YELLOW);
            setDimension(new Dimension(2*Helicopter.BUBBLE_RADIUS,
                    2*BUBBLE_RADIUS));
        }

        @Override
        protected void localDraw(Graphics g, Point parentOrigin, Point screenOrigin) {
//            g.setColor(getColor());
            g.setColor(ColorUtil.MAGENTA);
            containerTranslate(g, parentOrigin);
            cn1ForwardPrimitiveTranslate(g, getDimension());
            g.drawArc(0,0, getDimension().getWidth(),
                    getDimension().getHeight(), 135, 270);
        }
    }



    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private ArrayList<GameObject> heloParts;

    public Helicopter(Point lz) {
        heloParts = new ArrayList<>();

        heloParts.add(new HeloBubble());

    }

    @Override
    protected void localDraw(Graphics g, Point parentOrigin,
                             Point screenOrigin) {
        for(GameObject go : heloParts) {
            go.draw(g, parentOrigin, screenOrigin);
        }
    }



//    public Helicopter(Dimension worldSize, Point heliCenter, int helipadSize) {
//        this.color = ColorUtil.rgb(252, 252, 28);
//        this.dimension = new Dimension(worldSize.getWidth(),
//                worldSize.getHeight());
//        this.location = heliCenter;
//        size = dimension.getHeight()/42;
//        this.speed = 0;
//        fuel = 0;
//        water = 0;
//        helipadCenterLocation = location;
//        hRadius = size/2;
//        heliLocation = new Point(helipadCenterLocation.getX() - hRadius,
//                helipadCenterLocation.getY());
//        centerX = heliLocation.getX() + hRadius;
//        centerY = heliLocation.getY() + hRadius;
//        angle = Math.toRadians(90);
//        endHeadX = centerX;
//        endHeadY = centerY - (size*2);
//        riverCollision = false;
//        padSize = helipadSize;
//    }


    // TODO: 4/27/22 Watch videos on how to draw hierarchical helicopter

    @Override
    public void move(){
        heliLocation.setY((int) (heliLocation.getY() - Math.sin(angle) *
                speed));
        centerY = heliLocation.getY() + hRadius;
        heliLocation.setX((int) (heliLocation.getX() + Math.cos(angle) *
                speed));
        centerX = heliLocation.getX() + hRadius;
        endHeadX = (int) (centerX + Math.cos(angle) * size*2);
        endHeadY = (int) (centerY - Math.sin(angle) * size*2);
        fuel -= (int) (Math.sqrt(speed) + 5);
    }

    public void speedUp() {
        if(speed < MAX_SPEED) {
            speed++;
        }
    }

    public void slowDown() {
        if(speed > 0) {
            speed--;
        }
    }

    @Override
    public void steerLeft() {
        angle += Math.toRadians(15);
        endHeadX = (int) (centerX + Math.cos(angle) * size*2);
        endHeadY = (int) (centerY - Math.sin(angle) * size*2);
    }

    @Override
    public void steerRight() {
        angle -= Math.toRadians(15);
        endHeadX = (int) (centerX + Math.cos(angle) * size*2);
        endHeadY = (int) (centerY - Math.sin(angle) * size*2);
    }

//    public void checkRiverCollision(Point riverLocation,
//                                    Dimension riverDimension) {
//        riverCollision = (centerX >= riverLocation.getX() && centerY >=
//                riverLocation.getY()) &&
//                (centerX <= (riverLocation.getX() + riverDimension.getWidth())
//                        && centerY <=
//                        (riverLocation.getY() + riverDimension.getHeight()));
//    }
//
//    public boolean checkFireCollision(Fire fire) {
//        return (centerX >= (fire.getFireLocation().getX() - fire.getRadius()) &&
//                centerY >= (fire.getFireLocation().getY() - fire.getRadius()))
//                && (centerX <= (fire.getFireLocation().getX() +
//                fire.getRadius()) && centerY <= (fire.getFireLocation().getY()
//                + fire.getRadius()));
//    }
//
//    public void drinkWater() {
//        if((riverCollision && speed <= 2) && water < 1000) {
//            water += 100;
//        }
//    }
//
//    public void fightFire(Fires fires) {
//        for(Fire fire : fires) {
//            if(fire.detected()) {
//                fire.reduceFire(water);
//            }
//            fire.setFalse();
//        }
//    }
//
//    public void dropWater() {
//        water = 0;
//    }
//
//    public void setFuel(int fuelIn) {
//        fuel = fuelIn;
//    }
//
//    public boolean checkFuel() {
//        return fuel <= 0;
//    }
//
//    public int getFuel() {
//        return fuel;
//    }
//
//    public boolean isOnPad() {
//        return (centerX >= (helipadCenterLocation.getX() - padSize/4) &&
//                centerY >= (helipadCenterLocation.getY() - padSize/4))
//                && (centerX <= (helipadCenterLocation.getX() +
//                padSize/4) && centerY <= (helipadCenterLocation.getY()
//                + padSize/4));
//    }
//
////    @Override
//    // TODO: 4/27/22 Read about local draw method and how it will be implemented
//
//    public void draw(Graphics g, Point containerOrigin) {
//        g.setColor(color);
//        g.fillArc(containerOrigin.getX() + heliLocation.getX(),
//                containerOrigin.getY() + heliLocation.getY(), size,
//                size, 0, 360);
//        g.drawLine(containerOrigin.getX() + centerX,
//                containerOrigin.getY() + centerY,
//                containerOrigin.getX() + endHeadX,
//                containerOrigin.getY() + endHeadY);
//        g.drawString("F: " + fuel,
//                containerOrigin.getX() + heliLocation.getX(),
//                containerOrigin.getY() + heliLocation.getY() +
//                        (int)(size*2.5));
//        g.drawString("W: " + water ,
//                containerOrigin.getX() + heliLocation.getX(),
//                containerOrigin.getY() + heliLocation.getY() +
//                        (int)(size*3.5));
//    }

    public int getSpeed() {
        return super.getSpeed();
    }

    @Override
    public int getHeadingAngle() {
        heading = (int)(Math.round(Math.toDegrees(angle)));
        if(heading > 360) {
            return heading -= 360;
        }
        else if(heading < 0) {
            return heading += 360;
        }
        return heading;
    }

}
