package josercl.chartlib.lib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

/**
 * Created by eseprin on 3/31/14.
 */
public class AreaSerie extends LinearSerie{

    float y0=0;

    @Override
    public void draw(Canvas canvas, Rect chartArea) {
        y0=chartArea.height() - chartArea.top;
        super.draw(canvas, chartArea);

        if(interpolate && points.size()>2) {
            AreaSerie splined=new AreaSerie();
            splined.setColor(paint.getColor());
            splined.setInterpolate(false);
            splined.setStroke(stroke);
            for(Point p:splinedLinear.getPoints()){
                splined.addPoint(p);
            }
            splined.draw(canvas, chartArea);
        }
    }

    @Override
    public void drawPoint(Canvas canvas, double x, double y) {
        if(last!=null){
            float x1=(float) last.getX();
            float y1=(float) last.getY();
            float x2=(float) x;
            float y2=(float) y;

            drawTrapezium(canvas,x1,y1,x2,y2);
        }
        super.drawPoint(canvas,x,y);
    }

    private void drawTrapezium(Canvas canvas,double x1,double y1,double x2,double y2){
        Path figura=new Path();
        figura.moveTo((float) x1,(float) y1);
        figura.lineTo((float) x1,y0);
        figura.lineTo((float) x2,y0);
        figura.lineTo((float) x2,(float) y2);
        figura.lineTo((float) x1,(float) y1);
        figura.close();

        Paint p2=new Paint();
        int color=paint.getColor();
        int alpha=Color.alpha(color);
        color=(color & 0xFFFFFF) | (alpha/3 << 24); //Color al 50%

        p2.setStrokeJoin(Paint.Join.ROUND);
        p2.setColor(color);
        p2.setStrokeWidth(0);
        p2.setStyle(Paint.Style.FILL);
        canvas.drawPath(figura,p2);
    }
}
