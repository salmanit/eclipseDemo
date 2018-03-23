package widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import java.util.Random;
import com.charlie.demo0108.R;

/**
 * ���ܣ��¶��㻬����֤���View
 * ���ߣ�zhangxutong
 * ���䣺mcxtzhang@163.com
 * ��ҳ��http://blog.csdn.net/zxt0601
 * ʱ�䣺 2016/11/14.
 */

public class SwipeCaptchaView extends ImageView {
    private static final String TAG = "zxt/" + SwipeCaptchaView.class.getName();
    //�ؼ��Ŀ��
    protected int mWidth;
    protected int mHeight;

    //��֤�뻬��Ŀ��
    private int mCaptchaWidth;
    private int mCaptchaHeight;
    //��֤������Ͻ�(���)��x y
    private int mCaptchaX;
    private int mCaptchaY;
    private Random mRandom;
    private Paint mPaint;
    //��֤�� ��Ӱ����ͼ��Path
    private Path mCaptchaPath;
    private PorterDuffXfermode mPorterDuffXfermode;


    //�Ƿ���ƻ��飨��֤ʧ����˸�����ã�
    private boolean isDrawMask;
    //����Bitmap
    private Bitmap mMaskBitmap;
    private Paint mMaskPaint;
    //���ڻ�����Ӱ��Paint
    private Paint mMaskShadowPaint;
    private Bitmap mMaskShadowBitmap;
    //�����λ��
    private int mDragerOffset;

    //�Ƿ�����֤ģʽ������֤�ɹ��� Ϊfalse���������Ϊtrue
    private boolean isMatchMode;
    //��֤���������ֵ
    private float mMatchDeviation;
    //��֤ʧ�ܵ���˸����
    private ValueAnimator mFailAnim;
    //��֤�ɹ��İ׹�һ������
    private boolean isShowSuccessAnim;
    private ValueAnimator mSuccessAnim;
    private Paint mSuccessPaint;//����
    private int mSuccessAnimOffset;//������offset
    private Path mSuccessPath;//�ɹ����� ƽ���ı���Path


    public SwipeCaptchaView(Context context) {
        this(context, null);
    }

    public SwipeCaptchaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeCaptchaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        int defaultSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 36, getResources().getDisplayMetrics());
        mCaptchaHeight = defaultSize;
        mCaptchaWidth = defaultSize;
        mMatchDeviation = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SwipeCaptchaView, defStyleAttr, 0);
        int n = ta.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.SwipeCaptchaView_captchaHeight) {
                mCaptchaHeight = (int) ta.getDimension(attr, defaultSize);
            } else if (attr == R.styleable.SwipeCaptchaView_captchaWidth) {
                mCaptchaWidth = (int) ta.getDimension(attr, defaultSize);
            } else if (attr == R.styleable.SwipeCaptchaView_matchDeviation) {
                mMatchDeviation = ta.getDimension(attr, mMatchDeviation);
            }
        }
        ta.recycle();


        mRandom = new Random(System.nanoTime());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(0x77000000);
        //mPaint.setStyle(Paint.Style.STROKE);
        // ���û��������˾�
        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));

        //��������
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        mMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        // ʵ������Ӱ����
        mMaskShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mMaskShadowPaint.setColor(Color.BLACK);
/*        mMaskShadowPaint.setStrokeWidth(50);
        mMaskShadowPaint.setTextSize(50);
        mMaskShadowPaint.setStyle(Paint.Style.FILL_AND_STROKE);*/
        mMaskShadowPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));

        mCaptchaPath = new Path();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        //�������� ���õ����
        createMatchAnim();

        post(new Runnable() {
            @Override
            public void run() {
                createCaptcha();
            }
        });
    }

    //��֤������ʼ������
    private void createMatchAnim() {
        mFailAnim = ValueAnimator.ofFloat(0, 1);
        mFailAnim.setDuration(100)
                .setRepeatCount(4);
        mFailAnim.setRepeatMode(ValueAnimator.REVERSE);
        //ʧ�ܵ�ʱ������һ������ ������ ����-��ʾ -���� -��ʾ
        mFailAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onCaptchaMatchCallback.matchFailed(SwipeCaptchaView.this);
            }
        });
        mFailAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedFraction();
                Log.d(TAG, "onAnimationUpdate: " + animatedValue);
                if (animatedValue < 0.5f) {
                    isDrawMask = false;
                } else {
                    isDrawMask = true;
                }
                invalidate();
            }
        });

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        mSuccessAnim = ValueAnimator.ofInt(mWidth + width, 0);
        mSuccessAnim.setDuration(500);
