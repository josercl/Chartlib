package josercl.chartlib.lib;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * @author Jos&eacute; Rafael Carrero Le&oacute;n &lt;<a href="mailto:josercl@gmail.com">josercl@gmail.com</a>&gt;
 * @version 1.0
 */
public class LinearSerie extends Serie {
    private Point last=null;
    private boolean showPoints=false;
    private ScatterSerie scatter;
    protected PointFigureType pointFigureType=PointFigureType.SQUARE;

    public LinearSerie(){}

    public LinearSerie(boolean showPoints) {
        super();
        setShowPoints(showPoints);
    }

    @Override
    public void addPoint(Point p) {
        super.addPoint(p);
    }

    public void setShowPoints(boolean showPoints) {
        this.showPoints = showPoints;
    }

    public void setPointFigureType(PointFigureType pointFigureType) {
        this.pointFigureType = pointFigureType;
    }

    @Override
    public void draw(Canvas canvas, Rect chartArea) {
        super.draw(canvas, chartArea);
        if(showPoints){
            scatter=new ScatterSerie();
            scatter.addPoints(points);
            scatter.setStroke(this.stroke*2.5f);
            scatter.setColor(this.paint.getColor());
            scatter.setPointFigureType(this.pointFigureType);
            scatter.draw(canvas,chartArea);
        }
        last=null;
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
}
