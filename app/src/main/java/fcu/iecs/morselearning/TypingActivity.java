package fcu.iecs.morselearning;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TypingActivity extends AppCompatActivity {
    private ImageButton ibtnBackToMain;
    private ImageButton ibtnMain;
    private Button btnTextToMorse;
    private Button btnMorseToText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_typing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ibtnBackToMain = findViewById(R.id.ibtn_back_to_main);
        ibtnMain = findViewById(R.id.ibtn_main);
        btnTextToMorse = findViewById(R.id.btn_text_to_morse);
        btnMorseToText = findViewById(R.id.btn_morse_to_text);

        ibtnBackToMain.setOnClickListener(v -> {
            finish();
        });

        ibtnMain.setOnClickListener(v -> {
            finish();
        });

        btnTextToMorse.setOnClickListener(v -> showDialog("明轉密模式"));
        btnMorseToText.setOnClickListener(v -> showDialog("密轉明模式"));
    }

    // 顯示自訂 Dialog
    private void showDialog(String modeName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.typing_dialog_go, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);

        Button btnGo = dialogView.findViewById(R.id.btn_go);
        btnGo.setOnClickListener(v -> {
            //Toast.makeText(this, "開始 " + modeName + "！", Toast.LENGTH_SHORT).show();
            // 根據模式跳轉到不同的 Activity
            Intent intent;
            if (modeName.equals("明轉密模式")) {
                intent = new Intent(TypingActivity.this, TypingTextToMorseActivity.class);
            } else {
                intent = new Intent(TypingActivity.this, TypingMorseToTextActivity.class);
            }
            dialog.dismiss();
            startActivity(intent);
            finish();
        });

        dialog.show();
    }
}