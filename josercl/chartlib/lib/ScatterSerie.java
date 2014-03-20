package josercl.chartlib.lib;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * Created by eseprin on 3/20/14.
 */
public class ScatterSerie extends Serie {

    public ScatterSerie(){
        super();
        setPointFigureType(PointFigureType.NONE);
    }

    @Override
    public void drawPoint(Canvas canvas, double x, double y) {
        float x1=((float) x)-stroke/2;
        float x2=((float) x)+stroke/2;
        float y1=((float) y)-stroke/2;
        float y2=((float) y)+stroke/2;
        switch(pointFigureType){
            case CIRCLE:
                RectF bounds=new RectF(x1,y1,x2,y2);
                canvas.drawArc(bounds,0,360,true,paint);
                break;
            case SQUARE:
                bounds=new RectF(x1,y1,x2,y2);
                canvas.drawRect(bounds,paint);
                break;
            case DIAMOND:
                Path path=new Path();
                path.moveTo((float) x,y1);
                path.lineTo(x2, (float) y);
                path.lineTo((float) x,y2);
                path.lineTo(x1,(float) y);
                paint.setStrokeWidth(3);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawPath(path, paint);
                break;
            case CROSS:
                paint.setStrokeWidth(3);

                canvas.drawLine(x1,(float) y,x2,(float) y,paint);
                canvas.drawLine((float) x,y1,(float) x,y2,paint);
                break;
            case X:
                paint.setStrokeWidth(3);

                canvas.drawLine(x1,y1,x2,y2,paint);
                canvas.drawLine(x1,y2,x2,y1,paint);
                break;
            case TRIANGLE:
                path=new Path();
                path.moveTo((float) x,y1);
                path.lineTo(x2,y2);
                path.lineTo(x1,y2);
                path.lineTo((float) x,y1);

                paint.setStrokeWidth(3);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawPath(path, paint);
            default:
                canvas.drawPoint((float) x, (float) y, paint);break;
        }
    }
}