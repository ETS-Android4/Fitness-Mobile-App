package com.example.fitgain;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;


import androidx.annotation.Nullable;

//import java.util.logging.Handler;

public class Typewriter extends androidx.appcompat.widget.AppCompatTextView {

    private CharSequence text;
    private int index;
    private long delay = 150;

    public Typewriter(Context context) {
        super(context);
    }

    public Typewriter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Handler handler = new Handler();

    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(text.subSequence(0,index++));

            if(index < text.length()){
                handler.postDelayed(characterAdder, delay);
            }

        }
    };

    public void animateText(CharSequence myTxt){
        text = myTxt;
        index = 0;

        handler.removeCallbacks(characterAdder);

        handler.postDelayed(characterAdder, delay);
    }

    public void setCharacterDelay(long m){
        delay = m;

    }

}
