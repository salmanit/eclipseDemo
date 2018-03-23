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
	//抠图的形状
	public abstract Path getBlockPath();
	
	//缺块阴影的画笔
	public abstract Paint  getBlockPaint();
	
	//移动的滑块的画笔
	public abstract Paint getBlockBitmapPaint();
	
	
	//滑块的边界，比如虚线
	public abstract Paint getBlockShaderPaint();
	
	
	public int dp2px(int dp) {
		return (int) (mContext.getResources().getDisplayMetrics().density*dp);
	}
}
