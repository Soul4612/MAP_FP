package fcu.iecs.morselearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnLearningMode;
    private Button btnTypingPractice;
    private Button btnDictionary;
    private Button btnLProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnLearningMode = findViewById(R.id.btn_learning_mode);
        btnTypingPractice = findViewById(R.id.btn_typing_practice);
        btnDictionary = findViewById(R.id.btn_dictionary);
        btnLProfile = findViewById(R.id.btn_profile);

        btnLearningMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navToActivity(LearningActivity.class);
            }
        });

        btnTypingPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navToActivity(TypingActivity.class);
            }
        });

        btnDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navToActivity(DictionaryActivity.class);
            }
        });

        btnLProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navToActivity(ProfileActivity.class);
            }
        });
    }

    public void navToActivity(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
    }

}