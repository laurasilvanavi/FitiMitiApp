package com.example.laurute.fitimitiapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.laurute.fitimitiapp.R;

import java.util.ArrayList;

/**
 * Created by Kristaliukas on 21/11/2016.
 */

public class ArrayAdapterTask  extends ArrayAdapter<String> {
    Context mContext;
    int layoutResourceId;
    ArrayList<String> data = null;

    boolean[] checkBoxState;

    ViewHolder viewHolder;

    public ArrayAdapterTask(Context mContext, int layoutResourceId, ArrayList<String> data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;

        checkBoxState=new boolean[data.size()];
    }

    private class ViewHolder
    {
        TextView task;
        CheckBox checkBox;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.task_info, null);
            viewHolder = new ViewHolder();

            viewHolder.task=(TextView) convertView.findViewById(R.id.task);
            viewHolder.checkBox=(CheckBox) convertView.findViewById(R.id.check);

            //sulinkina duomenis, su tuo, ka rodysime
            convertView.setTag(viewHolder);
        }
        else
            viewHolder=(ViewHolder) convertView.getTag();


        //nustatome duomenis, kuriuos rodysime
        viewHolder.task.setText(data.get(position).toString());
        viewHolder.checkBox.setChecked(checkBoxState[position]);

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(((CheckBox)v).isChecked())
                    checkBoxState[position]=true;
                else
                    checkBoxState[position]=false;

            }
        });

        //grazina vaizda, kuri rodysime
        return convertView;
    }

    public boolean getIfChecked(int position) {
        return checkBoxState[position];
    }
    public void setIfChecked(int position) {
        checkBoxState[position] = false;
    }

}
