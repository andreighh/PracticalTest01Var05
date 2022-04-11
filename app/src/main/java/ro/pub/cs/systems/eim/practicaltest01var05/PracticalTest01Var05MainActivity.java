package ro.pub.cs.systems.eim.practicaltest01var05;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {

    private int numberOfClicks = 0;
    private EditText editText;
    private Button topLeft, topRight, center, bottomLeft, bottomRight;

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
            }
        }
    }

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

        editText.setText("");
        topLeft.setOnClickListener(buttonClickListener);
        topRight.setOnClickListener(buttonClickListener);
        center.setOnClickListener(buttonClickListener);
        bottomLeft.setOnClickListener(buttonClickListener);
        bottomRight.setOnClickListener(buttonClickListener);
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
}