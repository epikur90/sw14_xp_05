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
import java.util.Calendar;
import java.util.Date;

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

        String dateStr = "";
        String[] weekdays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
                             "Saturday"};
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
                           "Nov", "Dec"};
        Date date = searchArrayList.get(position).getDate();
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        cal.setTime(new Date());
        int dayNow = cal.get(Calendar.DAY_OF_MONTH);
        int monthNow = cal.get(Calendar.MONTH);
        int yearNow = cal.get(Calendar.YEAR);

        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        String time = (hour < 10 ? ("0" + hour) : hour) + ":" +
                (minute < 10 ? ("0" + minute) : minute);

        if ( year == yearNow ) {
            dateStr = months[month] + " " + day + ", ";

            if (month == monthNow) {

                if (day == dayNow) {
                    dateStr = "";
                } else if (dayNow - day == 1)
                    dateStr = "Yesterday, ";
                else if (dayNow - day < 7)
                    dateStr = weekdays[cal.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY] + ", ";
            }
        } else {
            dateStr = year + " " + months[month] + " " + day + ", ";
        }

        dateStr += time;

        holder.date.setText(dateStr);

        RelativeLayout wrapper = (RelativeLayout) convertView.findViewById(R.id.wrapper);
        if(message.isIncoming()) {
            wrapper.setGravity( Gravity.LEFT);
            wrapper.setBackgroundColor(Color.parseColor(sendchange));
        } else {
            wrapper.setGravity( Gravity.RIGHT);
            wrapper.setBackgroundColor(Color.parseColor(receivechange));
        }

        TextView field = (TextView) convertView.findViewById(R.id.messageText);
        TextView dateField = (TextView) convertView.findViewById(R.id.date);

        dateField.setPadding(5,3,3,3);
        field.setPadding(5,3,3,3);
        return convertView;
    }

    public void add(Message message) {
        searchArrayList.add(message);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView messageText;
        TextView date;
    }
}
