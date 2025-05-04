package fcu.iecs.morselearning;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fcu.iecs.morselearning.adapter.DictionaryAdapter;
import fcu.iecs.morselearning.model.MorseCode;

public class DictionaryActivity extends AppCompatActivity {
    private ImageView ivBack;
    private ImageView ivHome;
    private RecyclerView rvLetter;
    private RecyclerView rvNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ivBack = findViewById(R.id.iv_back_dict);
        ivHome = findViewById(R.id.iv_home_dict);
        rvLetter = findViewById(R.id.rv_letter);
        rvNumber = findViewById(R.id.rv_number);

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

        rvLetter.setLayoutManager(new GridLayoutManager(this, 2));
        rvLetter.setAdapter(new DictionaryAdapter(MorseCode.letterList));
        rvNumber.setLayoutManager(new GridLayoutManager(this, 2));
        rvNumber.setAdapter(new DictionaryAdapter(MorseCode.numberList));
    }
}