package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.gameobjects.parts.Rectangle;


public class River extends Fixed {
    private Dimension dimension;

    Rectangle rectangle;
    public River(Dimension worldSize) {
        dimension = new Dimension(worldSize.getWidth(),
                worldSize.getHeight()/9);
//        location = new Point(0,
//                worldSize.getHeight()/3-this.dimension.getHeight());
//        setColor(ColorUtil.rgb(0,52,254));
//        setDimension(new Dimension(RECTANGLE_WIDTH, RECTANGLE_HEIGHT));
//        translate(tx, ty);
//        scale(sx, sy);
//        rotate(degrees);
        rectangle = new Rectangle(ColorUtil.BLUE,
                        dimension.getWidth(),
                        dimension.getHeight(),
                        0, (float) (0),
                        1, 1, 0
        );
        translate(dimension.getWidth()/2,
                worldSize.getHeight()- worldSize.getHeight()/3);
    }

    @Override
    protected void localDraw(Graphics g, Point parentOrigin,
                             Point screenOrigin) {
        rectangle.draw(g, parentOrigin, screenOrigin);
    }
    }

//    @Override
//    public Point getLocation() {
//        return super.getLocation();
//    }
//
//    @Override
//    public Dimension getDimension() {
//        return super.getDimension();
//    }
//
////    @Override
//    public void draw(Graphics g, Point containerOrigin) {
//        g.setColor(color);
//        g.drawRect(containerOrigin.getX() + location.getX(),
//                containerOrigin.getY() + location.getY(),
//                dimension.getWidth(),
//                dimension.getHeight(),5);
//    }
//
//
//}
