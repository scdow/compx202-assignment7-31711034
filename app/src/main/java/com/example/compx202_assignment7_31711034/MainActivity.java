package com.example.compx202_assignment7_31711034;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // Create a custom view class extending View
    public class GraphicView extends View {
        private int x;
        private int y;
        private int radius;
        private Paint paint;

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

            x-=2;
            y-=2;

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
    }
}
