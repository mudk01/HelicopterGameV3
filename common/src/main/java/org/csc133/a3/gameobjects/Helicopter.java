package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.Game;
import org.csc133.a3.gameobjects.parts.Arc;
import org.csc133.a3.gameobjects.parts.Rectangle;
import org.csc133.a3.interfaces.Steerable;

import java.util.ArrayList;

public class Helicopter extends Moveable implements Steerable {
    private final static int BUBBLE_RADIUS = 125;
    private final static int ENGINE_BLOCK_WIDTH = (int)(BUBBLE_RADIUS * 1.8);
    private final static int ENGINE_BLOCK_HEIGHT = ENGINE_BLOCK_WIDTH / 3;
    private final static int BLADE_WIDTH = 25;
    private final static int BLADE_LENGTH = BUBBLE_RADIUS * 5;
    private final static int RAIL_WIDTH = 25;
    private final static int RAIL_LENGTH = (int) (BLADE_LENGTH/1.5);
    private final static int LEG_WIDTH = 45;
    private final static int LEG_HEIGHT = 10;
    private final static int TAIL_WIDTH = 10;
    private final static int TAIL_HEIGHT = (int) (RAIL_LENGTH*0.85);

    private int size, hRadius, centerX, centerY, fuel, water;
    private Point helipadCenterLocation, heliLocation;
    private int endHeadX, endHeadY, padSize;
    private double angle;
    private final int MAX_SPEED = 10;
    private boolean riverCollision;


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static class HeloBubble extends Arc {

        public HeloBubble() {
            super(ColorUtil.MAGENTA,
                    2*BUBBLE_RADIUS,
                    2*BUBBLE_RADIUS,
                    0, (float)(-2*BUBBLE_RADIUS*.06),
                    1,1,
                    0,
                    135,270);

        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static class HeloEngineBlock extends Rectangle {
        public HeloEngineBlock() {
            super(ColorUtil.MAGENTA,
                    ENGINE_BLOCK_WIDTH,
                    ENGINE_BLOCK_HEIGHT,
                    0, (float) (-ENGINE_BLOCK_HEIGHT*1.9),
                    1, 1, 0);
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static class HeloBlade extends Rectangle {
        public HeloBlade() {
            super(ColorUtil.LTGRAY,
                    BLADE_LENGTH,
                    BLADE_WIDTH,
                    0, (float) (-ENGINE_BLOCK_HEIGHT * 1.9),
                    1,1,
                    42);
        }

        public void updateLocalTransforms(double rotationSpeed) {
            this.rotate(rotationSpeed);
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static class HeloBladeShaft extends Arc {

        public HeloBladeShaft() {
            super(ColorUtil.GRAY,
                    2 * BLADE_WIDTH / 3,
                    2 * BLADE_WIDTH / 3,
                    0, (float)(-ENGINE_BLOCK_HEIGHT * 1.9),
                    1,1,
                    0,
                    0,360);
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static class HeloRail extends Rectangle{
        public HeloRail(float orientation){
            super(ColorUtil.MAGENTA,
                    RAIL_WIDTH, RAIL_LENGTH,
                    (float) (orientation*Helicopter.RAIL_LENGTH/2.5),
                    -RAIL_WIDTH/2,
                    1,1,0);
        }
//        @Override
//        protected void localDraw(Graphics g, Point parentOrigin,
//                                 Point screenOrigin) {
//            super.localDraw(g,parentOrigin,screenOrigin);
//            g.drawRect(-getDimension().getWidth()/2,
//                    -getDimension().getHeight()/2,
//                    getDimension().getWidth(), getDimension().getHeight());
//        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static class HeloLowerLeg extends Rectangle{
        public HeloLowerLeg(float orientation){
            super(ColorUtil.GRAY,
                    LEG_WIDTH, LEG_HEIGHT,
                    (float) (orientation*ENGINE_BLOCK_WIDTH/2
                                                + orientation*LEG_WIDTH/2),
                    (float) (-ENGINE_BLOCK_HEIGHT*2),
                    1,1,0);
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static class HeloUpperLeg extends Rectangle{
        public HeloUpperLeg(float orientation){
            super(ColorUtil.GRAY,
                    Helicopter.LEG_WIDTH, Helicopter.LEG_HEIGHT,
                    (float) (orientation*Helicopter.ENGINE_BLOCK_WIDTH/2
                            + orientation*Helicopter.LEG_WIDTH/2),
                    (float) (Helicopter.BUBBLE_RADIUS -
                            Helicopter.LEG_HEIGHT*10),
                    1,1,0);
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static class HeloTailBrace extends Rectangle {
        public HeloTailBrace(){
            super(ColorUtil.BLACK,
                    TAIL_WIDTH, TAIL_HEIGHT,
                    (float) (0),
                    (float) (-TAIL_HEIGHT),
                    1,1,0);

        }
    }


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Helicopter State Pattern
    //

    HeloState heloState;

    private void changeState(HeloState heloState){
        this.heloState=heloState;
    }

    //````````````````````````````````````````````````````````````````````````
    private abstract class HeloState{
        Helicopter getHelo() {
            return Helicopter.this;
        }
        public void startOrStopEngine(){}
        public void accelerate() {}
        public boolean hasLandedAt() {
            return false;
        }
        public void updateLocalTransforms(){
        }
    }

    //````````````````````````````````````````````````````````````````````````
    private class Off extends HeloState{

        @Override
        public void startOrStopEngine(){
            getHelo().changeState(new Starting());
        }

        @Override
        public boolean hasLandedAt() {
            // check other requirements
            return true; // some boolean expression
        }
    }

    //````````````````````````````````````````````````````````````````````````
    private class Starting extends HeloState{
        @Override
        public void startOrStopEngine(){
            getHelo().changeState(new Stopping());
        }
    }

    //````````````````````````````````````````````````````````````````````````
    private class Stopping extends HeloState{
        @Override
        public void startOrStopEngine(){
            getHelo().changeState(new Starting());
        }
    }

    //````````````````````````````````````````````````````````````````````````
    private class Ready extends HeloState{
        @Override
        public void startOrStopEngine(){
            // conditions go here to test for whether or not we can stop
            // the engine
            if(1>2){
                getHelo().changeState(new Stopping());
            }
        }
        public void accelerate(){
            // do some acceleration
        }
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private ArrayList<GameObject> heloParts;

    private HeloBlade heloBlade;
    public Helicopter(Point lz) {
        heloParts = new ArrayList<>();

        heloParts.add(new HeloBubble());
        heloParts.add(new HeloEngineBlock());
        heloBlade = new HeloBlade();
        heloParts.add(heloBlade);
        heloParts.add(new HeloBladeShaft());
        heloParts.add(new HeloRail(-1));
        heloParts.add(new HeloRail(1));
        heloParts.add(new HeloLowerLeg(1));
        heloParts.add(new HeloLowerLeg(-1));
        heloParts.add(new HeloUpperLeg(1));
        heloParts.add(new HeloUpperLeg(-1));
        heloParts.add(new HeloTailBrace());
    }

    @Override
    protected void localDraw(Graphics g, Point parentOrigin,
                             Point screenOrigin) {
        for(GameObject go : heloParts) {
            go.draw(g, parentOrigin, screenOrigin);
        }
    }

    public void updateLocalTransforms() {
        heloBlade.updateLocalTransforms(-10d);
    }
    public void startOrStopEngine() {
        heloState.startOrStopEngine();
    }
    public void accelerate() {
        heloState.accelerate();
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
