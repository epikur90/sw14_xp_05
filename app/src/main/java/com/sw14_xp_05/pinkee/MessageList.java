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


    public MessageList(Context context) {
		super(context);
		init();

	}

	public MessageList(ListView listView) {
		this(listView.getContext());
	}

	public MessageList(Context context, AttributeSet attrSet) {
		super(context, attrSet);
		init();

	}

	public MessageList(Context context, AttributeSet attrSet, int defStyle) {
		super(context, attrSet, defStyle);
		init();
	}

	public void init() {
		
		setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		setStackFromBottom(true);
		Log.d("#messageList", "Message List created");
		ArrayList<Message> valueList = new ArrayList<Message>();

		listAdapter = new ChatListAdapter(getContext(), valueList);
        setDivider(null);
        setDividerHeight(0);
		setAdapter(listAdapter);
	}
	
	public void displayMessage(Message message){
        if (!message.getMessageText().isEmpty())
		    listAdapter.add(message);
	}

}
