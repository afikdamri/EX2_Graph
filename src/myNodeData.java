public class myNodeData implements NodeData{
    int key;
    GeoLocation n;

    public myNodeData(int key, GeoLocation n) {
        this.key = key;
        this.n = n;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.n;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.n = p;
    }

    @Override
    public double getWeight() { // not use
        return 0;
    } //not use

    @Override
    public void setWeight(double w) { //not use
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {
        return 0;
    }

    @Override
    public void setTag(int t) {

    }

    public static class myGeoLocation implements GeoLocation {
        double x;
        double y;
        double z;

        public myGeoLocation(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public  myGeoLocation(myGeoLocation p){
            this.x = p.x;
            this.y = p.y;
            this.z = p.z;
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
            return Math.sqrt(Math.pow(this.x-g.x(),2)+Math.pow(this.y-g.y(),2)+Math.pow(this.z-g.z(),2));
        }
    }
}
