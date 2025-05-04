package fcu.iecs.morselearning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.gridlayout.widget.GridLayout;

import fcu.iecs.morselearning.model.MorseCode;

public class DictionaryActivity extends AppCompatActivity {
    private ImageView ivBack;
    private ImageView ivHome;
    private GridLayout gridLetter;
    private GridLayout gridNumber;

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
        gridLetter = findViewById(R.id.grid_letter);
        gridNumber = findViewById(R.id.grid_number);

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

        LayoutInflater inflater = LayoutInflater.from(this);

        for (String letter : MorseCode.letterList) {
            View itemView = inflater.inflate(R.layout.item_dictionary, gridLetter, false);
            TextView tvCharacter = itemView.findViewById(R.id.tv_character);
            TextView tvCode = itemView.findViewById(R.id.tv_code);
            tvCharacter.setText(letter);
            tvCode.setText(MorseCode.encode(letter));
            gridLetter.addView(itemView);
        }

        for (String number : MorseCode.numberList) {
            View itemView = inflater.inflate(R.layout.item_dictionary, gridNumber, false);
            TextView tvCharacter = itemView.findViewById(R.id.tv_character);
            TextView tvCode = itemView.findViewById(R.id.tv_code);
            tvCharacter.setText(number);
            tvCode.setText(MorseCode.encode(number));
            gridNumber.addView(itemView);
        }
    }
}