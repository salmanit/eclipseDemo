package captcha;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;

public abstract class CaptchaStrategy {
	private Context mContext;
	
    public CaptchaStrategy(Context ctx) {
        this.mContext = ctx;
    }
    public int blockWidth;
    public int blockHeight;
	//��ͼ����״
	public abstract Path getBlockPath();
	
	//ȱ����Ӱ�Ļ���
	public abstract Paint  getBlockPaint();
	
	//�ƶ��Ļ���Ļ���
	public abstract Paint getBlockBitmapPaint();
	
	
	//����ı߽磬��������
	public abstract Paint getBlockShaderPaint();
	
	
	public int dp2px(int dp) {
		return (int) (mContext.getResources().getDisplayMetrics().density*dp);
	}
}
