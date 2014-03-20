package josercl.chartlib.lib;

/**
 * Created by eseprin on 3/20/14.
 */
public class Point implements Comparable<Point>{
    private double x,y;

    public Point(){}
    public Point(double x,double y){
        setX(x);setY(y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public int compareTo(Point point) {
        return Double.compare(x,point.getX());
    }
}
