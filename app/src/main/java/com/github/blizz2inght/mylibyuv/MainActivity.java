package com.github.blizz2inght.mylibyuv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.nothing.yuvutils.YuvUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    // Used to load the 'native-lib' library on application startup.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(YuvUtils.stringFromJNI());
        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.ai);
        int count = bitmap.getByteCount();
        ByteBuffer buf = ByteBuffer.allocateDirect(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(buf);
        byte[] array = buf.array();
        byte[] bytes = YuvUtils.ARGB2ABGR(array, bitmap.getWidth(), bitmap.getHeight());
        Log.i(TAG, "useAppContext: " + bytes.length);
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        bitmap.copyPixelsFromBuffer(wrap);
        File dir = getFilesDir();

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

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

}
