package heart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Package com.hc.testheart
 * Created by HuaChao on 2016/5/25.
 */
public class HeartView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder surfaceHolder;
    int offsetX;
    int offsetY;
    private Garden garden;
    private int width;
    private int height;
    private Paint backgroundPaint;
    private boolean isDrawing = false;
    private Bitmap bm;
    private Canvas canvas;
    private int heartRadio = 1;

    public HeartView(Context context) {
        super(context);
        init();
    }

    public HeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        garden = new Garden();
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.rgb(0xff, 0xff, 0xe0));


    }

    ArrayList<Bloom> blooms = new ArrayList<Bloom>();

    public Point getHeartPoint(float angle) {
        float t = (float) (angle / Math.PI);
        float x = (float) (heartRadio * (16 * Math.pow(Math.sin(t), 3)));
        float y = (float) (-heartRadio * (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t)));

        return new Point(offsetX + (int) x, offsetY + (int) y);
    }


    //缁樺埗鍒楄〃閲屾墍鏈夌殑鑺辨湹
    private void drawHeart() {
        canvas.drawRect(0, 0, width, height, backgroundPaint);
        for (Bloom b : blooms) {
            b.draw(canvas);
        }
        Canvas c = surfaceHolder.lockCanvas();
        if(c!=null) {
         	c.drawBitmap(bm, 0, 0, null);
            if(surfaceHolder.getSurface()!=null)
            	surfaceHolder.unlockCanvasAndPost(c);
        }
       
    }

    public void reDraw() {
//        blooms.clear();
//        drawOnNewThread();
    	if(isDrawing) {
    		reset=true;
    	}else {
    		blooms.clear();
    		drawOnNewThread();
    	}
    	
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

    }
    private boolean reset=false;
    //寮�鍚竴涓柊绾跨▼缁樺埗
    private void drawOnNewThread() {
        new Thread() {
            @Override
            public void run() {
                if (isDrawing) return;
                isDrawing = true;

                float angle = 10;
                while (isDrawing) {
                	if(reset) {
                		reset=false;
                		angle=10;
                		blooms.clear();
                	}
                    Bloom bloom = getBloom(angle);
                    if (bloom != null) {
                        blooms.add(bloom);
                    }
                    if (angle >= 30) {
                    	isDrawing=false;
                        break;
                    } else {
                        angle += 0.2;
                    }
                    drawHeart();
                    try {
                        sleep(110);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                isDrawing = false;
            }
        }.start();
    }

@Override
protected void onDetachedFromWindow() {
	isDrawing=false;
	super.onDetachedFromWindow();
}
    private Bloom getBloom(float angle) {

        Point p = getHeartPoint(angle);

        boolean draw = true;
        /**寰幆姣旇緝鏂扮殑鍧愭爣浣嶇疆鏄惁鍙互鍒涘缓鑺辨湹,
         * 涓轰簡闃叉鑺辨湹澶瘑闆�
         * */
        for (int i = 0; i < blooms.size(); i++) {

            Bloom b = blooms.get(i);
            Point bp = b.getPoint();
            float distance = (float) Math.sqrt(Math.pow(p.x - bp.x, 2) + Math.pow(p.y - bp.y, 2));
            if (distance < Garden.Options.maxBloomRadius * 1.5) {
                draw = false;
                break;
            }
        }
        //濡傛灉浣嶇疆闂磋窛婊¤冻瑕佹眰锛屽氨鍦ㄨ浣嶇疆鍒涘缓鑺辨湹骞跺皢鑺辨湹鏀惧叆鍒楄〃
        if (draw) {
            Bloom bloom = garden.createRandomBloom(p.x, p.y);
            return bloom;
        }
        return null;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        this.width = width;
        this.height = height;
        //鎴戠殑鎵嬫満瀹藉害鍍忕礌鏄�1080锛屽彂鐜板弬鏁拌缃负30姣旇緝鍚堥�傦紝杩欓噷鏍规嵁涓嶅悓鐨勫搴﹀姩鎬佽皟鏁村弬鏁�
        heartRadio = width * 30 / 1080;

        offsetX = width / 2;
        offsetY = height / 2 - 55;
        bm = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        canvas = new Canvas(bm);
        drawOnNewThread();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    
}