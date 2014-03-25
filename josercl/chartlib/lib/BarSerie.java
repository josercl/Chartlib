package josercl.chartlib.lib;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * @author Jos&eacute; Rafael Carrero Le&oacute;n &lt;<a href="mailto:josercl@gmail.com">josercl@gmail.com</a>&gt;
 * @version 1.0
 */
public class BarSerie extends Serie {

    private float barGap=20;
    private float barWidth;

    @Override
    public void draw(Canvas canvas, Rect chartArea) {
        barWidth=chartArea.width()-barGap*(points.size()+1);
        super.draw(canvas, chartArea);
    }

    @Override
    public void drawPoint(Canvas canvas, double x, double y) {

    }

    public void setBarGap(float barGap) {
        this.barGap = barGap;
    }
}
