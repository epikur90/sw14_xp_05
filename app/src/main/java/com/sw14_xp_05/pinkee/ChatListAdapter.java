package com.sw14_xp_05.pinkee;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatListAdapter extends ArrayAdapter<Message> {
    private LayoutInflater inflater;

    public ChatListAdapter(Context context) {
        super(context, R.layout.list_item);
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            if (convertView!= null) {
                holder.messageText = (TextView) convertView.findViewById(R.id.messageText);
                holder.date = (TextView) convertView.findViewById(R.id.date);
                convertView.setTag(holder);
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.messageText.setText(getItem(position).getMessageText());
        holder.date.setText(getItem(position).getDateSent().toString());

        return convertView;
    }

    private static class ViewHolder {
        TextView messageText;
        TextView date;
    }
}
