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
 * Base Serie class
 * @author Jos&eacute; Rafael Carrero Le&oacute;n &lt;<a href="mailto:josercl@gmail.com">josercl@gmail.com</a>&gt;
 * @version 1.0
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

    /**
     * Adds a point to the Serie
     * @param p The new {@link Point}
     */
    public void addPoint(Point p){
        points.add(p);
        calculateRange(p);
    }

    /**
     * Adds all points of the collection to the Serie
     * @param otherPoints The {@link Point} collection
     */
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

    /**
     * Method used to draw all the points of the serie
     * @param canvas The canvas used to draw the points of the serie
     * @param chartArea Bounds used to draw the points
     *
     */
    public void draw(Canvas canvas,Rect chartArea){
        if(!points.isEmpty()) {
            sort();

            int offsetX=chartArea.left;
            int offsetY=chartArea.top;

            double rangeX = maxX-minX;
            double rangeY = maxY-minY;

            for (Point p : points) {
                double scaledX = (p.getX()-minX) * chartArea.width()/(rangeX);
                double scaledY = (p.getY()-minY) * chartArea.height()/(rangeY);

                drawPoint(canvas, scaledX+offsetX, chartArea.height()-scaledY+offsetY);
            }
        }
    }

    /**
     * Indicates if the series is empty
     * @return If the serie is empty
     */
    public boolean isEmpty(){
        return points.isEmpty();
    }

    //public void finishDrawing(){}

    /**
     * Draws a point of the serie at (x,y) coordinate
     * @param canvas The canvas used to draw the point
     * @param x X coordinate of the point
     * @param y Y coordinate of the point
     */
    public abstract void drawPoint(Canvas canvas,double x,double y);

    /**
     * Sets the stroke used to draw the points of the serie
     * @param stroke The stroke to use
     */
    public void setStroke(float stroke) {
        this.stroke = stroke;
        paint.setStrokeWidth(stroke);
    }

    /**
     * Sets the color used to draw the points of the serie
     * @param color color to use, black by default
     */
    public void setColor(int color) {
        paint.setColor(color);
    }

    /**
     * Sets the color used to draw the points of the serie
     * @param color Color in ARGB or RGB format
     */
    public void setColor(String color){
        setColor(Color.parseColor(color));
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

    /**
     * Gets the minimum X value of the serie
     * @return The minimum value of the X coordinate of the points of the serie
     */
    public double getMinX() {
        return minX;
    }

    /**
     * Gets the maximum X value of the serie
     * @return The maximum value of the X coordinate of the points of the serie
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * Gets the minimum Y value of the serie
     * @return The minimum value of the Y coordinate of the points of the serie
     */
    public double getMinY() {
        return minY;
    }

    /**
     * Gets the maximum Y value of the serie
     * @return The maximum value of the Y coordinate of the points of the serie
     */
    public double getMaxY() {
        return maxY;
    }
}
