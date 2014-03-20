package josercl.chartlib.lib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

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
    protected PointFigureType pointFigureType;

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
    }

    public void addPoints(Collection<? extends Point> otherPoints){
        points.addAll(otherPoints);
    }

    public void draw(Canvas canvas){
        sort();
        for(Point p:points){
            drawPoint(canvas,p.getX(),p.getY());
        }
        finishDrawing();
    }

    public void finishDrawing(){}

    public abstract void drawPoint(Canvas canvas,double x,double y);

    public void setStroke(float stroke){
        this.stroke=stroke;
        paint.setStrokeWidth(stroke);
    }

    public void setColor(int color) {
        paint.setColor(color);
    }
    public void setColor(String c){
        setColor(Color.parseColor(c));
    }

    public PointFigureType getPointFigureType() {
        return pointFigureType;
    }

    public void setPointFigureType(PointFigureType pointFigureType) {
        this.pointFigureType = pointFigureType;
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
}
