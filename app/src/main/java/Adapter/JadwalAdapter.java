package Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import DAO.Jadwal;
import id.sacredgeeks.ethereal.R;

/**
 * Created by SacredGeeks on 3/6/2017.
 */

public class JadwalAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;
    private int layoutItem;

    public JadwalAdapter(Activity act, ArrayList<HashMap<String, String>> data, int layout) {
        activity = act;
        this.data = data;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutItem = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        if(view == null) {
            vi = inflater.inflate(layoutItem,null);
        }

        HashMap<String,String> list = new HashMap<String, String>();
        list = data.get(i);

        if(layoutItem == R.layout.item_jadwal) {
            TextView nomer = (TextView) vi.findViewById(R.id.nomer);
            TextView namajadwal = (TextView) vi.findViewById(R.id.namajadwal);
            TextView hari = (TextView) vi.findViewById(R.id.hari);
            TextView jam = (TextView) vi.findViewById(R.id.jam);

            nomer.setText(list.get(Jadwal.KEY_NO));
            namajadwal.setText(list.get(Jadwal.KEY_JADWAL));
            hari.setText(list.get(Jadwal.KEY_HARI));
            jam.setText(list.get(Jadwal.KEY_JAM));
        }
        return vi;
    }
}
