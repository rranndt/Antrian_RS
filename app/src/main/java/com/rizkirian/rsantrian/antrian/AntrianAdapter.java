package com.rizkirian.rsantrian.antrian;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rizkirian.rsantrian.ui.detail_antrian.DetailAntrianActivity;
import com.rizkirian.rsantrian.R;

import java.util.List;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class AntrianAdapter extends RecyclerView.Adapter<AntrianAdapter.RecyclerViewHolder> {

    private List<Antrian> list;
    private Activity activity;

    public AntrianAdapter(List<Antrian> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tmp_list1, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
//        holder.bind(list.get(position));
        holder.itemView.setOnClickListener(v -> {
            Antrian antrian = list.get(position);
            Intent intent = new Intent(activity, DetailAntrianActivity.class);
            intent.putExtra("nomor_antrian", antrian.getNomor_antrian());
            intent.putExtra("di_layani", antrian.getDi_layani());
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        holder.tvNamaPasien.setText(list.get(position).getNama_pasien());
        holder.tvPerkiraan.setText(list.get(position).getDi_layani());
        holder.tvAntrian.setText(list.get(position).getNomor_antrian());

        if (list.get(position).getNomor_antrian().contains("A")) {
            holder.container.setBackgroundResource(R.color.gree_moss);
        } else if (list.get(position).getNomor_antrian().contains("B")) {
            holder.container.setBackgroundResource(R.color.green_jade);
        } else if (list.get(position).getNomor_antrian().contains("C")) {
            holder.container.setBackgroundResource(R.color.gree_mint);
        } else if (list.get(position).getNomor_antrian().contains("D")) {
            holder.container.setBackgroundResource(R.color.gree_russian);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNamaPasien, tvPerkiraan, tvAntrian;
        private ConstraintLayout container;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.containerColor);
            tvNamaPasien = itemView.findViewById(R.id.tv1);
            tvPerkiraan = itemView.findViewById(R.id.tv3);
            tvAntrian = itemView.findViewById(R.id.tv2);
        }

        private void bind(Antrian antrian) {
            tvNamaPasien.setText(antrian.getNama_pasien());
            tvPerkiraan.setText(antrian.getDi_layani());
            tvAntrian.setText(antrian.getNomor_antrian());
        }
    }
}
