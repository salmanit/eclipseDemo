package heart;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Package com.example.administrator.testrecyclerview
 * Created by HuaChao on 2016/5/25.
 */
public class Bloom {
    private int color;//鏁翠釜鑺辨湹鐨勯鑹�
    private Point point;//鑺辫姱鍦嗗績
    private int radius; //鑺辫姱鍗婂緞
    private ArrayList<Petal> petals;//鐢ㄤ簬淇濆瓨鑺辩摚

    public Point getPoint() {
        return point;
    }


    public Bloom(Point point, int radius, int color, int petalCount) {
        this.point = point;
        this.radius = radius;
        this.color = color;
        petals = new ArrayList<Petal>(petalCount);


        float angle = 360f / petalCount;
        int startAngle = MyUtil.randomInt(0, 90);
        for (int i = 0; i < petalCount; i++) {
            //闅忔満浜х敓绗竴涓帶鍒剁偣鐨勬媺浼稿�嶆暟
            float stretchA = MyUtil.random(Garden.Options.minPetalStretch, Garden.Options.maxPetalStretch);
            //闅忔満浜х敓绗簩涓帶鍒跺湴鐨勬媺浼稿�嶆暟
            float stretchB = MyUtil.random(Garden.Options.minPetalStretch, Garden.Options.maxPetalStretch);
            //璁＄畻姣忎釜鑺辩摚鐨勮捣濮嬭搴�
            int beginAngle = startAngle + (int) (i * angle);
            //闅忔満浜х敓姣忎釜鑺辩摚鐨勫闀垮洜瀛愶紙鍗崇唤鏀鹃�熷害锛�
            float growFactor = MyUtil.random(Garden.Options.minGrowFactor, Garden.Options.maxGrowFactor);
            //鍒涘缓涓�涓姳鐡ｏ紝骞舵坊鍔犲埌鑺辩摚鍒楄〃涓�
            this.petals.add(new Petal(stretchA, stretchB, beginAngle, angle, color, growFactor));
        }
    }

    //缁樺埗鑺辨湹
    public void draw(Canvas canvas) {
        Petal p;
        for (int i = 0; i < this.petals.size(); i++) {
            p = petals.get(i);
            //娓叉煋姣忔湹鑺辨湹
            p.render(point, this.radius, canvas);

        }

    }

    public int getColor() {
        return color;
    }
}
