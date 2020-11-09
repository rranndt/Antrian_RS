package com.rizkirian.rsantrian.ui.list_klinik;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.rizkirian.rsantrian.R;
import com.rizkirian.rsantrian.klinik.Klinik;
import com.rizkirian.rsantrian.klinik.KlinikAdapter;

import java.util.List;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class ListKlinikAdapter extends BaseAdapter {

    private Context context;
    private List<Klinik> pList;
    private LayoutInflater inflater = null;
    private LruCache<Integer, Bitmap> imageCache;
    private RequestQueue queue;

    public ListKlinikAdapter(Context context, List<Klinik> list) {
        this.context = context;
        this.pList = list;
        inflater = LayoutInflater.from(context);
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);
        queue = Volley.newRequestQueue(context);
    }

    public class ViewHolder {
        TextView _judul;
        TextView _detail;
        TextView _tgl;
        // ImageView _P_Image;
    }

    @Override
    public int getCount() {
        return pList.size();
    }

    @Override
    public Object getItem(int position) {

        return pList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Klinik p = pList.get(position);
        final ListKlinikAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_view_klinik, null);
            holder = new ListKlinikAdapter.ViewHolder();
            holder._judul = (TextView) convertView.findViewById(R.id.tv1);
            holder._detail = (TextView) convertView.findViewById(R.id.tv2);
            convertView.setTag(holder);
        } else {
            holder = (ListKlinikAdapter.ViewHolder) convertView.getTag();
        }
        holder._judul.setText("" + p.getId_klinik().toString());
        holder._detail.setText("" + p.getNama_klinik().toString());
        return convertView;
    }
}
