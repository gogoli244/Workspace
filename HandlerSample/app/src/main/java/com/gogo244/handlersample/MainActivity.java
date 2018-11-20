package com.gogo244.handlersample;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "HandlerSample";

    Button rotateButton;
    TextView mainTextView;
    TextView rotateTextView;

    boolean isRotating = false;

    final String runnableName = "SEND_ROTATE_MESSAGE_RUNNABLE";
    final String[] rotates = {"\\", "-", "/", "|"};

    // Create thread pool for rotating runnables
    private final ExecutorService pool = Executors.newSingleThreadScheduledExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rotateButton = findViewById(R.id.rotate_btn);
        mainTextView = findViewById(R.id.main_tv);
        rotateTextView = findViewById(R.id.rotate_tv);

        // Get handler for UI widgets
        final Handler uiHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                rotateTextView.setText(rotates[msg.what]);
            }
        };

        // Set up a listener to trigger rotate runnable
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRotating) {
                    mainTextView.setText(R.string.stopped);
                } else {
                    mainTextView.setText(R.string.rotating);
                }
                isRotating = !isRotating;
                SendRotateMessageRunnable runnable = new SendRotateMessageRunnable(runnableName, uiHandler);
                pool.execute(runnable);
            }
        });


    }

    private class SendRotateMessageRunnable implements Runnable {

        String name;
        Handler uiHandler;
        public SendRotateMessageRunnable(String name, Handler uiHandler) {
            this.name = name;
            this.uiHandler = uiHandler;
        }

        @Override
        public void run() {
            int flag = 0;
            while (isRotating) {
                Message msg = uiHandler.obtainMessage(flag);
                msg.sendToTarget();
                flag = (flag + 1) % rotates.length;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Log.e(TAG, "exception: " + e);
                }
            }
        }
    }


}
