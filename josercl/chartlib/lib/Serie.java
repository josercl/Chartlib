package josercl.chartlib.lib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by eseprin on 3/20/14.
 */
public abstract class Serie {

    protected String name;
    protected List<Point> points;
    protected float stroke=5;
    protected Paint paint=new Paint();

    double minX=Double.MAX_VALUE,maxX=Double.MIN_VALUE,minY=Double.MAX_VALUE,maxY=Double.MIN_VALUE;

    public Serie(){
        setColor(Color.BLACK);
        setStroke(stroke);
        points=new ArrayList<Point>();
    }

    protected void sort(){
        Collections.sort(points);
    }

    public void addPoint(Point p){
        points.add(p);
        calculateRange(p);
    }

    public void addPoints(Collection<? extends Point> otherPoints){
        points.addAll(otherPoints);
        for(Point p:otherPoints){
            calculateRange(p);
        }
    }

    private void calculateRange(Point p){
        if(p.getX()<minX){ minX=p.getX();}
        if(p.getX()>maxX){ maxX=p.getX();}
        if(p.getY()<minY){ minY=p.getY();}
        if(p.getY()>maxY){ maxY=p.getY();}
    }

    public void draw(Canvas canvas,Rect chartArea){
        if(!points.isEmpty()) {
            sort();

            int offsetX=chartArea.left;
            int offsetY=chartArea.top;

            double rangeX = maxX-minX;
            double rangeY = maxY-minY;

            for (Point p : points) {

                double scaledX = (p.getX()-minX) * chartArea.width()/rangeX;
                double scaledY = (p.getY()-minY) * chartArea.height()/rangeY;

                drawPoint(canvas, scaledX+offsetX, chartArea.height()-scaledY+offsetY);
            }
            finishDrawing();
        }
    }

    public boolean isEmpty(){
        return points.isEmpty();
    }

    public void finishDrawing(){}

    public abstract void drawPoint(Canvas canvas,double x,double y);

    public void setStroke(float stroke) {
        this.stroke = stroke;
        paint.setStrokeWidth(stroke);
    }

    public void setColor(int color) {
        paint.setColor(color);
    }
    public void setColor(String c){
        setColor(Color.parseColor(c));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
        return maxY;
    }
}
