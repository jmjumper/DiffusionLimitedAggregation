package jmjumper.diffusionlimitedaggregation.Components;

public class Vector {

    private double x, y;

    public Vector ( double x, double y ) {
        this.x = x;
        this.y = y;
    }

    public static double distance ( Vector v1, Vector v2 ) {
        double dx = v1.getX() - v2.getX();
        double dy = v1.getY() - v2.getY();
        return Math.sqrt( dx * dx + dy * dy );
    }

    // The square-root is not calculated for efficiency
    public static double distanceSquared ( Vector v1, Vector v2 ) {
        double dx = v1.getX() - v2.getX();
        double dy = v1.getY() - v2.getY();
        return dx * dx + dy * dy;
    }


    public static Vector add ( Vector v1, Vector v2 ) {
        return new Vector( v1.getX() + v2.getX(), v1.getY() + v2.getY() );
    }

    public void add ( Vector v ) {
        x += v.getX();
        y += v.getY();
    }

    public void sub ( Vector v ) {
        x -= v.getX();
        y -= v.getY();
    }

    public void multiply ( double d ) {
        x *= d;
        y *= d;
    }

    public void divide ( double d ) {
        x /= d;
        y /= d;
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
}
