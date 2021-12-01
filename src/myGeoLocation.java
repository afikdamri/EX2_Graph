import java.util.Scanner;

public class myGeoLocation implements GeoLocation {
    double x;
    double y;
    double z;

    public myGeoLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public myGeoLocation(String p) {
        Scanner sr = new Scanner(p);
        sr.useDelimiter(",");
        this.x = Double.parseDouble(sr.next());
        this.y = Double.parseDouble(sr.next());
        this.z = Double.parseDouble(sr.next());
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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        return Math.sqrt(Math.pow(this.x - g.x(), 2) + Math.pow(this.y - g.y(), 2) + Math.pow(this.z - g.z(), 2));
    }

    @Override
    public String toString() {
        return "myGeoLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
