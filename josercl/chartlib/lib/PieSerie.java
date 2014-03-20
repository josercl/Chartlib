package josercl.chartlib.lib;

import android.graphics.Canvas;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by eseprin on 3/20/14.
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

    @Override
    public void draw(Canvas canvas) {
        sort();
        startAngle=0;
        getBounds(canvas);
        for(int i=0;i<points.size();i++){
            Integer []gradient=colors.get(i);
            if(gradient[1]==null) {
                gradient[1]=gradient[0];
            }

            RadialGradient shader=new RadialGradient(bounds.centerX(),bounds.centerY(),diameter/2,gradient[0],gradient[1], Shader.TileMode.CLAMP);
            drawPoint(canvas, points.get(i).getY(), shader);
        }
        finishDrawing();
    }

    public void drawPoint(Canvas canvas, double y, Shader shader) {
        float angle=((float) y)*360/((float) total);
        paint.setShader(shader);
        paint.setDither(true);
        canvas.drawArc(bounds,startAngle,angle,true,paint);
        startAngle+=angle;
    }

    private void getBounds(Canvas canvas){
        int height=canvas.getHeight();
        int width=canvas.getWidth();
        diameter=(height>=width)?width:height;
        float x1=(width-diameter)/2;
        float y1=(height-diameter)/2;
        float x2=x1+diameter;
        float y2=y1+diameter;
        bounds=new RectF(x1,y1,x2,y2);
    }

    @Override
    public void drawPoint(Canvas canvas, double x, double y) {}
}
