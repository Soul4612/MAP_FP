package fcu.iecs.morselearning;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    private int seconds = 0;
    private boolean running = true;
    private Handler timerHandler;

    private int currentIndex = 0;
    private final Map<Character, String> morseMap = new HashMap<>();
    private SpannableString fullSpannable;

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
        tvEssayText = findViewById(R.id.tv_essay_morse);
        etEnterMorse = findViewById(R.id.et_enter_morse);
        ibtnEnter = findViewById(R.id.ibtn_enter);
        btnLong = findViewById(R.id.btn_long);
        btnShort = findViewById(R.id.btn_short);
        ibtnDelete = findViewById(R.id.ibtn_delete);

        ibtnBackToTyping.setOnClickListener(v -> {
            finish();
        });

        ibtnMain2.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        ibtnDict.setOnClickListener(v -> showMorseDictDialog());

        initializeMorseMap();
        String selectedText = getRandomSampleSentence();
        fullSpannable = new SpannableString(selectedText);
        tvEssayText.setText(fullSpannable);

        btnShort.setOnClickListener(v -> etEnterMorse.append("."));
        btnLong.setOnClickListener(v -> etEnterMorse.append("-"));
        ibtnDelete.setOnClickListener(v -> {
            String current = etEnterMorse.getText().toString();
            if (!current.isEmpty()) {
                etEnterMorse.setText(current.substring(0, current.length() - 1));
            }
        });

        ibtnEnter.setOnClickListener(v -> {
            String inputMorse = etEnterMorse.getText().toString();

            while (currentIndex < fullSpannable.length()) {
                char ch = fullSpannable.charAt(currentIndex);
                if (Character.isLetter(ch)) break;
                currentIndex++;
            }

            if (currentIndex >= fullSpannable.length()) return;

            char currentChar = Character.toUpperCase(fullSpannable.charAt(currentIndex));
            String correctMorse = morseMap.getOrDefault(currentChar, "");

            if (inputMorse.equals(correctMorse)) {
                fullSpannable.setSpan(new ForegroundColorSpan(Color.GREEN), currentIndex, currentIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                currentIndex++;
                etEnterMorse.setText("");
            } else {
                fullSpannable.setSpan(new ForegroundColorSpan(Color.RED), currentIndex, currentIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                etEnterMorse.setText("");
            }

            tvEssayText.setText(fullSpannable);

            int tempIndex = currentIndex;
            while (tempIndex < fullSpannable.length()) {
                char ch = fullSpannable.charAt(tempIndex);
                if (Character.isLetter(ch)) {
                    return;
                }
                tempIndex++;
            }

            stopTimerAndShowDialog();
        });

        startTimer();
    }

    private void initializeMorseMap() {
        morseMap.put('A', ".-"); morseMap.put('B', "-..."); morseMap.put('C', "-.-.");
        morseMap.put('D', "-.."); morseMap.put('E', "."); morseMap.put('F', "..-.");
        morseMap.put('G', "--."); morseMap.put('H', "...."); morseMap.put('I', "..");
        morseMap.put('J', ".---"); morseMap.put('K', "-.-"); morseMap.put('L', ".-..");
        morseMap.put('M', "--"); morseMap.put('N', "-."); morseMap.put('O', "---");
        morseMap.put('P', ".--."); morseMap.put('Q', "--.-"); morseMap.put('R', ".-.");
        morseMap.put('S', "..."); morseMap.put('T', "-"); morseMap.put('U', "..-");
        morseMap.put('V', "...-"); morseMap.put('W', ".--"); morseMap.put('X', "-..-");
        morseMap.put('Y', "-.--"); morseMap.put('Z', "--..");
    }

    private String getRandomSampleSentence() {
        List<String> sentences = new ArrayList<>();
        sentences.add("Every bird sings when the sky turns blue.");
        sentences.add("Smart kids play chess with great passion.");
        sentences.add("Dream big and chase your bright future.");
        sentences.add("Learning to code builds brain power fast.");
        sentences.add("The sun sets beyond the calm ocean waves.");
        sentences.add("Reading books opens doors to new worlds.");
        sentences.add("Music can lift your heart and inspire joy.");
        sentences.add("Hard work beats talent every single time.");
        sentences.add("Healthy minds thrive on daily challenges.");
        sentences.add("Focus sharpens when distractions are gone.");

        return sentences.get(new Random().nextInt(sentences.size()));
    }

    private void startTimer() {
        timerHandler = new Handler();
        timerHandler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = seconds / 60;
                int secs = seconds % 60;
                String time = String.format("%02d:%02d", minutes, secs);
                tvTimer.setText(time);

                if (running) {
                    seconds++;
                }

                timerHandler.postDelayed(this, 1000);
            }
        });
    }

    private void stopTimerAndShowDialog() {
        running = false;
        if (timerHandler != null) {
            timerHandler.removeCallbacksAndMessages(null);
        }

        String finalTime = tvTimer.getText().toString();

        View view = getLayoutInflater().inflate(R.layout.typing_dialog_end, null);

        TextView tvEndTime = view.findViewById(R.id.tv_end_time);
        Button btnBackToTyping = view.findViewById(R.id.btn_back_to_typing);
        Button btnBackToHome = view.findViewById(R.id.btn_back_to_home);

        tvEndTime.setText("本次花費時間：" + finalTime);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();

        btnBackToTyping.setOnClickListener(v -> {
            startActivity(new Intent(this, TypingActivity.class));
            dialog.dismiss();
            finish();
        });

        btnBackToHome.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            dialog.dismiss();
            finish();
        });

        dialog.show();
    }

    private void showMorseDictDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.typing_dialog_dict, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        ImageButton closeBtn = view.findViewById(R.id.ibtn_close_dict);
        closeBtn.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
