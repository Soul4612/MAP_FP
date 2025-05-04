package fcu.iecs.morselearning;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TypingMorseToTextActivity extends AppCompatActivity {
    private ImageButton ibtnBackToTyping2;
    private TextView tvTimer;
    private ImageButton ibtnMain3;
    private ImageButton ibtnDict;
    private TextView tvEssayMorse;
    private EditText etEnterText;
    private ImageButton ibtnEnter2;

    private final Map<Character, String> morseMap = new HashMap<>();
    private int seconds = 0;
    private boolean running = true;
    private Handler timerHandler;
    private String englishText;
    private SpannableString spannableMorse;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_typing_morse_to_text);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ibtnBackToTyping2 = findViewById(R.id.ibtn_back_to_typing2);
        tvTimer = findViewById(R.id.tv_timer);
        ibtnMain3 = findViewById(R.id.ibtn_main3);
        ibtnDict = findViewById(R.id.ibtn_dict);
        tvEssayMorse = findViewById(R.id.tv_essay_morse);
        etEnterText = findViewById(R.id.et_enter_text);
        ibtnEnter2 = findViewById(R.id.ibtn_enter2);

        ibtnBackToTyping2.setOnClickListener(v -> {
            finish();
        });

        ibtnMain3.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        ibtnDict.setOnClickListener(v -> showMorseDictDialog());

        initializeMorseMap();
        englishText = getRandomSampleSentence();
        String morseText = convertToMorse(englishText);
        spannableMorse = new SpannableString(morseText);
        tvEssayMorse.setText(spannableMorse);

        ibtnEnter2.setOnClickListener(v -> {
            while (currentIndex < englishText.length()) {
                char ch = englishText.charAt(currentIndex);
                if (Character.isLetter(ch)) break;
                currentIndex++;
            }

            if (currentIndex >= englishText.length()) {
                stopTimerAndShowDialog();
                return;
            }

            char correctChar = englishText.charAt(currentIndex);
            String expectedMorse = morseMap.getOrDefault(Character.toUpperCase(correctChar), "");
            String userInput = etEnterText.getText().toString().trim();

            int morseStart = findMorseStartIndex(currentIndex);
            int morseEnd = morseStart + expectedMorse.length();

            if (!userInput.isEmpty() && Character.toUpperCase(userInput.charAt(0)) == Character.toUpperCase(correctChar)) {
                spannableMorse.setSpan(new ForegroundColorSpan(Color.GREEN), morseStart, morseEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                currentIndex++;
            } else {
                spannableMorse.setSpan(new ForegroundColorSpan(Color.RED), morseStart, morseEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            etEnterText.setText("");
            tvEssayMorse.setText(spannableMorse);

            boolean allDone = true;
            for (int i = currentIndex; i < englishText.length(); i++) {
                if (Character.isLetter(englishText.charAt(i))) {
                    allDone = false;
                    break;
                }
            }
            if (allDone) {
                stopTimerAndShowDialog();
            }
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

    private String convertToMorse(String text) {
        StringBuilder morseBuilder = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            if (morseMap.containsKey(c)) {
                morseBuilder.append(morseMap.get(c)).append(" ");
            } else if (c == ' ') {
                morseBuilder.append("/ ");
            } else {
                morseBuilder.append(c).append(" ");
            }
        }
        return morseBuilder.toString().trim();
    }

    private int findMorseStartIndex(int index) {
        int count = 0;
        for (int i = 0; i < index; i++) {
            char c = Character.toUpperCase(englishText.charAt(i));
            if (morseMap.containsKey(c)) {
                count += morseMap.get(c).length() + 1;
            } else if (c == ' ') {
                count += 2;
            } else {
                count += 2;
            }
        }
        return count;
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