package cz.ami.android.wallpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;

/**
 *
 * @author Petr Halaska
 */

public class AMIWallpaper extends WallpaperService {

    public static final String SHARED_PREFS_NAME = "settings";
    Context context;
    long l;
    long time;

    @Override
    public WallpaperService.Engine onCreateEngine() {
        this.context = this;
        return new MyWallpaperEngine(this);
    }

    class MyWallpaperEngine extends WallpaperService.Engine implements SharedPreferences.OnSharedPreferenceChangeListener {

        private final Handler handler = new Handler();
        private SharedPreferences mPreferences;
        private String mShape = "5";

        private final Runnable drawRunner = new Runnable() {
            public void run() {
                draw();
            }
        };

        public Bitmap[] image = new Bitmap[2];
        boolean visible;
        int i = 0;

        public MyWallpaperEngine(WallpaperService ws) {
            this.mPreferences = AMIWallpaper.this.getSharedPreferences(AMIWallpaper.SHARED_PREFS_NAME, 0);
            this.mPreferences.registerOnSharedPreferenceChangeListener(MyWallpaperEngine.this);
            onSharedPreferenceChanged(this.mPreferences, null);

            this.image[0] = BitmapFactory.decodeResource(getResources(), R.drawable.image_1);
            this.image[1] = BitmapFactory.decodeResource(getResources(), R.drawable.image_2);

            this.handler.post(this.drawRunner);
        }

        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                this.handler.post(this.drawRunner);
            } else {
                this.handler.removeCallbacks(this.drawRunner);
            }
        }

        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible = false;
            this.handler.removeCallbacks(this.drawRunner);
        }

        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            this.mShape = sharedPreferences.getString("slide_shape", "5");
            AMIWallpaper.this.l = Long.parseLong(this.mShape);
            AMIWallpaper.this.time = AMIWallpaper.this.l * 1000;
        }

        private void draw() {
            SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    if (this.i != this.image.length) {
                        DisplayMetrics metrics = AMIWallpaper.this.context.getResources().getDisplayMetrics();
                        int width = metrics.widthPixels << 1;
                        int width1 = width / 2;
                        canvas.drawBitmap(Bitmap.createScaledBitmap(this.image[this.i], width1, metrics.heightPixels, false), 0.0f, 0.0f, null);
                        this.i++;
                    } else if (this.i == this.image.length) {
                        this.i = 0;
                    }
                }
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
                this.handler.removeCallbacks(this.drawRunner);
                if (this.visible) {
                    this.handler.postDelayed(this.drawRunner, AMIWallpaper.this.time);
                }
            } catch (Throwable th) {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

}
