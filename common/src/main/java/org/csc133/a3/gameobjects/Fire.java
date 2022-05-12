package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.gameobjects.parts.Arc;

import java.util.Random;

public class Fire extends Fixed {
    private Point centerLocation;
    private int size, radius, growth;
    private Font fireSizeFont;
    private boolean isDetected, canGrow;
    private FireState currentState;
    private Dimension worldDimension;
//    private Arc fire;

    public Fire(Dimension worldSize, int fireSize) {
        setDimension(new Dimension(fireSize,
                fireSize));
        setColor(ColorUtil.MAGENTA);
        worldDimension = worldSize;
        size = fireSize;
        radius = size/2;
        fireSizeFont = Font.createSystemFont(Font.FACE_SYSTEM,
                Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
        isDetected = false;
//        fire = new Arc(ColorUtil.MAGENTA, fireSize, fireSize, 0,0,1,1,0,0,360);
        currentState = UnStartedFire.instance();
        canGrow = true;
    }

    @Override
    protected void localDraw(Graphics g, Point parentOrigin,
                             Point screenOrigin) {
        g.setColor(getColor());
        containerTranslate(g, parentOrigin);
        g.fillArc(centerLocation.getX() - radius,
                centerLocation.getY() - radius,
                size, size, 0, 360);
        AddString sizeString = new AddString("" + size, getColor(),
                new Point(centerLocation.getX() + radius,
                        centerLocation.getY() + radius));
        sizeString.draw(g, parentOrigin, screenOrigin);
//        g.drawString(""  + size, centerLocation.getX() + radius,
//                centerLocation.getY() + radius);

    } 

    public void setFire(Building b) {
        b.setFireInBuilding(this);
    }

    public void setTrue() {
        isDetected = true;
    }

    public void setFalse() {
        isDetected = false;
    }

    public boolean detected() {
        return isDetected;
    }

    public void growFire() {
        if(canGrow == true) {
            growth = new Random().nextInt(2);
            size += growth;
            radius = size / 2;
            centerLocation.setX(centerLocation.getX() - (int) (growth / 2));
            centerLocation.setY(centerLocation.getY() - (int) (growth / 2));

        }
    }

    public Point getFireLocation() {
        return centerLocation;
    }

    public int getRadius() {
        return radius;
    }

    public int getSize() {
        return size;
    }

    void reduceFire(int water) {
        size -= water / (new Random().nextInt(7) + 8);
    }

//    @Override
    public void setLocation(Point buildingLocation) {
        centerLocation = new Point((buildingLocation.getX() + radius),
                (buildingLocation.getY() + radius));
//        this.myTranslation.translate(0,  0);
    }

//    @Override
//    public void draw(Graphics g, Point containerOrigin) {
//        g.setColor(color);
//        g.setFont(fireSizeFont);
//        if(size > 0) {
//            g.fillArc(centerLocation.getX() - radius,
//                    containerOrigin.getY() + centerLocation.getY() - radius,
//                    size,
//                    size, 0, 360);
//            g.drawString("" + size, centerLocation.getX() + radius,
//                    containerOrigin.getY() + centerLocation.getY() + radius);
//        }
//    }

    public void setCurrentState(FireState state) {
        this.currentState = state;
    }

    public FireState getCurrentState() {
        return currentState;
    }

    public void update() {
        this.currentState.updateState(this);
    }

    public void start() {
        update();
    }

    public void extinguishFire() {
        update();
    }

    public int getArea() {
        return (int) (Math.PI * (radius*radius));
    }

    public void setCanGrow(Boolean canGrow) {
        this.canGrow = canGrow;
    }

    public void updateLocalTransforms(){

    }

}
