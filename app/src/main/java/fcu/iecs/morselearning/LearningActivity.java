package fcu.iecs.morselearning;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LearningActivity extends AppCompatActivity {
    private ImageView ivBack;
    private ImageView ivHome;
    private Button btnIntro;
    private Button btnCh1;
    private Button btnCh2;
    private Button btnCh3;
    private Button btnCh4;
    private Button btnCh5;
    private Button btnCh6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learning);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        ivBack = findViewById(R.id.iv_back_learning);
        ivHome = findViewById(R.id.iv_home_learning);
        btnCh1 = findViewById(R.id.btn_ch1);
        btnCh2 = findViewById(R.id.btn_ch2);
        btnCh3 = findViewById(R.id.btn_ch3);
        btnCh4 = findViewById(R.id.btn_ch4);
        btnCh5 = findViewById(R.id.btn_ch5);
        btnCh6 = findViewById(R.id.btn_ch6);
        btnIntro = findViewById(R.id.btn_intro);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}