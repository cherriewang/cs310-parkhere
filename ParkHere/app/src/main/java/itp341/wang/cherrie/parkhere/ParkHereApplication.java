package itp341.wang.cherrie.parkhere;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by glarencezhao on 10/27/16.
 */

public class ParkHereApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
