package com.kishor.countdown;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kishor.countdown.widget.CircleCountDownView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected EditText etTime;
    protected CircleCountDownView countDownView;
    protected Button startTimerBt, cancelTimerBt;

    int progress;
    int endTime;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTime = (EditText) findViewById(R.id.et_total_time);
        countDownView = (CircleCountDownView) findViewById(R.id.circle_count_down_view);
        startTimerBt = (Button) findViewById(R.id.startTimer);
        cancelTimerBt = (Button) findViewById(R.id.cancleTimer);

        // set click listeners
        startTimerBt.setOnClickListener(this);
        cancelTimerBt.setOnClickListener(this);
    }

    protected void startCountDown(final View view) {

        String timeInterval = etTime.getText().toString();
        if (TextUtils.isEmpty(timeInterval)) {
            Toast.makeText(this, "Please enter end time in Edit text.", Toast.LENGTH_SHORT).show();
        } else {
            etTime.getText().clear();
            view.setVisibility(View.GONE); // hide button
            countDownView.setVisibility(View.VISIBLE); // show progress view
            cancelTimerBt.setVisibility(View.VISIBLE); // show cancel button

            progress = 1;
            endTime = Integer.parseInt(timeInterval); // up to finish time

            countDownTimer = new CountDownTimer(endTime * 1000 /*finishTime**/, 1000 /*interval**/) {
                @Override
                public void onTick(long millisUntilFinished) {
                    countDownView.setProgress(progress, endTime);
                    progress = progress + 1;
                }

                @Override
                public void onFinish() {
                    countDownView.setProgress(progress, endTime);
                    view.setVisibility(View.VISIBLE);
                    cancelTimerBt.setVisibility(View.GONE);
                }
            };
            countDownTimer.start(); // start timer

            // hide softkeyboard
            View currentFocus = this.getCurrentFocus();
            if (currentFocus != null) {
                InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        }
    }

    public void stopCountDown(View view) {

        countDownView.setProgress(endTime, endTime);
        countDownTimer.cancel();
        cancelTimerBt.setVisibility(View.GONE);
        startTimerBt.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startTimer:
                startCountDown(view);
                break;
            case R.id.cancleTimer:
                stopCountDown(view);
                break;
        }
    }
}
