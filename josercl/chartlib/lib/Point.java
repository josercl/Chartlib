package josercl.chartlib.lib;

/**
 * @author Jos&eacute; Rafael Carrero Le&oacute;n &lt;<a href="mailto:josercl@gmail.com">josercl@gmail.com</a>&gt;
 * @version 1.0
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