//        mSuccessAnim.setInterpolator(new FastOutLinearInInterpolator());
        mSuccessAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSuccessAnimOffset = (int) animation.getAnimatedFraction();
                invalidate();
            }
        });
        mSuccessAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isShowSuccessAnim = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onCaptchaMatchCallback.matchSuccess(SwipeCaptchaView.this);
                isShowSuccessAnim = false;
                isMatchMode = false;
            }
        });
        mSuccessPaint = new Paint();
        mSuccessPaint.setShader(new LinearGradient(0, 0, width/2*3, mHeight, new int[]{
                0x00ffffff, 0x88ffffff}, new float[]{0,0.5f},
                Shader.TileMode.MIRROR));
        //ģ�¶��� ��һ��ƽ���ı��ι�����ȥ
        mSuccessPath = new Path();
        mSuccessPath.moveTo(0, 0);
        mSuccessPath.rLineTo(width, 0);
        mSuccessPath.rLineTo(width / 2, mHeight);
        mSuccessPath.rLineTo(-width, 0);
        mSuccessPath.close();
    }

    //������֤������
    public void createCaptcha() {
        if (getDrawable() != null) {
            resetFlags();
            createCaptchaPath();
            craeteMask();
            invalidate();
        }
    }

    //����һЩflasg�� ������֤ģʽ
    private void resetFlags() {
        isMatchMode = true;
    }

    //������֤��Path
    private void createCaptchaPath() {
        //ԭ�������������gap���������� ���/3 Ч���ȽϺã�
        int gap = mRandom.nextInt(mCaptchaWidth / 2);
        gap = mCaptchaWidth / 3;

        //���������֤����Ӱ���Ͻ� x y �㣬
        mCaptchaX = mRandom.nextInt(mWidth - mCaptchaWidth - gap);
        mCaptchaY = mRandom.nextInt(mHeight - mCaptchaHeight - gap);
        Log.d(TAG, "createCaptchaPath() called mWidth:" + mWidth + ", mHeight:" + mHeight + ", mCaptchaX:" + mCaptchaX + ", mCaptchaY:" + mCaptchaY);

        mCaptchaPath.reset();
        mCaptchaPath.lineTo(0, 0);


        //�����Ͻǿ�ʼ ����һ�����������Ӱ
        mCaptchaPath.moveTo(mCaptchaX, mCaptchaY);//���Ͻ�


/*        mCaptchaPath.lineTo(mCaptchaX + gap, mCaptchaY);
        //������͹ �����Ƕ��Path �޷��պϣ���ֱ������
        int r = mCaptchaWidth / 2 - gap;
        RectF oval = new RectF(mCaptchaX + gap, mCaptchaY - (r), mCaptchaX + gap + r * 2, mCaptchaY + (r));
        mCaptchaPath.arcTo(oval, 180, 180);*/

        mCaptchaPath.lineTo(mCaptchaX + gap, mCaptchaY);
        //drawһ�������͹��Բ
        DrawHelperUtils.drawPartCircle(new PointF(mCaptchaX + gap, mCaptchaY),
                new PointF(mCaptchaX + gap * 2, mCaptchaY),
                mCaptchaPath, mRandom.nextBoolean());


        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth, mCaptchaY);//���Ͻ�
        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth, mCaptchaY + gap);
        //drawһ�������͹��Բ
        DrawHelperUtils.drawPartCircle(new PointF(mCaptchaX + mCaptchaWidth, mCaptchaY + gap),
                new PointF(mCaptchaX + mCaptchaWidth, mCaptchaY + gap * 2),
                mCaptchaPath, mRandom.nextBoolean());


        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth, mCaptchaY + mCaptchaHeight);//���½�
        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth - gap, mCaptchaY + mCaptchaHeight);
        //drawһ�������͹��Բ
        DrawHelperUtils.drawPartCircle(new PointF(mCaptchaX + mCaptchaWidth - gap, mCaptchaY + mCaptchaHeight),
                new PointF(mCaptchaX + mCaptchaWidth - gap * 2, mCaptchaY + mCaptchaHeight),
                mCaptchaPath, mRandom.nextBoolean());


        mCaptchaPath.lineTo(mCaptchaX, mCaptchaY + mCaptchaHeight);//���½�
        mCaptchaPath.lineTo(mCaptchaX, mCaptchaY + mCaptchaHeight - gap);
        //drawһ�������͹��Բ
        DrawHelperUtils.drawPartCircle(new PointF(mCaptchaX, mCaptchaY + mCaptchaHeight - gap),
                new PointF(mCaptchaX, mCaptchaY + mCaptchaHeight - gap * 2),
                mCaptchaPath, mRandom.nextBoolean());


        mCaptchaPath.close();

