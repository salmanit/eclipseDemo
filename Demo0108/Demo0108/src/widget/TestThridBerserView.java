package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * ���ܣ�
 * ���ߣ�zhangxutong
 * ���䣺mcxtzhang@163.com
 * ��ҳ��http://blog.csdn.net/zxt0601
 * ʱ�䣺 2016/11/16.
 */

public class TestThridBerserView extends View {
    public TestThridBerserView(Context context) {
        super(context);
    }

    public TestThridBerserView(Context context, AttributeSet attrs) {
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
        mCaptchaPath.moveTo(mCaptchaX, mCaptchaY);

        int gap = mCaptchaWidth / 3;
        mCaptchaPath.lineTo(mCaptchaX + gap, mCaptchaY);
        int left = mCaptchaX + gap;
        int right = mCaptchaX + gap * 2;
        PointF start = new PointF(left, mCaptchaY);
        PointF end = new PointF(right, mCaptchaY);
        drawPartCircle(start, end, mCaptchaPath, false);


        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth, mCaptchaY);//�ڵ�
        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth, mCaptchaY + gap);

        drawPartCircle(new PointF(mCaptchaX + mCaptchaWidth, mCaptchaY + gap),
                new PointF(mCaptchaX + mCaptchaWidth, mCaptchaY + gap * 2),
                mCaptchaPath, false);

        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth, mCaptchaY + mCaptchaHeight);//�ڵ�
        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth - gap, mCaptchaY + mCaptchaHeight);

        drawPartCircle(new PointF(mCaptchaX + mCaptchaWidth - gap, mCaptchaY + mCaptchaHeight),
                new PointF(mCaptchaX + mCaptchaWidth - gap - gap, mCaptchaY + mCaptchaHeight),
                mCaptchaPath, false);

        mCaptchaPath.lineTo(mCaptchaX, mCaptchaY + mCaptchaHeight);//�ڵ�
        mCaptchaPath.lineTo(mCaptchaX, mCaptchaY + mCaptchaHeight - gap);

        drawPartCircle(new PointF(mCaptchaX, mCaptchaY + mCaptchaHeight - gap),
                new PointF(mCaptchaX, mCaptchaY + mCaptchaHeight - gap * 2),
                mCaptchaPath, false);


        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mCaptchaPath, paint);
    }


    /**
     * ������㡢�յ� ���ꡢ��͹��Path��
     * ���Զ����ư�͹�İ�Բ��
     *
     * @param start �������
     * @param end   �յ�����
     * @param path  ��Բ����������path��
     * @param outer �Ƿ�͹��Բ
     */
    private void drawPartCircle(PointF start, PointF end, Path path, boolean outer) {
        float c = 0.551915024494f;
        //�е�
        PointF middle = new PointF(start.x + (end.x - start.x) / 2, start.y + (end.y - start.y) / 2);
        //�뾶
        float r1 = (float) Math.sqrt(Math.pow((middle.x - start.x), 2) + Math.pow((middle.y - start.y), 2));
        //gapֵ
        float gap1 = r1 * c;

        if (start.x == end.x) {
            //������ֱ�����

            //�Ƿ��Ǵ��ϵ���
            boolean topToBottom = end.y - start.y > 0 ? true : false;
            //��������д�������еļ��㹫ʽ���Ƶģ���Ҫ���ҹ��̣�ֻ����ᡣ
            int flag;//��תϵ��
            if (topToBottom) {
                flag = 1;
            } else {
                flag = -1;
            }
            if (outer) {
                //͹�� ������Բ
                path.cubicTo(start.x + gap1 * flag, start.y,
                        middle.x + r1 * flag, middle.y - gap1 * flag,
                        middle.x + r1 * flag, middle.y);
                path.cubicTo(middle.x + r1 * flag, middle.y + gap1 * flag,
                        end.x + gap1 * flag, end.y,
                        end.x, end.y);
            } else {
                //���� ������Բ
                path.cubicTo(start.x - gap1 * flag, start.y,
                        middle.x - r1 * flag, middle.y - gap1 * flag,
                        middle.x - r1 * flag, middle.y);
                path.cubicTo(middle.x - r1 * flag, middle.y + gap1 * flag,
                        end.x - gap1 * flag, end.y,
                        end.x, end.y);
            }
        } else {
            //����ˮƽ�����

            //�Ƿ��Ǵ�����
            boolean leftToRight = end.x - start.x > 0 ? true : false;
            //��������д�������еļ��㹫ʽ���Ƶģ���Ҫ���ҹ��̣�ֻ����ᡣ
            int flag;//��תϵ��
            if (leftToRight) {
                flag = 1;
            } else {
                flag = -1;
            }
            if (outer) {
                //͹ ������Բ
                path.cubicTo(start.x, start.y - gap1 * flag,
                        middle.x - gap1 * flag, middle.y - r1 * flag,
                        middle.x, middle.y - r1 * flag);
                path.cubicTo(middle.x + gap1 * flag, middle.y - r1 * flag,
                        end.x, end.y - gap1 * flag,
                        end.x, end.y);
            } else {
                //�� ������Բ
                path.cubicTo(start.x, start.y + gap1 * flag,
                        middle.x - gap1 * flag, middle.y + r1 * flag,
                        middle.x, middle.y + r1 * flag);
                path.cubicTo(middle.x + gap1 * flag, middle.y + r1 * flag,
                        end.x, end.y + gap1 * flag,
                        end.x, end.y);
            }


/*
            û�Ƶ�֮ǰ�Ĺ�ʽ������
            if (start.x < end.x) {
                if (outer) {
                    //�����Բ ˳ʱ��
                    path.cubicTo(start.x, start.y - gap1,
                            middle.x - gap1, middle.y - r1,
                            middle.x, middle.y - r1);
                    //���Ұ�Բ:˳ʱ��
                    path.cubicTo(middle.x + gap1, middle.y - r1,
                            end.x, end.y - gap1,
                            end.x, end.y);
                } else {
                    //�����Բ ��ʱ��
                    path.cubicTo(start.x, start.y + gap1,
                            middle.x - gap1, middle.y + r1,
                            middle.x, middle.y + r1);
                    //���Ұ�Բ ��ʱ��
                    path.cubicTo(middle.x + gap1, middle.y + r1,
                            end.x, end.y + gap1,
                            end.x, end.y);
                }
            } else {
                if (outer) {
                    //���Ұ�Բ ˳ʱ��
                    path.cubicTo(start.x, start.y + gap1,
                            middle.x + gap1, middle.y + r1,
                            middle.x, middle.y + r1);
                    //�����Բ ˳ʱ��
                    path.cubicTo(middle.x - gap1, middle.y + r1,
                            end.x, end.y + gap1,
                            end.x, end.y);
                }
            }*/
        }
    }
}