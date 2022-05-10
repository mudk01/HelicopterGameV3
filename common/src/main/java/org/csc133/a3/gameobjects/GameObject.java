//package org.csc133.a3.gameobjects;
//
//import com.codename1.ui.Graphics;
//import com.codename1.ui.Transform;
//import com.codename1.ui.geom.Dimension;
//import com.codename1.ui.geom.Point;
//
//public abstract class GameObject {
//    int color;
//    Dimension dimension;
//    Point location;
//    protected Transform myTranslation, myRotation, myScale;
//
//    public GameObject(){
//        myTranslation = Transform.makeIdentity();
//        myRotation = Transform.makeIdentity();
//        myScale = Transform.makeIdentity();
//    }
//
//
////    public GameObject(Point location, Dimension dimension, int color) {
////        this.location = location;
////        this.dimension = dimension;
////        this.color = color;
////    }
//
//    protected void localTransforms(Transform gXform) {
//        gXform.translate(myTranslation.getTranslateX(),
//                myTranslation.getTranslateY());
//        gXform.concatenate(myRotation);
//        gXform.scale(myScale.getScaleX(), myScale.getScaleY());
//    }
//
//    protected void containerTranslate(Graphics g, Point parentOrigin) {
//        Transform gxForm = Transform.makeIdentity();
//        g.getTransform(gxForm);
//        gxForm.translate(parentOrigin.getX(), parentOrigin.getY());
//        g.setTransform(gxForm);
//    }
//
//    protected void cn1ForwardPrimitiveTranslate(Graphics g,
//                                                Dimension pDimension) {
//        Transform gXform = Transform.makeIdentity();
//        g.getTransform(gXform);
//        gXform.translate(-pDimension.getWidth()/2, -pDimension.getHeight()/2);
//        g.setTransform(gXform);
//    }
//
//    protected void cn1ReversePrimitiveTranslate(Graphics g,
//                                                Dimension pDimension) {
//        Transform gxForm = Transform.makeIdentity();
//        g.getTransform(gxForm);
//        gxForm.translate(pDimension.getWidth()/2, pDimension.getHeight()/2);
//        g.setTransform(gxForm);
//    }
//    private Transform gOrigXform;
//
//    protected Transform preLTTransform(Graphics g, Point screenOrigin) {
//        Transform gXform = Transform.makeIdentity();
//
//        g.getTransform(gXform);
//        gOrigXform = gXform.copy();
//
//        gXform.translate(screenOrigin.getX(), screenOrigin.getY());
//        return gXform;
//    }
//
//    protected void postLTTransform(Graphics g, Point screenOrigin, Transform gXform) {
//        gXform.translate(-screenOrigin.getX(), -screenOrigin.getY());
//        g.setTransform(gXform);
//    }
//
//    protected void restoreOriginalTransforms(Graphics g) {
//        g.setTransform(gOrigXform);
//    }
//
//    //    Below are methods to perform transformations
//    public void rotate(double degrees){
//        myRotation.rotate((float) Math.toRadians(degrees), 0, 0);
//    }
//
//    public void scale(double sx, double sy){
//        myScale.scale((float) sx, (float) sy);
//    }
//
//    public void translate(double tx, double ty){
//        myTranslation.translate((float) tx, (float) ty);
//    }
//
//    public Point getLocation() {
//        return location;
//    }
//
//    public Dimension getDimension() {
//        return dimension;
//    }
//
//    public void setLocation(Point location) {}
//
//    public void setColor(int color) {
//        this.color = color;
//    }
//
//    public int getColor(){
//        return this.color;
//    }
//
//    public void setDimension(Dimension dimension) {
//        this.dimension = new Dimension(dimension.getWidth(),
//                dimension.getHeight());
//    }
//
//    public void draw(Graphics g, Point parentOrigin, Point screenOrigin) {
//        Transform gXform = Transform.makeIdentity();
//        g.getTransform(gXform);
//        Transform gOriginXForm = gXform.copy();
//
//        gXform.translate(screenOrigin.getX(), screenOrigin.getY());
//        gXform.translate(myTranslation.getTranslateX(),
//                myTranslation.getTranslateY());
//        gXform.concatenate(myRotation);
//        gXform.scale(myScale.getScaleX(), myScale.getScaleY());
//        gXform.translate(-screenOrigin.getX(), -screenOrigin.getY());
//        g.setTransform(gXform);
//
//        localDraw(g, parentOrigin, screenOrigin);
//        g.setTransform(gOriginXForm);
//    }
//
//    protected abstract void localDraw(Graphics g, Point parentOrigin,
//                                  Point screenOrigin);
//}
//
package org.csc133.a3.gameobjects;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

public abstract class GameObject {

    protected Transform myTranslation, myRotation, myScale;
    protected int myColor;
    protected Dimension myDimension;

    public GameObject() {
        myTranslation = Transform.makeIdentity();
        myRotation = Transform.makeIdentity();
        myScale = Transform.makeIdentity();
    }

    public void setColor(int iColor) {
        myColor = iColor;
    }

    public int getColor() {
        return myColor;
    }

    public void setDimension(Dimension iDimension) {
        myDimension = iDimension;
    }

    public Dimension getDimension() {
        return myDimension;
    }

    public void rotate(double degrees) {
        myRotation.rotate((float)Math.toRadians(degrees), 0, 0);
    }

    public void scale(double sx, double sy) {
        myScale.scale((float)sx, (float)sy);
    }

    public void translate(double tx, double ty) {
        myTranslation.translate((float)tx, (float)ty);
    }

    private Transform gOrigXform;

    protected Transform preLTTransform(Graphics g, Point screenOrigin) {
        Transform gXform = Transform.makeIdentity();

        g.getTransform(gXform);
        gOrigXform = gXform.copy();

        gXform.translate(screenOrigin.getX(), screenOrigin.getY());
        return gXform;
    }

    protected void postLTTransform(Graphics g, Point screenOrigin, Transform gXform) {
        gXform.translate(-screenOrigin.getX(), -screenOrigin.getY());
        g.setTransform(gXform);
    }

    protected void restoreOriginalTransforms(Graphics g) {
        g.setTransform(gOrigXform);
    }

    protected void localTransforms(Transform gXform) {
        gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
        gXform.concatenate(myRotation);
        gXform.scale(myScale.getScaleX(), myScale.getScaleY());
    }

    public void updateLocalTransforms() {
    }

    protected void containerTranslate(Graphics g, Point parentOrigin) {
        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        gXform.translate(parentOrigin.getX(), parentOrigin.getY());
        g.setTransform(gXform);
    }

    protected void cn1ForwardPrimitiveTranslate(Graphics g, Dimension pDimension) {
        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        gXform.translate(-pDimension.getWidth()/2, -pDimension.getHeight()/2);
        g.setTransform(gXform);
    }

    protected void cn1ReversePrimitiveTranslate(Graphics g, Dimension pDimension) {
        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        gXform.translate(pDimension.getWidth()/2, pDimension.getHeight()/2);
        g.setTransform(gXform);
    }

    protected abstract void localDraw(Graphics g, Point parentOrigin, Point screenOrigin);

    public void draw(Graphics g, Point parentOrigin, Point screenOrigin) {
        Transform gXform = preLTTransform(g, screenOrigin);
        localTransforms(gXform);
        postLTTransform(g, screenOrigin, gXform);
        localDraw(g, parentOrigin, screenOrigin);
        restoreOriginalTransforms(g);
    }


}
