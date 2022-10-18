package online.flowerinsnow.xibaom.object;

public class LocationDimension {
    public int x;
    public int y;

    public LocationDimension(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        LocationDimension o = (LocationDimension) obj;
        return this.x == o.x && this.y == o.y;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{x=" + this.x + ",y=" + this.y + "}";
    }
}
