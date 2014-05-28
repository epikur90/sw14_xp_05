package com.sw14_xp_05.pinkee;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MessageList extends ListView {
	
	//private ArrayAdapter<Message> listAdapter;
    private ChatListAdapter listAdapter;
    private SQLiteStorageHelper dbhelper;
    private Contact contact;

    public MessageList(Context context) {
		super(context);
		init();

	}

	public MessageList(ListView listView, Contact contact) {
		this(listView.getContext());
        this.contact = contact;
	}

	public MessageList(Context context, AttributeSet attrSet) {
		super(context, attrSet);
        contact = new Contact();
        init();
	}

	public MessageList(Context context, AttributeSet attrSet, int defStyle) {
		super(context, attrSet, defStyle);
		init();
	}

	public void init() {
        dbhelper = new SQLiteStorageHelper(this.getContext());

        setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		setStackFromBottom(true);

        setDivider(null);
        setDividerHeight(0);
	}
	
	public void displayMessage(Message message){
        if (message.getMessageText().isEmpty()) return;

        dbhelper.saveMessage(message);
        listAdapter.add(message);

	}

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
        //listAdapter.notifyDataSetChanged();
    }

    public void initAdapter(){
        ArrayList<Message> valueList = dbhelper.getMessages(contact);

        listAdapter = new ChatListAdapter(getContext(), valueList);
        setAdapter(listAdapter);
    }
}
