package com.example.dungeonescape.game.collectable;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import java.util.List;
import java.util.ArrayList;


import com.example.dungeonescape.game.GameObject;


public class Blitz extends GameObject implements Collectable {

    private Rect blitzShape;
    private boolean available;

    public Blitz(int x, int y, int size){
        super(x, y);
        blitzShape = new Rect(x, y, x + size, y + size);
        available = true;
    }

    @Override
    public void draw(Canvas canvas){

        Path path = new Path();
        List<Point> points = createShapePoints();
        Point startPoint = points.get(0);
        path.moveTo(startPoint.x, startPoint.y);
        for (int i = 1; i < points.size(); i++ ){
            Point point = points.get(i);
            path.lineTo(point.x, point.y);
        }
        path.lineTo(startPoint.x, startPoint.y);
        path.close();
        canvas.drawPath(path, getPaint());
    }

    private List<Point> createShapePoints(){

        List<Point> shapePoints = new ArrayList<>();

        int size = blitzShape.width();

        int hMargin = size/8;
        int vMargin = size/4;

        int posX = getX();
        int posY = getY();

        Point a = new Point((posX + size/2), posY);
        shapePoints.add(a);
        Point b = new Point((posX + size/2 + hMargin), (int) (posY + vMargin * 1.5));
        shapePoints.add(b);
        Point c = new Point((posX + size), (int) (posY + vMargin * 1.5));
        shapePoints.add(c);
        Point d = new Point((posX + size/2 + 2*hMargin), (posY + size/2 + vMargin/2));
        shapePoints.add(d);
        Point e = new Point((posX + size/2 + 3*hMargin), (posY + size));
        shapePoints.add(e);
        Point f = new Point((posX + size/2), (posY + size - vMargin));
        shapePoints.add(f);
        Point g = new Point((posX + size/2 - 3*hMargin), (posY + size));
        shapePoints.add(g);
        Point h = new Point((posX + size/2 - 2*hMargin), (posY + size/2 + vMargin/2));
        shapePoints.add(h);
        Point i = new Point( posX, (int) (posY + vMargin * 1.5));
        shapePoints.add(i);
        Point j = new Point((posX + size/2 - hMargin), (int) (posY + vMargin * 1.5));
        shapePoints.add(j);

        return shapePoints;

    }
    public Boolean getAvailableStatus(){
        return available;
    }

    public void gotCollected(){
        available = false;
    }
}
