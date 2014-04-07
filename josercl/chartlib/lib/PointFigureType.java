package josercl.chartlib.lib;

/**
 * @author Jos&eacute; Rafael Carrero Le&oacute;n &lt;<a href="mailto:josercl@gmail.com">josercl@gmail.com</a>&gt;
 * @version 1.0
 */
public enum PointFigureType{
    SQUARE,
    CIRCLE,
    DIAMOND,
    CROSS,
    X,
    TRIANGLE;

    public static PointFigureType getInstance(int n){
        switch(n){
            default:
            case 0: return SQUARE;
            case 1: return CIRCLE;
            case 2: return DIAMOND;
            case 3: return CROSS;
            case 4: return X;
            case 5: return TRIANGLE;
        }
    }
}
