package widget;


import android.graphics.Path;
import android.graphics.PointF;

/**
 * ���ܣ�������ͼ�Ĺ�����
 * ���ߣ�zhangxutong
 * ���䣺mcxtzhang@163.com
 * ��ҳ��http://blog.csdn.net/zxt0601
 * ʱ�䣺 2016/11/16.
 */

public class DrawHelperUtils {
    /**
     * ������㡢�յ� ���ꡢ��͹��Path��
     * ���Զ����ư�͹�İ�Բ��
     *
     * @param start �������
     * @param end   �յ�����
     * @param path  ��Բ����������path��
     * @param outer �Ƿ�͹��Բ
     */
    public static void drawPartCircle(PointF start, PointF end, Path path, boolean outer) {
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
