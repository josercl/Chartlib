package josercl.chartlib.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eseprin on 3/20/14.
 */
public class Chart extends View {

    private boolean showLeftAxis=true,showRightAxis=false,showTopAxis=false,showBottomAxis=true;
    private int leftAxisColor,rightAxisColor,topAxisColor,bottomAxisColor;
    private List<Serie> series;

    public Chart(Context context) {
        this(context,null,0);
    }

    public Chart(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        series=new ArrayList<Serie>();

        /*TypedArray attributes=context.obtainStyledAttributes(attrs,R.styleable.chartlib);

        showLeftAxis=attributes.getBoolean(R.styleable.chartlib_showLeftAxis,true);
        showRightAxis=attributes.getBoolean(R.styleable.chartlib_showRightAxis,false);
        showBottomAxis=attributes.getBoolean(R.styleable.chartlib_showBottomAxis,true);
        showTopAxis=attributes.getBoolean(R.styleable.chartlib_showTopAxis,false);

        leftAxisColor=attributes.getColor(R.styleable.chartlib_leftAxisColor,Color.GRAY);
        rightAxisColor=attributes.getColor(R.styleable.chartlib_rightAxisColor,Color.GRAY);
        topAxisColor=attributes.getColor(R.styleable.chartlib_topAxisColor,Color.GRAY);
        bottomAxisColor=attributes.getColor(R.styleable.chartlib_bottomAxisColor, Color.GRAY);*/
    }

    public Chart addSerie(Serie s){
        series.add(s);
        return this;
    }

    private void drawAxis(Canvas canvas){
        Paint paint=new Paint();
        paint.setStrokeWidth(5);
        if(showLeftAxis){
            paint.setColor(leftAxisColor);
            canvas.drawLine(0,0,0,getHeight(),paint);
        }
        if(showRightAxis){
            paint.setColor(rightAxisColor);
            canvas.drawLine(getWidth(),0,getWidth(),getHeight(),paint);
        }
        if(showTopAxis){
            paint.setColor(topAxisColor);
            canvas.drawLine(0,0,getWidth(),0,paint);
        }
        if(showBottomAxis){
            paint.setColor(bottomAxisColor);
            canvas.drawLine(0,getHeight(),getWidth(),getHeight(),paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAxis(canvas);
        if(series!=null) {
            for (Serie s : series) {
                s.draw(canvas,getWidth(),getHeight());
            }
        }
    }

    public boolean isShowLeftAxis() {
        return showLeftAxis;
    }

    public void setShowLeftAxis(boolean showLeftAxis) {
        this.showLeftAxis = showLeftAxis;
    }

    public boolean isShowRightAxis() {
        return showRightAxis;
    }

    public void setShowRightAxis(boolean showRightAxis) {
        this.showRightAxis = showRightAxis;
    }

    public boolean isShowTopAxis() {
        return showTopAxis;
    }

    public void setShowTopAxis(boolean showTopAxis) {
        this.showTopAxis = showTopAxis;
    }

    public boolean isShowBottomAxis() {
        return showBottomAxis;
    }

    public void setShowBottomAxis(boolean showBottomAxis) {
        this.showBottomAxis = showBottomAxis;
    }
}
