package com.sw14_xp_05.pinkee;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatListAdapter extends BaseAdapter {
    private static ArrayList<Message> searchArrayList;

    private LayoutInflater inflater;
    private SQLiteStorageHelper dbhelper;

    public static final String MyPreferences = "MyPrefs";
    public static final String MyRcolor = "MyRcolor";
    public static final String MyScolor = "MyScolor";

    public ChatListAdapter(Context context, ArrayList<Message> results) {
        searchArrayList = results;
        inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        final SharedPreferences sharedpreferences = inflater.getContext().getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        String sendchange = "#FFFFFF";
        String receivechange = "#FFFFFF";

        if(sharedpreferences.contains(MyRcolor))
        {
            receivechange = sharedpreferences.getString(MyRcolor, "");
        }

        if(sharedpreferences.contains(MyScolor))
        {
            sendchange = sharedpreferences.getString(MyScolor,"");
        }

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.chat_list_item, parent, false);
            holder = new ViewHolder();

            holder.messageText = (TextView) convertView.findViewById(R.id.messageText);
            holder.date = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Message message = searchArrayList.get(position);

        holder.messageText.setText(searchArrayList.get(position).getMessageText());
        holder.date.setText(searchArrayList.get(position).getDate().toString());

        RelativeLayout wrapper = (RelativeLayout) convertView.findViewById(R.id.wrapper);
        if(message.isIncoming())
        {
            wrapper.setGravity( Gravity.RIGHT);
            wrapper.setBackgroundColor(Color.parseColor(sendchange));
        }
        else
        {
            wrapper.setGravity( Gravity.LEFT);
            wrapper.setBackgroundColor(Color.parseColor(receivechange));
        }
        //wrapper.setGravity(position % 2 != 0 ? Gravity.LEFT : Gravity.RIGHT);
        //wrapper.setBackgroundColor(position % 2 != 0 ? Color.LTGRAY: Color.TRANSPARENT);




        TextView field = (TextView) convertView.findViewById(R.id.messageText);
        TextView dateField = (TextView) convertView.findViewById(R.id.date);

        dateField.setPadding(5,3,3,3);
        field.setPadding(5,3,3,3);
        return convertView;
    }

    public void add(Message message) {
        searchArrayList.add(message);
        //TODO message.getContact().setLastMessage(message.getDate());
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView messageText;
        TextView date;
    }
}
