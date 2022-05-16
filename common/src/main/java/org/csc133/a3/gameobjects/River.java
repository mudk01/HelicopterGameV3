package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.gameobjects.parts.Arc;
import org.csc133.a3.gameobjects.parts.Rectangle;


public class River extends Fixed {
    private Dimension dimension;
    Arc arc, arc2;
    Rectangle rectangle;
    public River(Dimension worldSize) {
        dimension = new Dimension(worldSize.getWidth(),
                worldSize.getHeight()/9);
        rectangle = new Rectangle(ColorUtil.BLUE,
                        dimension.getWidth(),
                        dimension.getHeight(),
                        0, (float) (0),
                        1, 1, 0
        );
        setDimension(rectangle.getDimension());
        translate(dimension.getWidth()/2,
                worldSize.getHeight()- worldSize.getHeight()/3);
    }

    @Override
    protected void localDraw(Graphics g, Point parentOrigin,
                             Point screenOrigin) {
        rectangle.draw(g, parentOrigin, screenOrigin);
    }

    public Transform getTranslation() {
        return rectangle.myTranslation;
    }

    public Dimension getDimension() {
        return dimension;

    }
}
