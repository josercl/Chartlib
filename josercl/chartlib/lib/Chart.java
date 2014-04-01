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

/**
 * View used to draw charts
 * @author Jos&eacute; Rafael Carrero Le&oacute;n &lt;<a href="mailto:josercl@gmail.com">josercl@gmail.com</a>&gt;
 * @version 1.0
 */
public class Chart extends RelativeLayout {

    private List<Serie> series;

    private Rect chartArea;
    private double minX=Double.MAX_VALUE,maxX=Double.MIN_VALUE,minY=Double.MAX_VALUE,maxY=Double.MIN_VALUE;

    /* Axis */
    private LinearLayout leftAxis,rightAxis,topAxis,bottomAxis;
    /**
     * Boolean value to control left axis and labels
     */
    private boolean showLeftAxis=false;
    /**
     * Boolean value to control right axis and labels
     */
    private boolean showRightAxis=false;
    /**
     * Boolean value to control top axis and labels
     */
    private boolean showTopAxis=false;
    /**
     * Boolean value to control bottom axis and labels
     */
    private boolean showBottomAxis=false;
    /**
     * Left axis width, 40 by default
     */
    private int leftAxisWidth=40;
    /**
     * Right axis width, 40 by default
     */
    private int rightAxisWidth=40;
    /**
     * Bottom axis width, 40 by default
     */
    private int bottomAxisHeight=40;
    /**
     * Top axis width, 40 by default
     */
    private int topAxisHeight=40;


    /* Grid attributes */
    /**
     * Grid lines color, default to ARGB #80c0c0c0
     */
    private int gridColor=Color.parseColor("#80c0c0c0");
    /**
     * Number of vertical grid lines, default to 10
     */
    private int verticalGridLines=10;
    /**
     * Number of horizontal grid lines, default to 10
     */
    private int horizontalGridLines=10;
    /**
     * Grid lines stroke width, default to 2
     */
    private int gridStroke=1;
    /**
     * Boolean value that controls grid visibility
     */
    private boolean showGrid=true;

    private final DecimalFormat dm=new DecimalFormat("0.#");

    /**
     * Simple constructor when creating a view from code
     * @param context The Context the view is running in, through which it can access the current theme, resources, etc.
     */
    public Chart(Context context) {
        this(context,null,0);
    }

    /**
     * Constructor that is called when inflating a view from XML. This is called when a view is being constructed from an XML file, supplying attributes that were specified in the XML file. This version uses a default style of 0, so the only attribute values applied are those in the Context's Theme and the given AttributeSet.
     * @param context The Context the view is running in, through which it can access the current theme, resources, etc.
     * @param attrs The Context the view is running in, through which it can access the current theme, resources, etc.
     */
    public Chart(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style. This constructor of View allows subclasses to use their own base style when they are inflating. For example, a Button class's constructor would call this version of the super class constructor and supply R.attr.buttonStyle for defStyle; this allows the theme's button style to modify all of the base view attributes (in particular its background) as well as the Button class's attributes.
     * @param context The Context the view is running in, through which it can access the current theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a reference to a style resource to apply to this view. If 0, no default style will be applied.
     */
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

    /**
     * Adds a Serie to the chart
     * @param s The Serie that will be added to the chart
     */
    public void addSerie(Serie s){
        series.add(s);
        if(s.getMinX() < minX){ minX = s.getMinX();}
        if(s.getMaxX() > maxX){ maxX = s.getMaxX();}

        if(s.getMinY() < minY){ minY = s.getMinY();}
        if(s.getMaxY() > maxY){ maxY = s.getMaxY();}

        /*for(Serie se:series) {
            se.setMinX(minX);
            se.setMinY(minY);
            se.setMaxX(maxX);
            se.setMaxY(maxY);
        }*/
    }

    /**
     * Returns the serie at the specified order in the chart
     * @param i The index of the serie to return
     * @return The serie that was added in the specified index to the chart
     */
    public Serie getSerie(int i){
        return series.get(i);
    }

    /**
     *
     * @return The total of series added to the chart
     */
    public int getSeriesCount(){
        return series.size();
    }

    /**
     * Removes all series and clears the chart
     */
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

    /**
     * Draws numbers in all the visible axes
     */
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

    /**
     * Used to draw labels on Top/Bottom axis
     * @param axis The axis on which the labels will be drawn
     * @param axisHeight The axis height
     */
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

    /**
     * Used to draw labels on Left/Right axis
     * @param axis The axis on which the labels will be drawn
     * @param axisWidth The axis width
     */
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

    /**
     * Shortcut to showLeftAxis(left);showRightAxis(right);showTopAxis(top);showBottomAxis(bottom)
     * @param left if true, sets the left axis as visible
     * @param top if true, sets the top axis as visible
     * @param right if true, sets the right axis as visible
     * @param bottom if true, sets the bottom axis as visible
     * @see #showLeftAxis(boolean)
     * @see #showRightAxis(boolean)
     * @see #showTopAxis(boolean)
     * @see #showBottomAxis(boolean)
     */
    public void showAxes(boolean left,boolean top,boolean right,boolean bottom){
        showLeftAxis(left);
        showRightAxis(right);
        showTopAxis(top);
        showBottomAxis(bottom);
    }

    private void drawGrid(Canvas canvas,Rect bounds){
        float horizontalGridSep=(bounds.height()-2*gridStroke)/horizontalGridLines;
        float verticalGridSep=bounds.width()/verticalGridLines;

        Paint gridPaint=new Paint();

        gridPaint.setStrokeWidth(gridStroke);
        gridPaint.setColor(gridColor);

        for (int i = 0; i <= verticalGridLines; i++) {
            canvas.drawLine(bounds.left + (i*verticalGridSep) + gridStroke, bounds.top, bounds.left + (i*verticalGridSep) + gridStroke, bounds.bottom, gridPaint);
        }

        for (int i=0; i<horizontalGridLines;i++){
            canvas.drawLine(bounds.left - leftAxisWidth/8, bounds.top + i*horizontalGridSep+gridStroke,bounds.right + rightAxisWidth/8,bounds.top+i*horizontalGridSep+gridStroke,gridPaint);
        }
        canvas.drawLine(bounds.left - leftAxisWidth/8, bounds.bottom-gridStroke,bounds.right + rightAxisWidth/8,bounds.bottom-gridStroke,gridPaint);
    }

    /**
     * Controls left axis visibility
     * @param showLeftAxis if true, the left axis and labels will be displayed
     * @see #showAxis(boolean, boolean, boolean, boolean)
     */
    public void showLeftAxis(boolean showLeftAxis) {
        this.showLeftAxis = showLeftAxis;
    }

    /**
     * Controls right axis visibility
     * @param showRightAxis if true, the right axis and labels will be displayed
     * @see #showAxis(boolean, boolean, boolean, boolean)
     */
    public void showRightAxis(boolean showRightAxis) {
        this.showRightAxis = showRightAxis;
    }

    /**
     * Controls top axis visibility
     * @param showTopAxis if true, the top axis and labels will be displayed
     * @see #showAxis(boolean, boolean, boolean, boolean)
     */
    public void showTopAxis(boolean showTopAxis) {
        this.showTopAxis = showTopAxis;
    }

    /**
     * Controls bottom axis visibility
     * @param showBottomAxis if true, the bottom axis and labels will be displayed
     * @see #showAxis(boolean, boolean, boolean, boolean)
     */
    public void showBottomAxis(boolean showBottomAxis) {
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

    public void showGrid(boolean showGrid) {
        this.showGrid = showGrid;
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
