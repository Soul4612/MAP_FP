package fcu.iecs.morselearning;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TypingTextToMorseActivity extends AppCompatActivity {
    private ImageButton ibtnBackToTyping;
    private TextView tvTimer;
    private ImageButton ibtnMain2;
    private ImageButton ibtnDict;
    private TextView tvEssayText;
    private EditText etEnterMorse;
    private ImageButton ibtnEnter;
    private Button btnLong;
    private Button btnShort;
    private ImageButton ibtnDelete;

    // 計時器相關變數
    private int seconds = 0;
    private boolean running = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_typing_text_to_morse);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ibtnBackToTyping = findViewById(R.id.ibtn_back_to_typing);
        tvTimer = findViewById(R.id.tv_timer);
        ibtnMain2 = findViewById(R.id.ibtn_main2);
        ibtnDict = findViewById(R.id.ibtn_dict);
        tvEssayText = findViewById(R.id.tv_essay_text);
        etEnterMorse = findViewById(R.id.et_enter_morse);
        ibtnEnter = findViewById(R.id.ibtn_enter);
        btnLong = findViewById(R.id.btn_long);
        btnShort = findViewById(R.id.btn_short);
        ibtnDelete = findViewById(R.id.ibtn_delete);

        // 返回 TypingActivity
        ibtnBackToTyping.setOnClickListener(v -> {
            Intent intent = new Intent(this, TypingActivity.class);
            startActivity(intent);
            finish();
        });

        // 返回 MainActivity
        ibtnMain2.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // 啟動計時器
        startTimer();
    }

    // 正向計時器
    private void startTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = seconds / 60;
                int secs = seconds % 60;
                String time = String.format("%02d:%02d", minutes, secs);
                tvTimer.setText(time);

                if (running) {
                    seconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });
    }
}