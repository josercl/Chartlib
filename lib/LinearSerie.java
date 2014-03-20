package josercl.chartlib.lib;

import android.graphics.Canvas;

/**
 * Created by eseprin on 3/20/14.
 */
public class LinearSerie extends Serie {
    Point last=null;

    @Override
    public void drawPoint(Canvas canvas, double x, double y) {
        if(last!=null){
            float x1=(float) last.getX();
            float y1=(float) last.getY();
            float x2=(float) x;
            float y2=(float) y;
            canvas.drawLine(x1,y1,x2,y2,paint);
        }else{
            last=new Point(x,y);
        }
    }

    @Override
    public void finishDrawing() {
        last=null;
    }
}
