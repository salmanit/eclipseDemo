package heart;

/**
 * Package com.example.administrator.testrecyclerview
 * Created by HuaChao on 2016/5/24.
 */
public class Garden {

    //鍒涘缓涓�涓殢鏈虹殑鑺辨湹
    public Bloom createRandomBloom(int x, int y) {
        //鍒涘缓涓�涓殢鏈虹殑鑺辨湹鍗婂緞
        int radius = MyUtil.randomInt(Options.minBloomRadius, Options.maxBloomRadius);
        //鍒涘缓涓�涓殢鏈虹殑鑺辨湹棰滆壊
        int color = MyUtil.randomrgba(Options.minRedColor, Options.maxRedColor, Options.minGreenColor, Options.maxGreenColor, Options.minBlueColor, Options.maxBlueColor, Options.opacity);
        //鍒涘缓闅忔満鐨勮姳鏈典腑鑺辩摚涓暟
        int petalCount = MyUtil.randomInt(Options.minPetalCount, Options.maxPetalCount);
        return createBloom(x, y, radius, color, petalCount);
    }

    //鍒涘缓鑺辨湹
    public Bloom createBloom(int x, int y, int radius, int color, int petalCount) {
        return new Bloom(new Point(x, y), radius, color, petalCount);
    }

    static class Options {
        //鐢ㄤ簬鎺у埗浜х敓闅忔満鑺辩摚涓暟鑼冨洿
        public static int minPetalCount = 8;
        public static int maxPetalCount = 15;
        //鐢ㄤ簬鎺у埗浜х敓寤堕暱绾垮�嶆暟鑼冨洿
        public static float minPetalStretch = 2f;
        public static float maxPetalStretch = 3.5f;
        //鐢ㄤ簬鎺у埗浜х敓闅忔満澧為暱鍥犲瓙鑼冨洿,澧為暱鍥犲瓙鍐冲畾鑺辩摚缁芥斁閫熷害
        public static float minGrowFactor = 1f;
        public static float maxGrowFactor = 1.1f;
        //鐢ㄤ簬鎺у埗浜х敓鑺辨湹鍗婂緞闅忔満鏁拌寖鍥�
        public static int minBloomRadius = 8;
        public static int maxBloomRadius = 10;
        //鐢ㄤ簬浜х敓闅忔満棰滆壊
        public static int minRedColor = 128;
        public static int maxRedColor = 255;
        public static int minGreenColor = 0;
        public static int maxGreenColor = 128;
        public static int minBlueColor = 0;
        public static int maxBlueColor = 128;
        //鑺辩摚鐨勯�忔槑搴�
        public static int opacity = 50;//0.1
    }
}
