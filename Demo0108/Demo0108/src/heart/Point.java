package heart;

/**
 * Package com.hc.testheart
 * Created by HuaChao on 2016/5/25.
 */
public class Point {

    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //鏃嬭浆
    public Point rotate(float theta) {
        int x = this.x;
        int y = this.y;
        this.x = (int) (Math.cos(theta) * x - Math.sin(theta) * y);
        this.y = (int) (Math.sin(theta) * x + Math.cos(theta) * y);
        return this;
    }

    //涔樹互涓�涓父鏁�
    public Point mult(float f) {
        this.x *= f;
        this.y *= f;
        return this;
    }

    //澶嶅埗
    public Point clone() {
        return new Point(this.x, this.y);
    }

    //璇ョ偣涓庡渾蹇冭窛绂�
    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    //鍚戦噺鐩稿噺
    public Point subtract(Point p) {
        this.x -= p.x;
        this.y -= p.y;
        return this;
    }

    //鍚戦噺鐩稿姞
    public Point add(Point p) {
        this.x += p.x;
        this.y += p.y;
        return this;
    }

    public Point set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
}
