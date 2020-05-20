package com.github.blizz2inght.mylibyuv;

public class YuvUtils {
    static {
        System.loadLibrary("native-lib");
    }
    public native static byte[] ARGB2ABGR(byte[] array, int width, int height);
}