/*        RectF oval = new RectF(mCaptchaX + gap, mCaptchaY - (r), mCaptchaX + gap + r * 2, mCaptchaY + (r));
        mCaptchaPath.addArc(oval, 180,180);
        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth, mCaptchaY);
        //���Ļ����鷳һ�㣬Ҫ���ö��move
        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth, mCaptchaY + gap);
        oval = new RectF(mCaptchaX + mCaptchaWidth - r, mCaptchaY + gap, mCaptchaX + mCaptchaWidth + r, mCaptchaY + gap + r * 2);
        mCaptchaPath.addArc(oval, 90, 180);
        mCaptchaPath.moveTo(mCaptchaX + mCaptchaWidth, mCaptchaY + gap + r * 2);*//*
        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth, mCaptchaY + mCaptchaHeight);
        mCaptchaPath.lineTo(mCaptchaX, mCaptchaY + mCaptchaHeight);
        mCaptchaPath.close();*/
    }

    //���ɻ���
    private void craeteMask() {
        mMaskBitmap = getMaskBitmap(((BitmapDrawable) getDrawable()).getBitmap(), mCaptchaPath);
        //������Ӱ
        mMaskShadowBitmap = mMaskBitmap.extractAlpha();
        //�϶���λ������
        mDragerOffset = 0;
        //isDrawMask  ����ʧ����˸������
        isDrawMask = true;
    }

    //��ͼ
    private Bitmap getMaskBitmap(Bitmap mBitmap, Path mask) {
        //�Կؼ���� createһ��bitmap
        Bitmap tempBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Log.e(TAG, " getMaskBitmap: width:" + mBitmap.getWidth() + ",  height:" + mBitmap.getHeight());
        Log.e(TAG, " View: width:" + mWidth + ",  height:" + mHeight);
        //�Ѵ�����bitmap��Ϊ����
        Canvas mCanvas = new Canvas(tempBitmap);
        //�о�� ���޷����,���Ի���XFermode�ķ�����
        //mCanvas.clipPath(mask);
        // �����
        mCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        //�����������ֵ�Բ��
        mCanvas.drawPath(mask, mMaskPaint);
        //��������ģʽ(ͼ����ģʽ)
        mMaskPaint.setXfermode(mPorterDuffXfermode);
        //�￼�ǵ�scaleType�����أ�Ҫ��Matrix��Bitmap��������
        mCanvas.drawBitmap(mBitmap, getImageMatrix(), mMaskPaint);
        mMaskPaint.setXfermode(null);
        return tempBitmap;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //�̳���ImageView������Bitmap��ImageView�Ѿ�������draw���ˡ�
        //��ֻ��������ƺ���֤����صĲ��֣�

        //�Ƿ�����֤ģʽ������֤�ɹ��� Ϊfalse���������Ϊtrue
        if (isMatchMode) {
            //���Ȼ�����֤����Ӱ
            if (mCaptchaPath != null) {
                canvas.drawPath(mCaptchaPath, mPaint);
            }
            //���ƻ���
            // isDrawMask  ����ʧ����˸������
            if (null != mMaskBitmap && null != mMaskShadowBitmap && isDrawMask) {
                // �Ȼ�����Ӱ
                canvas.drawBitmap(mMaskShadowBitmap, -mCaptchaX + mDragerOffset, 0, mMaskShadowPaint);
                canvas.drawBitmap(mMaskBitmap, -mCaptchaX + mDragerOffset, 0, null);
            }
            //��֤�ɹ����׹�ɨ���Ķ�������һ�鶯���о�������������߿ռ�
            if (isShowSuccessAnim) {
                canvas.translate(mSuccessAnimOffset, 0);
                canvas.drawPath(mSuccessPath, mSuccessPaint);
            }
        }
    }


    /**
     * У��
     */
    public void matchCaptcha() {
        if (null != onCaptchaMatchCallback && isMatchMode) {
            //������֤�߼�����ͨ���Ƚϣ���ק�ľ��� �� ��֤�����x���ꡣ Ĭ��3dp����������֤�ɹ���
            if (Math.abs(mDragerOffset - mCaptchaX) < mMatchDeviation) {
                Log.d(TAG, "matchCaptcha() true: mDragerOffset:" + mDragerOffset + ", mCaptchaX:" + mCaptchaX);
                //matchSuccess();
                //�ɹ��Ķ���
                mSuccessAnim.start();


            } else {
                Log.e(TAG, "matchCaptcha() false: mDragerOffset:" + mDragerOffset + ", mCaptchaX:" + mCaptchaX);

                mFailAnim.start();
                //matchFailed();
            }
        }

    }

    /**
     * ������֤�뻬������,(һ��������֤ʧ��)
     */
    public void resetCaptcha() {
        mDragerOffset = 0;
        invalidate();
    }

    /**
     * ���ɻ���ֵ
     * @return
     */
    public int getMaxSwipeValue() {
        //return ((BitmapDrawable) getDrawable()).getBitmap().getWidth() - mCaptchaWidth;
        //���ؿؼ����
        return mWidth - mCaptchaWidth;
    }

    /**
     * ���õ�ǰ����ֵ
     * @param value
     */
    public void setCurrentSwipeValue(int value) {
        mDragerOffset = value;
        invalidate();
    }

    public interface OnCaptchaMatchCallback {
        void matchSuccess(SwipeCaptchaView swipeCaptchaView);

        void matchFailed(SwipeCaptchaView swipeCaptchaView);
    }

    /**
     * ��֤����֤�Ļص�
     */
    private OnCaptchaMatchCallback onCaptchaMatchCallback;

    public OnCaptchaMatchCallback getOnCaptchaMatchCallback() {
        return onCaptchaMatchCallback;
    }

    /**
     * ������֤����֤�ص�
     *
     * @param onCaptchaMatchCallback
     * @return
     */
    public SwipeCaptchaView setOnCaptchaMatchCallback(OnCaptchaMatchCallback onCaptchaMatchCallback) {
        this.onCaptchaMatchCallback = onCaptchaMatchCallback;
        return this;
    }
}