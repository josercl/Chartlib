package josercl.chartlib.lib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jos&eacute; Rafael Carrero Le&oacute;n &lt;<a href="mailto:josercl@gmail.com">josercl@gmail.com</a>&gt;
 * @version 1.0
 */
public class WaterfallSerie extends BarSerie {

    private List<Boolean> areSum;
    private List<Integer> colors;
    private double yMax;
    private double suma;
    private Paint parentPaint;
    private boolean drawGradient=false;

    public WaterfallSerie(){
        super();
        areSum=new ArrayList<Boolean>();
        colors=new ArrayList<Integer>();
        parentPaint=paint;
    }

    @Override
    public void addPoint(Point p) {
        super.addPoint(p);
        areSum.add(false);
    }

    public void addPoint(double value){
        addPoint(value, false, null);
    }

    public void addPoint(double value,boolean isSum){
        addPoint(value,isSum,null);
    }

    public void addPoint(double value,boolean isSum,Integer color){
        if(!isSum){
            suma-=value;
            super.addPoint(new Point(0, value));
        }else{
            if(points.isEmpty()){
                suma=value;
            }
            super.addPoint(new Point(0, suma));
        }
        if(color!=null) {
            colors.add(color);
        }else{
            colors.add(parentPaint.getColor());
        }
        areSum.add(isSum);
    }

    public boolean isDrawGradient() {
        return drawGradient;
    }

    public void setDrawGradient(boolean drawGradient) {
        this.drawGradient = drawGradient;
    }

    @Override
    public void draw(Canvas canvas, Rect chartArea) {
        int n=points.size();
        scaledGap=barGap * (float) (chartArea.width()/(maxX-minX));
        barWidth=(chartArea.width()-scaledGap*(n-1))/n;

        Point p;
        double scaledY;
        double yMax=chartArea.top;

        for (int i=0;i<points.size();i++) {

            y0 = chartArea.top + chartArea.height();

            p=points.get(i);
            scaledY = (p.getY()-minY+barGap) * (chartArea.height())/(maxY-minY+2*barGap);
            double scaledX = i*(barWidth+scaledGap);

            if(!areSum.get(i)){
                y0 = yMax + scaledY;
                drawPoint(canvas, scaledX + chartArea.left, yMax, colors.get(i));
                yMax+=scaledY;
            }else {
                drawPoint(canvas, scaledX + chartArea.left, chartArea.top + chartArea.height() - scaledY, colors.get(i));
            }
        }
    }

    public void drawPoint(Canvas canvas, double x, double y, int color) {
        float left=(float) x;
        float top=(float) y;
        float right=left+barWidth;

        Paint p=new Paint();
        if(drawGradient) {
            p.setDither(true);
            int[] cs = new int[3];
            int r = (int) (Color.red(color) * .72);
            int g = (int) (Color.green(color) * .72);
            int b = (int) (Color.blue(color) * .72);
            cs[0] = Color.rgb(r, g, b);
            cs[1] = color;
            cs[2] = cs[0];
            LinearGradient lg = new LinearGradient(left, 0, right, 0, cs, new float[]{0, .5f, 1}, Shader.TileMode.CLAMP);
            p.setShader(lg);
        }else{
            p.setColor(color);
        }

        canvas.drawRect(left, top, right, (float) y0, p);
    }
}
