package josercl.chartlib.lib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by eseprin on 3/20/14.
 */
public class PieSerie extends Serie {

    private double total=0;
    private List<Integer> colors;
    private float startAngle;

    public PieSerie(){
        super();
        colors=new ArrayList<Integer>();
    }

    public void addPoint(Point p,int color) {
        super.addPoint(p);
        colors.add(color);
        total+=p.getY();
    }

    @Override
    public void addPoints(Collection<? extends Point> otherPoints) {
        super.addPoints(otherPoints);
        for(Point p:otherPoints){
            total+=p.getY();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        sort();
        startAngle=0;
        for(int i=0;i<points.size();i++){
            Integer color=colors.get(i%colors.size());
            if(color==null){color= Color.BLACK;}
            drawPoint(canvas, points.get(i).getY(), color);
        }
        finishDrawing();
    }

    public void drawPoint(Canvas canvas, double y, int color) {
        int height=canvas.getHeight();
        int width=canvas.getWidth();
        int diameter=(height>=width)?width:height;
        float x1=(width-diameter)/2;
        float y1=(height-diameter)/2;
        float x2=x1+diameter;
        float y2=y1+diameter;
        RectF bounds=new RectF(x1,y1,x2,y2);
        float angle=((float) y)*360/((float) total);


        paint.setColor(color);


        canvas.drawArc(bounds,startAngle,angle,true,paint);
        startAngle+=angle;
    }

    @Override
    public void drawPoint(Canvas canvas, double x, double y) {}
}
