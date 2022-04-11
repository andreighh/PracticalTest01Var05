package ro.pub.cs.systems.eim.practicaltest01var05;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {

    private int numberOfClicks = 0;
    private int serviceStatus = Constants.SERVICE_STOPPED;
    private EditText editText;
    private Button topLeft, topRight, center, bottomLeft, bottomRight, navigateToSecondary;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String text = editText.getText().toString();
            switch (view.getId()) {
                case R.id.top_left:
                    if (text.equals("")) {
                        editText.setText(topLeft.getText().toString());
                    } else {
                        editText.setText(String.join(", ", text, topLeft.getText().toString()));
                    }
                    numberOfClicks++;
                    break;
                case R.id.top_right:
                    if (text.equals("")) {
                        editText.setText(topRight.getText().toString());
                    } else {
                        editText.setText(String.join(", ", text, topRight.getText().toString()));
                    }
                    numberOfClicks++;
                    break;
                case R.id.center:
                    if (text.equals("")) {
                        editText.setText(center.getText().toString());
                    } else {
                        editText.setText(String.join(", ", text, center.getText().toString()));
                    }
                    numberOfClicks++;
                    break;
                case R.id.bottom_left:
                    if (text.equals("")) {
                        editText.setText(bottomLeft.getText().toString());
                    } else {
                        editText.setText(String.join(", ", text, bottomLeft.getText().toString()));
                    }
                    numberOfClicks++;
                    break;
                case R.id.bottom_right:
                    if (text.equals("")) {
                        editText.setText(bottomRight.getText().toString());
                    } else {
                        editText.setText(String.join(", ", text, bottomRight.getText().toString()));
                    }
                    numberOfClicks++;
                    break;
                case R.id.navigate_to_secondary_activity_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05SecondaryActivity.class);
                    intent.putExtra(Constants.NUMBER_OF_CLICKS, numberOfClicks);
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }

            if (numberOfClicks > Constants.THRESHOLD && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05Service.class);
                intent.putExtra(Constants.TEXT_VIEW, text);
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }
    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_main);

        editText = (EditText) findViewById(R.id.edit_text);
        topLeft = findViewById(R.id.top_left);
        topRight = findViewById(R.id.top_right);
        center = findViewById(R.id.center);
        bottomLeft = findViewById(R.id.bottom_left);
        bottomRight = findViewById(R.id.bottom_right);
        navigateToSecondary = findViewById(R.id.navigate_to_secondary_activity_button);


        editText.setText("");
        topLeft.setOnClickListener(buttonClickListener);
        topRight.setOnClickListener(buttonClickListener);
        center.setOnClickListener(buttonClickListener);
        bottomLeft.setOnClickListener(buttonClickListener);
        bottomRight.setOnClickListener(buttonClickListener);
        navigateToSecondary.setOnClickListener(buttonClickListener);

        intentFilter.addAction(Constants.ACTION);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(Constants.NUMBER_OF_CLICKS, numberOfClicks);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.NUMBER_OF_CLICKS)) {
            Toast.makeText(this, "" + savedInstanceState.getInt(Constants.NUMBER_OF_CLICKS), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
        editText.setText("");
        numberOfClicks = 0;
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var05Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}