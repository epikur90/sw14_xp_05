package com.sw14_xp_05.pinkee;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.plus.model.people.Person;

import java.util.ArrayList;

/**
 * Created by your mama on 28.05.14.
 */
public class ContactListAdapter extends BaseAdapter {

    private static ArrayList<Contact> searchArrayList;

    private LayoutInflater inflater;
    private  SQLiteStorageHelper dbhelper;

    public ContactListAdapter(Context context, ArrayList<Contact> results) {
        searchArrayList = results;
        inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Contact getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.contact_list_item, parent, false);
            holder = new ViewHolder();

            holder.contactName = (TextView) convertView.findViewById(R.id.contactName);
            holder.contactImage = (ImageView) convertView.findViewById(R.id.contactImage);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact contact = searchArrayList.get(position);

        holder.contactName.setText(searchArrayList.get(position).getFullName());
        //holder.contactImage.setImage

        return convertView;
    }

    private static class ViewHolder {
        TextView contactName;
        ImageView contactImage;
    }
}
