package yincheng.sourcecodeinvestigate.ui.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import yincheng.sourcecodeinvestigate.R;
import yincheng.sourcecodeinvestigate.disklrucache.DiskLruCache;

/**
 * Created by yincheng on 2018/4/25/18:29.
 * github:luoyincheng
 */
public class LruCacheActivity extends AppCompatActivity {
    private ImageView imageView;
    private DiskLruCache mDiskLruCache = null;
    private File imageCacheDir;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lrucache);
        imageView = findViewById(R.id.iv_cache);
        try {
            imageCacheDir = getDiskCacheDir(this, "image");
            if (!imageCacheDir.exists()) {
                imageCacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(imageCacheDir, getAppVersionCode(this), 1, 1024 * 1024 * 100);
        } catch (IOException e) {
            e.printStackTrace();
        }


        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageToCache();
            }
        });
        findViewById(R.id.tv_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromCache();
            }
        });
        findViewById(R.id.tv_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeImageFromCache();
            }
        });

    }

    private void saveImageToCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String imageUrl = "https://wallpapersite.com/images/pages/pic_h/13426.jpg";
                    String key = hashKeyForDisk(imageUrl);
                    Log.e("wodedisklurcache", "存储的key:" + key);
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (downloadUrlToStream(imageUrl, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public File getDiskCacheDir(Context context, String dirName) {
        String cachePath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        return new File(cachePath + File.separator + dirName);
    }

    public int getAppVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection httpURLConnection = null;
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            final URL url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream(), 8 * 1024);
            bufferedOutputStream = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(b);
            }
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
            try {
                if (bufferedInputStream != null)
                    bufferedInputStream.close();
                if (bufferedOutputStream != null)
                    bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private void loadImageFromCache() {
        try {
            String imageUrl = "https://wallpapersite.com/images/pages/pic_h/13426.jpg";
            String key = hashKeyForDisk(imageUrl);
            Log.e("wodedisklurcache", "加载的key:" + key);
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {
                InputStream is = snapShot.getInputStream(0);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imageView.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeImageFromCache() {
        try {
            String imageUrl = "https://wallpapersite.com/images/pages/pic_h/13426.jpg";
            String key = hashKeyForDisk(imageUrl);
            mDiskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
