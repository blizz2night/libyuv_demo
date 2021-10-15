package com.github.blizz2inght.mylibyuv;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.Assert.*;

import com.nothing.yuvutils.YuvUtils;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "ExampleInstrumentedTest";



    @Test
    public void ARGB2ABGR() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.github.blizz2inght.mylibyuv", appContext.getPackageName());
        Resources res = appContext.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.ai);
        int count = bitmap.getByteCount();
        ByteBuffer buf = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(buf);
        byte[] array = buf.array();
        byte[] bytes = YuvUtils.ARGB2ABGR(array, bitmap.getWidth(), bitmap.getHeight());
        Log.i(TAG, "useAppContext: " + bytes.length);
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        bitmap.copyPixelsFromBuffer(wrap);
        File dir = appContext.getDataDir();

        File file = new File(dir, "abc.jpeg");
        try {
            boolean newFile = file.createNewFile();
        } catch (IOException e) {
            Log.e(TAG, "useAppContext: ", e);
        }

        try (FileOutputStream fout = new FileOutputStream(file)){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
