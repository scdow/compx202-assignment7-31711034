package com.example.compx202_assignment7_31711034;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    SensorManager sensorMgr;
    Sensor accelerometer;

    SensorEventListener accelListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            Log.i("TAG", "" + event.values[0] +
                    ", " + event.values[1] +
                    ", " + event.values[2]);
            xa = event.values[0];
            ya = event.values[1];
//            za = event.values[2];
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}
    };


    private float x,xa,xs,xmax;
    private float y,ya,ys,ymax;
//    private float z,za,zs,zmax;
    float frameTime = 2f;
    private int radius;
    private Paint paint;


    // Create a custom view class extending View
    public class GraphicView extends View {
//        private float x,xs,xmax;
//        private float y,ya,ys,ymax;
//        private float z,za,zs,zmax;
//        private int radius;
//        private Paint paint;

        public GraphicView(Context context) {
            super(context);
            radius=30;
            paint = new Paint();
            paint.setColor(getColor(R.color.colorAccent));
        }

        // Set onDraw to draw a circle
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawCircle(x,y,radius, paint);

            x=x-xa*frameTime*frameTime/2;
            y=y+ya*frameTime*frameTime/2;

//            make ball always stay on screen
            if (x > getWidth()) {
                x = getWidth();
            } else if (x < 0) {
                x = 0;
            }

            if (y > getHeight()) {
                y = getHeight();
            } else if (y < 0) {
                y = 0;
            }

//            x-=2;
//            y-=2;

            // Animate the ball using invalidate
            invalidate();
        }

        // Use onSizeChange to get information on screen size and set the ball on a different initial position
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            x=w;
            y=h;
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        // Set sticky immersive fullscreen mode
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        // Get the ConstraintLayout
        ConstraintLayout root = (ConstraintLayout)findViewById(R.id.MainView);
        // Create an instance of the custom view
        GraphicView myView = new GraphicView(this);
        // Add the customview to the ConstraintLayout
        root.addView(myView);

        PackageManager manager = getPackageManager();
        for (FeatureInfo fi : manager.getSystemAvailableFeatures()){
            Log.d("MYTAG", fi.toString());
        }
//        check Accelerometer sensor
        if (manager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER)) {
            Log.d("MYTAG", "Have accelerometer");
        }

        sensorMgr = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Log.d("TAG", "Obtained accelerometer " + accelerometer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorMgr.registerListener(accelListener, accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorMgr.unregisterListener(accelListener, accelerometer);
    }
}
