package fcu.iecs.morselearning.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fcu.iecs.morselearning.R;
import fcu.iecs.morselearning.model.MorseCode;


public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {
    private String[] list;

    public DictionaryAdapter(String[] list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dictionary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvCharacter.setText(list[position]);
        holder.tvCode.setText(MorseCode.encode(list[position]));
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCharacter, tvCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCharacter = itemView.findViewById(R.id.tv_character);
            tvCode = itemView.findViewById(R.id.tv_code);
        }
    }
}
