package heart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Package com.example.administrator.testrecyclerview
 * Created by HuaChao on 2016/5/24.
 */
public class Petal {
    private float stretchA;//绗竴涓帶鍒剁偣寤堕暱绾垮�嶆暟
    private float stretchB;//绗簩涓帶鍒剁偣寤堕暱绾垮�嶆暟
    private float startAngle;//璧峰鏃嬭浆瑙掞紝鐢ㄤ簬纭畾绗竴涓鐐�
    private float angle;//涓ゆ潯绾夸箣闂村す瑙掞紝鐢辫捣濮嬫棆杞鍜屽す瑙掑彲浠ョ‘瀹氱浜屼釜绔偣
    private int radius = 2;//鑺辫姱鐨勫崐寰�
    private float growFactor;//澧為暱鍥犲瓙锛岃姳鐡ｆ槸鏈夊紑鏀剧殑鍔ㄧ敾鏁堟灉锛岃繖涓弬鏁板喅瀹氳姳鐡ｅ睍寮�閫熷害
    private int color;//鑺辩摚棰滆壊
    private boolean isFinished = false;//鑺辩摚鏄惁缁芥斁瀹屾垚
    private Path path = new Path();//鐢ㄤ簬淇濆瓨涓夋璐濆灏旀洸绾�
    private Paint paint = new Paint();//鐢荤瑪
    //鏋勯�犲嚱鏁帮紝鐢辫姳鏈电被璋冪敤
    public Petal(float stretchA, float stretchB, float startAngle, float angle, int color, float growFactor) {
        this.stretchA = stretchA;
        this.stretchB = stretchB;
        this.startAngle = startAngle;
        this.angle = angle;
        this.color = color;
        this.growFactor = growFactor;
        paint.setColor(color);
    }
    //鐢ㄤ簬娓叉煋鑺辩摚锛岄�氳繃涓嶆柇鏇存敼鍗婂緞浣垮緱鑺辩摚瓒婃潵瓒婂ぇ
    public void render(Point p, int radius, Canvas canvas) {
        if (this.radius <= radius) {
            this.radius += growFactor; // / 10;
        } else {
            isFinished = true;
        }
        this.draw(p, canvas);
    }

    //缁樺埗鑺辩摚锛屽弬鏁皃鏄姳鑺殑鍦嗗績鐨勫潗鏍�
    private void draw(Point p, Canvas canvas) {
        if (!isFinished) {

            path = new Path();
            //灏嗗悜閲忥紙0锛宺adius锛夋棆杞捣濮嬭搴︼紝绗竴涓帶鍒剁偣鏍规嵁杩欎釜鏃嬭浆鍚庣殑鍚戦噺璁＄畻
            Point t = new Point(0, this.radius).rotate(MyUtil.degrad(this.startAngle));
            //绗竴涓鐐癸紝涓轰簡淇濊瘉鍦嗗績涓嶄細闅忕潃radius澧炲ぇ鑰屽彉澶ц繖閲屽浐瀹氫负3
            Point v1 = new Point(0, 3).rotate(MyUtil.degrad(this.startAngle));
            //绗簩涓鐐�
            Point v2 = t.clone().rotate(MyUtil.degrad(this.angle));
            //寤堕暱绾匡紝鍒嗗埆纭畾涓や釜鎺у埗鐐�
            Point v3 = t.clone().mult(this.stretchA);
            Point v4 = v2.clone().mult(this.stretchB);
            //鐢变簬鍦嗗績鍦╬鐐癸紝鍥犳锛屾瘡涓偣瑕佸姞鍦嗗績鍧愭爣鐐�
            v1.add(p);
            v2.add(p);
            v3.add(p);
            v4.add(p);
            path.moveTo(v1.x, v1.y);
            //鍙傛暟鍒嗗埆鏄細绗竴涓帶鍒剁偣锛岀浜屼釜鎺у埗鐐癸紝缁堢偣
            path.cubicTo(v3.x, v3.y, v4.x, v4.y, v2.x, v2.y);
        }
        canvas.drawPath(path, paint);
    }


}