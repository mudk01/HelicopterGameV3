package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
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

    private int size;
    private int hRadius;
    private int centerX;
    private int centerY;
    private int fuel;
    private int water;
    private Point helipadCenterLocation, heliLocation;
    private int padSize;
    private double angle;
    private final int MAX_SPEED = 10;
    private boolean riverCollision;


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static class HeloBubble extends Arc {

        public HeloBubble() {
            super(ColorUtil.rgb(252, 252, 28),
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
            super(ColorUtil.rgb(252, 252, 28),
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
            super(ColorUtil.rgb(252, 252, 28),
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
            super(ColorUtil.rgb(252, 252, 28),
                    RAIL_WIDTH, RAIL_LENGTH,
                    (float) (orientation*Helicopter.RAIL_LENGTH/2.5),
                    -RAIL_WIDTH/2,
                    1,1,0);
        }
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
        @Override
        protected void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.fillRect(0,0,getDimension().getWidth(),
                    getDimension().getHeight());
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
        @Override
        protected void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.fillRect(0,0,getDimension().getWidth(),
                    getDimension().getHeight());
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static class HeloTailBrace extends Rectangle {
        public HeloTailBrace(){
            super(ColorUtil.rgb(252, 252, 28),
                    TAIL_WIDTH, TAIL_HEIGHT,
                    (float) (0),
                    (float) (-TAIL_HEIGHT),
                    1,1,0);
        }
        @Override
        protected void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.fillRect(0,0,getDimension().getWidth(),
                    getDimension().getHeight());
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static class HeloTailSide extends Rectangle {
        private int side;
        public HeloTailSide(int orientation) {
            super(ColorUtil.rgb(252, 252, 28),
                    1, TAIL_HEIGHT,
                    (float) orientation * (ENGINE_BLOCK_WIDTH/4),
                    (float) ((-ENGINE_BLOCK_HEIGHT+TAIL_WIDTH*1.5)),
                    1, 1, 0);
            side = orientation;
        }

        @Override
        protected void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            g.drawLine(0, 0,
                    (-side*ENGINE_BLOCK_WIDTH/4 + (side*TAIL_WIDTH/2)),
                     -TAIL_HEIGHT);
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static class HeloTailLine extends Rectangle {
        private int side;
        public HeloTailLine(int orientation) {
            super(ColorUtil.rgb(252, 252, 28),
                    1, TAIL_HEIGHT,
                    (float) orientation * (ENGINE_BLOCK_WIDTH/4),
                    (float) ((-ENGINE_BLOCK_HEIGHT+TAIL_WIDTH*1.5)),
                    1, 1, 0);
            side = orientation;
        }

        @Override
        protected void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            g.drawLine(0, 0,
                    (int) (-side*ENGINE_BLOCK_WIDTH/2.5),
                    (int) (-TAIL_HEIGHT/2));
            g.drawLine((int) (-side*ENGINE_BLOCK_WIDTH/2.5),
                    (int) (-TAIL_HEIGHT/2),
                    (int) (-side*(ENGINE_BLOCK_WIDTH/4)),
                    -TAIL_HEIGHT+TAIL_HEIGHT/10);
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static class HeloRotorBox extends Rectangle {
        public HeloRotorBox() {
            super(ColorUtil.GRAY,
                    (int) (TAIL_WIDTH*2.5), (int) (TAIL_WIDTH*2.5),
                    0, -TAIL_HEIGHT + -TAIL_HEIGHT/2,
                    1, 1, 0);
        }
        @Override
        protected void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.fillRect(0,0,getDimension().getWidth(),
                    getDimension().getHeight());
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static class HeloRotorJoint extends Rectangle {
        public HeloRotorJoint() {
            super(ColorUtil.rgb(252, 252, 28),
                    (int) (TAIL_WIDTH*1.5), (TAIL_WIDTH),
                    (float) (TAIL_WIDTH*2),
                    (float) (-TAIL_HEIGHT + -TAIL_HEIGHT/2 ),
                    1, 1, 0);
        }
        @Override
        protected void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.fillRect(0,0,getDimension().getWidth(),
                    getDimension().getHeight());
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static class HeloRotorBlade extends Rectangle {
        public HeloRotorBlade() {
            super(ColorUtil.GRAY,
                    (TAIL_WIDTH), (TAIL_WIDTH*8),
                    (float) (TAIL_WIDTH*3.5),
                    (float) (-TAIL_HEIGHT + -TAIL_HEIGHT/2 ),
                    1, 1, 0);
        }
        @Override
        protected void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.fillRect(0,0,getDimension().getWidth(),
                    getDimension().getHeight());
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
    private HeloBubble heloBubble;
    private Dimension worldDim;
    private int helipadBoxSize;
    private Dimension riverdim;
    private Boolean canDraw;
    private float drawX, drawY;
    private AddString fuelString, waterString;

    private HeloBlade heloBlade;
    public Helicopter(Dimension worldSize, Point lz, int padBoxSize,
                      Dimension riverdim) {
        heloParts = new ArrayList<>();
        heloBubble = new HeloBubble();
        heloParts.add(heloBubble);
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
        heloParts.add(new HeloTailSide(1));
        heloParts.add(new HeloTailSide(-1));
        heloParts.add(new HeloTailLine(1));
        heloParts.add(new HeloTailLine(-1));
        heloParts.add(new HeloRotorBox());
        heloParts.add(new HeloRotorJoint());
        heloParts.add(new HeloRotorBlade());
        heliLocation = lz;
        translate(heliLocation.getX(),
                heliLocation.getY());
        scale(.3f, .3f);
        setColor(ColorUtil.rgb(252, 252, 28));
        angle = Math.toRadians(90);
        speed = 0;
        fuel = 0;
        water = 0;
        this.riverdim = riverdim;
        centerX =
                (int) (heliLocation.getX() - worldSize.getWidth()/2);
        centerY =
                (int) (heliLocation.getY()/2 - worldSize.getHeight() +
                        padBoxSize*1.5);
        worldDim = worldSize;
        helipadBoxSize = padBoxSize;
    }

    @Override
    protected void localDraw(Graphics g, Point parentOrigin,
                             Point screenOrigin) {
        for(GameObject go : heloParts) {
            go.draw(g, parentOrigin, screenOrigin);
        }
        g.setColor(getColor());
        fuelString = new AddString("F: " + fuel, getColor(),
                new Point(helipadBoxSize,
                        -heliLocation.getY()));
        waterString = new AddString("W: " + water, getColor(),
                new Point(helipadBoxSize,
                        -heliLocation.getY()-helipadBoxSize));
        waterString.scale(3,3);
        waterString.draw(g, parentOrigin, screenOrigin);
        fuelString.scale(3,3);
        fuelString.draw(g, parentOrigin, screenOrigin);
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

    @Override
    public void move(){
        translate(Math.cos(angle) * speed,
                Math.sin(angle) * speed);
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
        updateLocalTransforms(15);
    }

    @Override
    public void steerRight() {
        angle -= Math.toRadians(15);
        updateLocalTransforms(-15);
    }

    public void updateLocalTransforms(double rotationSpeed) {
        this.rotate(rotationSpeed);
    }

    public void setTrue(float x, float y) {
        canDraw = true;
        drawX = x;
        drawY = y;
    }

    public void checkRiverCollision() {
        riverCollision =
                (myTranslation.getTranslateX()-heliLocation.getX() >=
                        -1*(int)((-heliLocation.getX()+(worldDim.getWidth() -
                                worldDim.getWidth()/2)*3) +
                                (riverdim.getWidth()/2+helipadBoxSize*2.3)) &&
                        myTranslation.getTranslateY() <=
                        (-heliLocation.getY()+(worldDim.getHeight() -
                                worldDim.getHeight()/3)*3-helipadBoxSize -
                                (riverdim.getHeight()/2)*3)) &&
                (myTranslation.getTranslateX()-heliLocation.getX() <=
                        (-heliLocation.getX()+ (worldDim.getWidth() -
                                worldDim.getWidth()/2)*3)+(riverdim.getWidth()
                                / 2 +helipadBoxSize*2)
                        && myTranslation.getTranslateY() <=
                        (-heliLocation.getY()+(worldDim.getHeight() -
                                worldDim.getHeight()/3)*3-helipadBoxSize +
                                (riverdim.getHeight()/2)*3));
    }

    public boolean checkFireCollision(Fire fire) {
        return (centerX >= (fire.getFireLocation().getX() - fire.getRadius()) &&
                centerY >= (fire.getFireLocation().getY() - fire.getRadius()))
                && (centerX <= (fire.getFireLocation().getX() +
                fire.getRadius()) && centerY <= (fire.getFireLocation().getY()
                + fire.getRadius()));
    }

    public void drinkWater() {
        if((riverCollision && speed <= 2) && water < 1000) {
            water += 100;
        }
    }

    public void fightFire(Fires fires) {
        for(Fire fire : fires) {
            if(fire.detected()) {
                fire.reduceFire(water);
            }
            fire.setFalse();
        }
    }

    public void dropWater() {
        water = 0;
    }
//
    public void setFuel(int fuelIn) {
        fuel = fuelIn;
    }

    public boolean checkFuel() {
        return fuel <= 0;
    }

    public int getFuel() {
        return fuel;
    }

    public boolean isOnPad() {
        return (centerX >= (helipadCenterLocation.getX() - padSize/4) &&
                centerY >= (helipadCenterLocation.getY() - padSize/4))
                && (centerX <= (helipadCenterLocation.getX() +
                padSize/4) && centerY <= (helipadCenterLocation.getY()
                + padSize/4));
    }

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
