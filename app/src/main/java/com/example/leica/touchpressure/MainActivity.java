package com.example.leica.touchpressure;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView mEditTextPower;  // pressure , size , power = pressure/size
    MultiTouchView mSurface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextPower = (TextView)findViewById(R.id.editText_Power);

        mSurface = (MultiTouchView)findViewById(R.id.Tview);
        mSurface.setTextView((TextView)findViewById(R.id.textView4));
    }

}