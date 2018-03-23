package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * ���ܣ�
 * ���ߣ�zhangxutong
 * ���䣺mcxtzhang@163.com
 * ��ҳ��http://blog.csdn.net/zxt0601
 * ʱ�䣺 2016/11/16.
 */

public class TestSinView extends View {
    public TestSinView(Context context) {
        super(context);
    }

    public TestSinView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //��֤�� ��Ӱ����ͼ��Path
    private Path mCaptchaPath = new Path();
    //��֤������Ͻ�(���)��x y
    private int mCaptchaX = 200;
    private int mCaptchaY = 200;
    //��֤��Ŀ��
    private int mCaptchaWidth = 300;
    private int mCaptchaHeight = 300;

    @Override
    protected void onDraw(Canvas canvas) {


        mCaptchaPath.reset();
        //�����Ͻǿ�ʼ ����һ�����������Ӱ
        mCaptchaPath.moveTo(mCaptchaX, mCaptchaY);


/*        mCaptchaPath.lineTo(mCaptchaX + gap, mCaptchaY);
        //������͹ �����Ƕ��Path �޷��պϣ���ֱ������
        int r = mCaptchaWidth / 2 - gap;
        RectF oval = new RectF(mCaptchaX + gap, mCaptchaY - (r), mCaptchaX + gap + r * 2, mCaptchaY + (r));
        mCaptchaPath.arcTo(oval, 180, 180);*/

        int gap = mCaptchaWidth / 3;
        mCaptchaPath.lineTo(mCaptchaX + gap, mCaptchaY);
        //�����������߷��� Y = A sin(wx+FAI)+k�������������괫��Path.quadTo()���������Ʊ��������ߣ���,�����������ߡ�

        W = (float) (Math.PI / (mCaptchaWidth - 2 * gap));
        FAI = (float) (-W * (mCaptchaX + gap) + Math.PI);

        for (int x = mCaptchaX + gap; x < mCaptchaX + mCaptchaWidth - gap; x++) {
            float y = (float) (A * Math.sin(W * x + FAI) + K + mCaptchaY);
   /*         if (x == mCaptchaX + gap) {
                mCaptchaPath.moveTo(x, y);
            }*/
            mCaptchaPath.quadTo(x, y, x + 1, y);
        }


        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth, mCaptchaY);//�ڵ�

        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth, mCaptchaY + gap);

        W = (float) (Math.PI / (mCaptchaHeight - 2 * gap));
        FAI = (float) (-W * (mCaptchaY + gap) + Math.PI);

        for (int x = mCaptchaY + gap; x < mCaptchaY + mCaptchaHeight - gap; x++) {
            float y = (float) (A * Math.sin(W * x + FAI) + K + mCaptchaX + mCaptchaWidth);
   /*         if (x == mCaptchaX + gap) {
                mCaptchaPath.moveTo(x, y);
            }*/
            mCaptchaPath.quadTo(y, x, y + 1, x);
        }




        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mCaptchaPath, paint);
    }


    /**
     * ����ԲX��ƫ��
     */
    private float FAI = 0;
    /**
     * ����Բ���
     */
    private float A = 50;
    /**
     * ����Բ������
     */
    private float W;
    /**
     * ����ԲY��ƫ��
     */
    private float K = 0;


    /**
     * �Ƕ�ת���ɻ���
     *
     * @param degree
     * @return
     */
    private double degreeToRad(double degree) {
        return degree * Math.PI / 180;
    }
}
