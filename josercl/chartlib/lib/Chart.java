package josercl.chartlib.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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

    private int leftAxisWidth=20;
    private int rightAxisWidth=20;
    private int bottomAxisHeight=20;
    private int topAxisHeight=20;

    private int horizontalGridColor=Color.parseColor("#80c0c0c0");
    private int verticalGridColor=Color.parseColor("#80c0c0c0");
    private double horizontalGridGap=1d;
    private double verticalGridGap=1d;
    private boolean showGrid=true;

    int verticalGridLines=10;
    int horizontalGridLines=10;

    private double minX=Double.MAX_VALUE,maxX=Double.MIN_VALUE,minY=Double.MAX_VALUE,maxY=Double.MIN_VALUE;

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

        chartArea=new Rect(0,0,getWidth(),getHeight());

        leftAxis=new LinearLayout(context);
        LayoutParams leftparams=new LayoutParams(leftAxisWidth, LayoutParams.MATCH_PARENT);
        leftAxis.setOrientation(LinearLayout.VERTICAL);
        leftAxis.setLayoutParams(leftparams);

        rightAxis=new LinearLayout(context);
        LayoutParams rightparams=new LayoutParams(rightAxisWidth, LayoutParams.MATCH_PARENT);
        rightparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightAxis.setOrientation(LinearLayout.VERTICAL);
        rightAxis.setLayoutParams(rightparams);

        bottomAxis=new LinearLayout(context);
        LayoutParams bottomparams=new LayoutParams(LayoutParams.MATCH_PARENT, bottomAxisHeight);
        bottomparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bottomAxis.setOrientation(LinearLayout.HORIZONTAL);
        bottomAxis.setLayoutParams(bottomparams);

        topAxis=new LinearLayout(context);
        LayoutParams topparams=new LayoutParams(LayoutParams.MATCH_PARENT, topAxisHeight);
        topAxis.setOrientation(LinearLayout.HORIZONTAL);
        topAxis.setLayoutParams(topparams);

        if(showLeftAxis){addView(leftAxis);}
        if(showRightAxis){addView(rightAxis);}
        if(showBottomAxis){addView(bottomAxis);}
        if(showTopAxis){addView(topAxis);}
    }

    public Chart addSerie(Serie s){
        series.add(s);
        if(s.getMinX() < minX){ minX = s.getMinX();}
        if(s.getMaxX() > maxX){ maxX = s.getMaxX();}

        if(s.getMinY() < minY){ minY = s.getMinY();}
        if(s.getMaxY() > maxY){ maxY = s.getMaxY();}

        for(Serie se:series) {
            se.minX = minX;
            se.minY = minY;
            se.maxX = maxX;
            se.maxY = maxY;
        }

        return this;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        LayoutParams leftparams= (LayoutParams) leftAxis.getLayoutParams();
        leftparams.width=leftAxisWidth;
        leftparams.height=getHeight();
        leftAxis.setLayoutParams(leftparams);

        LayoutParams rightparams= (LayoutParams) rightAxis.getLayoutParams();
        rightparams.width=rightAxisWidth;
        rightparams.height=getHeight();
        rightAxis.setLayoutParams(rightparams);

        LayoutParams bottomparams= (LayoutParams) bottomAxis.getLayoutParams();
        bottomparams.height=bottomAxisHeight;
        bottomparams.width=getWidth();
        bottomAxis.setLayoutParams(bottomparams);

        LayoutParams topparams= (LayoutParams) topAxis.getLayoutParams();
        topparams.height=topAxisHeight;
        topparams.width=getWidth();
        topAxis.setLayoutParams(topparams);

        if(!showLeftAxis){leftAxisWidth=0;}
        if(!showRightAxis){rightAxisWidth=0;}
        if(!showBottomAxis){bottomAxisHeight=0;}
        if(!showTopAxis){topAxisHeight=0;}

        chartArea.set(leftAxisWidth, topAxisHeight, getWidth()-rightAxisWidth, getHeight()-bottomAxisHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (showGrid){ drawGrid(canvas,chartArea);drawGridLabels(chartArea); }
        if(series!=null) {
            for (Serie s : series) {
                s.draw(canvas,chartArea);
            }
        }
    }

    private void drawGridLabels(Rect bounds){
        float horizontalGridSep=bounds.height()/(horizontalGridLines+1);
        DecimalFormat dm=new DecimalFormat("0.0");
        for(int i=0;i<=horizontalGridLines;i++){
            if(showLeftAxis){
                TextView label=new TextView(getContext());
                label.setMinWidth(leftAxisWidth);
                label.setHeight(new Double(horizontalGridSep).intValue());
                label.setText(dm.format(maxY - i * (maxY - minY) / horizontalGridLines));
                label.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
                label.setTextSize(10);
                label.setPadding(0,0,leftAxisWidth/4,0);
                leftAxis.addView(label);
            }
        }
    }

    private void drawGrid(Canvas canvas,Rect bounds){
        float horizontalGridSep=bounds.height()/horizontalGridLines;
        float verticalGridSep=bounds.width()/verticalGridLines;

        Paint gridPaint=new Paint();
        gridPaint.setStrokeWidth(2);

        gridPaint.setColor(verticalGridColor);
        for (int i = 0; i < verticalGridLines; i++) {
            canvas.drawLine(bounds.left + (verticalGridSep * i), bounds.top, bounds.left + (verticalGridSep * i), bounds.bottom, gridPaint);
        }

        for (int i=0; i< horizontalGridLines;i++){
            canvas.drawLine(bounds.left - leftAxisWidth/4, bounds.top + i*horizontalGridSep,bounds.right + rightAxisWidth/4,bounds.top+i*horizontalGridSep,gridPaint);
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

    public int getLeftAxisWidth() {
        return leftAxisWidth;
    }

    public void setLeftAxisWidth(int leftAxisWidth) {
        this.leftAxisWidth = leftAxisWidth;
    }

    public int getRightAxisWidth() {
        return rightAxisWidth;
    }

    public void setRightAxisWidth(int rightAxisWidth) {
        this.rightAxisWidth = rightAxisWidth;
    }

    public int getBottomAxisHeight() {
        return bottomAxisHeight;
    }

    public void setBottomAxisHeight(int bottomAxisHeight) {
        this.bottomAxisHeight = bottomAxisHeight;
    }

    public int getTopAxisHeight() {
        return topAxisHeight;
    }

    public void setTopAxisHeight(int topAxisHeight) {
        this.topAxisHeight = topAxisHeight;
    }

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }

    public int getVerticalGridColor() {
        return verticalGridColor;
    }

    public void setVerticalGridColor(int verticalGridColor) {
        this.verticalGridColor = verticalGridColor;
    }

    public int getHorizontalGridColor() {
        return horizontalGridColor;
    }

    public void setHorizontalGridColor(int horizontalGridColor) {
        this.horizontalGridColor = horizontalGridColor;
    }

    public double getVerticalGridGap() {
        return verticalGridGap;
    }

    public void setVerticalGridGap(double verticalGridGap) {
        this.verticalGridGap = verticalGridGap;
    }

    public double getHorizontalGridGap() {
        return horizontalGridGap;
    }

    public void setHorizontalGridGap(double horizontalGridGap) {
        this.horizontalGridGap = horizontalGridGap;
    }
}
