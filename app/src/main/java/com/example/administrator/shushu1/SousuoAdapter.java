package com.example.administrator.shushu1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/6/10.
 */

public class SousuoAdapter extends ArrayAdapter<Sou>
{
    private int reouid;
    public SousuoAdapter(Context context, int textViewResourseId, List<Sou> objects)
    {
        super(context,textViewResourseId,objects);
        reouid=textViewResourseId;
    }
    @Override
    public View getView(int position, View convertView , ViewGroup parent)
    {
        Sou sou =getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(reouid,parent,false);
        TextView sousuoname=(TextView)view.findViewById(R.id.sousuojilu);
        sousuoname.setText(sou.getName());
        return view;
    }
}
