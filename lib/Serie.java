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

    private String name;
    private List<Point> points;
    private Color color;
    protected Paint paint=new Paint();

    public Serie(){}

    private void sort(){
        Collections.sort(points);
    }

    public void addPoint(Point p){
        if(points==null){
            points=new ArrayList<Point>();
        }
        points.add(p);
    }

    public void addPoints(Collection<? extends Point> otherPoints){
        if(points==null){
            points=new ArrayList<Point>();
        }
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
