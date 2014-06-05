package josercl.chartlib.lib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Jos&eacute; Rafael Carrero Le&oacute;n &lt;<a href="mailto:josercl@gmail.com">josercl@gmail.com</a>&gt;
 * @version 1.0
 */
public class PieSerie extends Serie {

    private double total=0;
    private List<Integer[]> colors;
    private float startAngle;
    private int diameter;

    private RectF bounds;

    public PieSerie(){
        super();
        colors=new ArrayList<Integer[]>();
    }

    public void addPoint(Point p,Integer color) {
        this.addPoint(p,color,null);
    }

    public void addPoint(Point p,Integer color1,Integer color2) {
        super.addPoint(p);
        colors.add(new Integer[]{color1,color2});
        total+=p.getY();
    }

    @Override
    public void addPoints(Collection<? extends Point> otherPoints) {
        super.addPoints(otherPoints);
        for(Point p:otherPoints){
            total+=p.getY();
        }
    }

    public void draw(Canvas canvas,Rect area) {
        sort();
        startAngle=0;

        if(diameter==0) {
            diameter = (area.height() >= area.width()) ? area.width() : area.height();
        }

        float x1=(area.width()-diameter)/2;
        float y1=(area.height()-diameter)/2;
        float x2=x1+diameter;
        float y2=y1+diameter;
        bounds=new RectF(x1+area.left,y1+area.top,x2+area.left,y2+area.top);

        for(int i=0;i<points.size();i++){
            Integer []gradient=colors.get(i);
            if(gradient[1]==null) {
                gradient[1]=gradient[0];
            }

            RadialGradient shader=new RadialGradient(bounds.centerX(),bounds.centerY(),diameter/2,gradient[0],gradient[1], Shader.TileMode.CLAMP);
            drawPoint(canvas, points.get(i).getY(), shader);
        }

        Paint paint2=new Paint();
        paint2.setStrokeWidth(3);
        paint2.setColor(Color.WHITE);

        startAngle=0;
        for(int i=0;i<points.size();i++){
            startAngle+=((float) points.get(i).getY())*360/((float) total);
            double xl=bounds.centerX()+diameter*Math.cos(startAngle * Math.PI/180)/2;
            double yl=bounds.centerY()+diameter*Math.sin(startAngle * Math.PI/180)/2;
            canvas.drawLine(bounds.centerX(),bounds.centerY(),(float) xl,(float) yl,paint2);
        }
    }

    public void drawPoint(Canvas canvas, double y, Shader shader) {
        float angle=((float) y)*360/((float) total);
        paint.setShader(shader);
        paint.setDither(true);

        canvas.drawArc(bounds, startAngle, angle, true, paint);
        startAngle+=angle;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    @Override
    public void drawPoint(Canvas canvas, double x, double y) {}
}
