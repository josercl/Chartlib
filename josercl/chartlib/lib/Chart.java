package josercl.chartlib.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Chart extends RelativeLayout {

    private LinearLayout leftAxis,rightAxis,topAxis,bottomAxis;
    private boolean showLeftAxis=false,showRightAxis=false,showTopAxis=false,showBottomAxis=false;
    private List<Serie> series;
    private Rect chartArea;

    private int leftAxisWidth=40;
    private int rightAxisWidth=40;
    private int bottomAxisHeight=40;
    private int topAxisHeight=40;

    private int gridColor=Color.parseColor("#80c0c0c0");
    private double horizontalGridGap=1d;
    private double verticalGridGap=1d;
    private int verticalGridLines=10;
    private int horizontalGridLines=10;
    private int gridStroke=2;
    private boolean showGrid=true;

    private final DecimalFormat dm=new DecimalFormat("0.#");

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

        LayoutParams leftparams=new LayoutParams(leftAxisWidth, LayoutParams.MATCH_PARENT);
        leftAxis=new LinearLayout(context);
        leftAxis.setOrientation(LinearLayout.VERTICAL);
        leftAxis.setLayoutParams(leftparams);

        LayoutParams rightparams=new LayoutParams(rightAxisWidth, LayoutParams.MATCH_PARENT);
        rightparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightAxis=new LinearLayout(context);
        rightAxis.setOrientation(LinearLayout.VERTICAL);
        rightAxis.setLayoutParams(rightparams);

        LayoutParams bottomparams=new LayoutParams(LayoutParams.MATCH_PARENT, bottomAxisHeight);
        bottomparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bottomAxis=new LinearLayout(context);
        bottomAxis.setOrientation(LinearLayout.HORIZONTAL);
        bottomAxis.setLayoutParams(bottomparams);

        topAxis=new LinearLayout(context);
        LayoutParams topparams=new LayoutParams(LayoutParams.MATCH_PARENT, topAxisHeight);
        topAxis.setOrientation(LinearLayout.HORIZONTAL);
        topAxis.setLayoutParams(topparams);

        addView(leftAxis);
        addView(rightAxis);
        addView(bottomAxis);
        addView(topAxis);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if(!showLeftAxis){leftAxisWidth=0;}
        if(!showRightAxis){rightAxisWidth=0;}
        if(!showBottomAxis){bottomAxisHeight=0;}
        if(!showTopAxis){topAxisHeight=0;}

        chartArea.set(leftAxisWidth + gridStroke, topAxisHeight + gridStroke, getWidth()-rightAxisWidth - gridStroke, getHeight()-bottomAxisHeight - gridStroke);

        if(changed) {
            LayoutParams leftparams = (LayoutParams) leftAxis.getLayoutParams();
            leftparams.width = leftAxisWidth;
            leftparams.height = getHeight() - topAxisHeight - bottomAxisHeight;
            leftparams.topMargin = topAxisHeight;
            leftAxis.setLayoutParams(leftparams);

            LayoutParams rightparams = (LayoutParams) rightAxis.getLayoutParams();
            rightparams.width = rightAxisWidth;
            rightparams.height = getHeight() - topAxisHeight - bottomAxisHeight;
            rightparams.topMargin = topAxisHeight;
            rightAxis.setLayoutParams(rightparams);

            LayoutParams bottomparams = (LayoutParams) bottomAxis.getLayoutParams();
            bottomparams.height = bottomAxisHeight;
            bottomparams.width = getWidth() - leftAxisWidth - rightAxisWidth;
            bottomparams.leftMargin = leftAxisWidth;
            bottomAxis.setLayoutParams(bottomparams);

            LayoutParams topparams = (LayoutParams) topAxis.getLayoutParams();
            topparams.height = topAxisHeight;
            topparams.width = getWidth() - leftAxisWidth - rightAxisWidth;
            topparams.leftMargin = leftAxisWidth;
            topAxis.setLayoutParams(topparams);

            leftAxis.layout(0,chartArea.top,chartArea.right,chartArea.bottom);
            rightAxis.layout(chartArea.left,chartArea.top,0,chartArea.bottom);
            topAxis.layout(chartArea.left,0,chartArea.right,chartArea.bottom);
            bottomAxis.layout(chartArea.left,chartArea.top,chartArea.right,0);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (showGrid){
            drawGrid(canvas,chartArea);
            drawGridLabels();
        }
        if(series!=null) {
            for (Serie s : series) {
                s.draw(canvas,chartArea);
            }
        }
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

    public Serie getSerie(int i){
        return series.get(i);
    }

    public int getSeriesCount(){
        return series.size();
    }

    public void clear(){
        for(int i=0;i<series.size();i++){
            series.remove(i);
        }
        leftAxis.removeAllViews();
        rightAxis.removeAllViews();
        topAxis.removeAllViews();
        bottomAxis.removeAllViews();

        minX=Double.MAX_VALUE;maxX=Double.MIN_VALUE;minY=Double.MAX_VALUE;maxY=Double.MIN_VALUE;
    }

    private void drawGridLabels(){
        if (showLeftAxis) {
            drawVerticalAxisLabels(leftAxis,leftAxisWidth);
        }

        if (showRightAxis) {
            drawVerticalAxisLabels(rightAxis,rightAxisWidth);
        }

        if(showBottomAxis){
            drawHorizontalAxisLabels(bottomAxis,bottomAxisHeight);
        }
        if(showTopAxis){
            drawHorizontalAxisLabels(topAxis,topAxisHeight);
        }
    }

    private void drawHorizontalAxisLabels(LinearLayout axis, int axisHeight){
        axis.setWeightSum(verticalGridLines);
        TextView label = null;

        for(int i=0;i<=verticalGridLines;i++) {
            label = (TextView) axis.getChildAt(i);
            if (label == null) {
                label = new TextView(getContext());
                label.setTextSize(10);
                label.setTextColor(Color.BLACK);
                label.setPadding(0, 0, 0, 0);
                int gravity = Gravity.TOP;
                label.setGravity(gravity);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
                params.weight = 1;
                label.setLayoutParams(params);

                axis.addView(label);
            }

            label.setText(dm.format(minX + i * (maxX - minX) / verticalGridLines));
        }
        for(int i=verticalGridLines+1;i<axis.getChildCount();i++){
            axis.removeViewAt(i);
        }
    }

    private void drawVerticalAxisLabels(LinearLayout axis,int axisWidth){
        axis.setWeightSum(horizontalGridLines);
        TextView label = null;

        for(int i=0;i<=horizontalGridLines;i++) {
            label = (TextView) axis.getChildAt(i);
            if (label == null) {
                label = new TextView(getContext());
                label.setTextSize(10);
                label.setTextColor(Color.BLACK);
                label.setPadding(0, 0, axisWidth / 4, 0);
                int gravity = Gravity.RIGHT;
                if (i > 0) {
                    gravity |= Gravity.BOTTOM;
                }
                label.setGravity(gravity);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,0);
                params.weight = 1;
                if (i <= 1) {
                    params.weight = .5f;
                }
                label.setLayoutParams(params);

                axis.addView(label);
            }

            label.setText(dm.format(maxY - i * (maxY - minY) / horizontalGridLines));
        }
        for(int i=horizontalGridLines+1;i<axis.getChildCount();i++){
            axis.removeViewAt(i);
        }
    }

    public void showAxis(boolean left,boolean top,boolean right,boolean bottom){
        setShowLeftAxis(left);
        setShowRightAxis(right);
        setShowTopAxis(top);
        setShowBottomAxis(bottom);
    }

    private void drawGrid(Canvas canvas,Rect bounds){
        float horizontalGridSep=bounds.height()/horizontalGridLines;
        float verticalGridSep=bounds.width()/verticalGridLines;

        Paint gridPaint=new Paint();

        gridPaint.setStrokeWidth(gridStroke);
        gridPaint.setColor(gridColor);

        for (int i = 0; i <= verticalGridLines; i++) {
            canvas.drawLine(bounds.left + (verticalGridSep * i), bounds.top, bounds.left + (verticalGridSep * i), bounds.bottom, gridPaint);
        }

        for (int i=0; i<= horizontalGridLines;i++){
            canvas.drawLine(bounds.left - leftAxisWidth/8, bounds.top + i*horizontalGridSep,bounds.right + rightAxisWidth/8,bounds.top+i*horizontalGridSep,gridPaint);
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

    public int getHorizontalGridLines() {
        return horizontalGridLines;
    }

    public void setHorizontalGridLines(int horizontalGridLines) {
        this.horizontalGridLines = horizontalGridLines;
    }

    public int getVerticalGridLines() {
        return verticalGridLines;
    }

    public void setVerticalGridLines(int verticalGridLines) {
        this.verticalGridLines = verticalGridLines;
    }

    public int getGridStroke() {
        return gridStroke;
    }

    public void setGridStroke(int gridStroke) {
        this.gridStroke = gridStroke;
    }

    public int getGridColor() {
        return gridColor;
    }

    public void setGridColor(int gridColor) {
        this.gridColor = gridColor;
    }
}
