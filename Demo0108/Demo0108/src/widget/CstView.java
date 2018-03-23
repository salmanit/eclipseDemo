package widget;

import com.charlie.demo0108.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * ���ܣ�
 * ���ߣ�zhangxutong
 * ���䣺mcxtzhang@163.com
 * ��ҳ��http://blog.csdn.net/zxt0601
 * ʱ�䣺 2016/11/15.
 */

public class CstView extends View {
    private static final String TAG = "zxt";
    ;

    public CstView(Context context) {
        super(context);
    }

    public CstView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CstView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), (R.drawable.pic11)), 0, 0, new Paint());
        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(200, 100);
        path.lineTo(200, 200);
        path.lineTo(200, 300);
        path.close();
        Bitmap rightBitmap = getMaskBitmap(BitmapFactory.decodeResource(getResources(), (R.drawable.pic11)), path);
        canvas.drawBitmap(rightBitmap, -100, -100, new Paint());

    }

    //�����ұߵı���
    private Bitmap getMaskBitmap(Bitmap mBitmap, Path mask) {
        Bitmap bgBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Log.e(TAG, " getRightBitmap: " + bgBitmap.getWidth());
        //�Ѵ�����λͼ��Ϊ����
        Canvas mCanvas = new Canvas(bgBitmap);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //mCanvas.save();
        //�Ƚ�canvas����
        //��canvas�޼���ָ����·������
        //mCanvas.translate(100, 100);

        mCanvas.clipPath(mask);
        mCanvas.drawBitmap(mBitmap, 0, 0, mPaint);

        Log.e(TAG, "getRightBitmap: " + bgBitmap.getWidth());
        return bgBitmap;
    }

    private Bitmap leftBitmap;

/*    //������ߵĻ���ı���
    private void getLeftBitmap(Bitmap mBitmap) {
        Bitmap bgBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //�Ѵ�����λͼ��Ϊ����
        Canvas mCanvas = new Canvas(bgBitmap);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mCanvas.save();
        mCanvas.translate(point.x, point.y);
        mCanvas.clipPath(path);
        mCanvas.drawColor(Color.WHITE);
        mCanvas.restore();
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //�Ƚ�canvas����
        //��canvas�޼���ָ����·������
        mCanvas.drawBitmap(mBitmap, 0, 0, mPaint);
        leftBitmap = Bitmap.createBitmap(bgBitmap, point.x, point.y, w, w);
    }*/
}
