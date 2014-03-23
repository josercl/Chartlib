package josercl.chartlib.lib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by eseprin on 3/20/14.
 */
public class LinearSerie extends Serie {
    Point last=null;
    boolean showPoints=false;

    public boolean isShowPoints() {
        return showPoints;
    }

    public void setShowPoints(boolean showPoints) {
        this.showPoints = showPoints;
    }

    @Override
    public void drawPoint(Canvas canvas, double x, double y) {
        if(last!=null){
            float x1=(float) last.getX();
            float y1=(float) last.getY();
            float x2=(float) x;
            float y2=(float) y;
            canvas.drawLine(x1,y1,x2,y2,paint);
        }

        last=new Point(x,y);
    }

    @Override
    public void finishDrawing() {
        last=null;
    }
}
