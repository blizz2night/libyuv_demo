package com.nothing.yuvutils;

import java.nio.ByteBuffer;

public class YuvUtils {
    static {
        System.loadLibrary("yuvutils");
    }

    public native static String stringFromJNI();
    public native static byte[] ARGB2ABGR(byte[] array, int width, int height);
}
