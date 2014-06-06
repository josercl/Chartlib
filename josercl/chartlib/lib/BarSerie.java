package josercl.chartlib.lib;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * @author Jos&eacute; Rafael Carrero Le&oacute;n &lt;<a href="mailto:josercl@gmail.com">josercl@gmail.com</a>&gt;
 * @version 1.0
 */
public class BarSerie extends Serie {

    protected float barGap=.5f;
    protected float barWidth,scaledGap;

    protected double y0;

    @Override
    public void addPoint(Point p) {
        super.addPoint(new Point(points.size(), p.getY()));
    }

    @Override
    public void draw(Canvas canvas, Rect chartArea) {
        int n=points.size();
        scaledGap=barGap * (float) (chartArea.width()/(maxX-minX));
        barWidth=(chartArea.width()-scaledGap*(n-1))/n;

        y0 = chartArea.height()+chartArea.top;

        for (int i=0;i<points.size();i++) {
            Point p=points.get(i);
            double scaledY = (p.getY()-minY+barGap) * (chartArea.height())/(maxY-minY+2*barGap);
            double scaledX = i*(barWidth+scaledGap);
            drawPoint(canvas, scaledX + chartArea.left, chartArea.height() - scaledY + chartArea.top);
        }
    }

    @Override
    public void drawPoint(Canvas canvas, double x, double y) {
        float left=(float) x;
        float top=(float) y;
        float right=left+barWidth;
        canvas.drawRect(left,top,right,(float) y0,paint);
    }

    @Override
    public double getMinX() {
        return super.getMinX()-barGap;
    }

    @Override
    public double getMaxX() {
        return super.getMaxX()+barGap;
    }

    @Override
    public double getMinY() {
        return super.getMinY()-barGap;
    }

    @Override
    public double getMaxY() {
        return super.getMaxY()+barGap;
    }

    public void setBarGap(float barGap) {
        this.barGap = barGap;
    }
}
