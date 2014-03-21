package josercl.chartlib.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import josercl.charttest.app.R;

/**
 * Created by eseprin on 3/20/14.
 */
public class Chart extends RelativeLayout {

    private LinearLayout leftAxis,rightAxis,topAxis,bottomAxis;
    private boolean showLeftAxis=true,showRightAxis=false,showTopAxis=false,showBottomAxis=true;
    private int leftAxisColor,rightAxisColor,topAxisColor,bottomAxisColor;
    private List<Serie> series;
    private Rect chartArea;

    int leftAxisWidth=20;
    int rightAxisWidth=20;
    int bottomAxisHeight=20;
    int topAxisHeight=20;

    double minX=Double.MAX_VALUE,maxX=Double.MIN_VALUE,minY=Double.MAX_VALUE,maxY=Double.MIN_VALUE;

    public Chart(Context context) {
        this(context,null,0);
    }

    public Chart(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        series=new ArrayList<Serie>();

        setWillNotDraw(false);

        TypedArray attributes=context.obtainStyledAttributes(attrs,R.styleable.chartlib);

        showLeftAxis=attributes.getBoolean(R.styleable.chartlib_showLeftAxis,true);
        showRightAxis=attributes.getBoolean(R.styleable.chartlib_showRightAxis,false);
        showBottomAxis=attributes.getBoolean(R.styleable.chartlib_showBottomAxis,true);
        showTopAxis=attributes.getBoolean(R.styleable.chartlib_showTopAxis,false);

        leftAxisColor=attributes.getColor(R.styleable.chartlib_leftAxisColor, Color.GRAY);
        rightAxisColor=attributes.getColor(R.styleable.chartlib_rightAxisColor,Color.GRAY);
        topAxisColor=attributes.getColor(R.styleable.chartlib_topAxisColor,Color.GRAY);
        bottomAxisColor=attributes.getColor(R.styleable.chartlib_bottomAxisColor, Color.GRAY);

        chartArea=new Rect(0,0,getWidth(),getHeight());

        leftAxis=new LinearLayout(context);
        LayoutParams leftparams=new LayoutParams(leftAxisWidth, LayoutParams.MATCH_PARENT);
        leftAxis.setOrientation(LinearLayout.VERTICAL);
        leftAxis.setBackgroundColor(Color.parseColor("#a0ff0000"));
        leftAxis.setLayoutParams(leftparams);

        rightAxis=new LinearLayout(context);
        LayoutParams rightparams=new LayoutParams(rightAxisWidth, LayoutParams.MATCH_PARENT);
        rightparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightAxis.setOrientation(LinearLayout.VERTICAL);
        rightAxis.setBackgroundColor(Color.parseColor("#a0ff0000"));
        rightAxis.setLayoutParams(rightparams);

        bottomAxis=new LinearLayout(context);
        LayoutParams bottomparams=new LayoutParams(LayoutParams.MATCH_PARENT, bottomAxisHeight);
        bottomparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bottomAxis.setOrientation(LinearLayout.HORIZONTAL);
        bottomAxis.setBackgroundColor(Color.parseColor("#a0ff0000"));
        bottomAxis.setLayoutParams(bottomparams);

        topAxis=new LinearLayout(context);
        LayoutParams topparams=new LayoutParams(LayoutParams.MATCH_PARENT, topAxisHeight);
        topAxis.setOrientation(LinearLayout.HORIZONTAL);
        topAxis.setBackgroundColor(Color.parseColor("#a0ff0000"));
        topAxis.setLayoutParams(topparams);

        addView(leftAxis);
        addView(rightAxis);
        addView(bottomAxis);
        addView(topAxis);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        LayoutParams leftparams= (LayoutParams) leftAxis.getLayoutParams();
        leftparams.height=getHeight();
        leftAxis.setLayoutParams(leftparams);

        LayoutParams rightparams= (LayoutParams) rightAxis.getLayoutParams();
        rightparams.height=getHeight();
        rightAxis.setLayoutParams(rightparams);

        LayoutParams bottomparams= (LayoutParams) bottomAxis.getLayoutParams();
        bottomparams.width=getWidth();
        bottomAxis.setLayoutParams(bottomparams);

        LayoutParams topparams= (LayoutParams) topAxis.getLayoutParams();
        topparams.width=getWidth();
        topAxis.setLayoutParams(topparams);

        chartArea.set(leftAxisWidth, topAxisHeight, getWidth()-rightAxisWidth, getHeight()-bottomAxisHeight);
    }

    public Chart addSerie(Serie s){
        series.add(s);
        if(s.getMinX() < minX){ minX = s.getMinX();}
        if(s.getMinY() < minX){ minX = s.getMinY();}
        if(s.getMinX() > maxX){ maxX = s.getMaxX();}
        if(s.getMinY() > maxY){ maxY = s.getMaxY();}
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p=new Paint();p.setColor(Color.parseColor("#a000ff00"));
        canvas.drawRect(chartArea,p);
        if(series!=null) {
            for (Serie s : series) {
                s.draw(canvas,chartArea);
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
