package josercl.chartlib.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eseprin on 3/20/14.
 */
public class Chart extends View {

    private List<Serie> series;

    public Chart(Context context) {
        super(context);
        series=new ArrayList<Serie>();
    }

    public Chart(Context context, AttributeSet attrs) {
        super(context, attrs);
        series=new ArrayList<Serie>();
    }

    public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        series=new ArrayList<Serie>();
    }

    public Chart addSerie(Serie s){
        series.add(s);
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(series!=null) {
            for (Serie s : series) {
                s.draw(canvas);
            }
        }
    }
}
