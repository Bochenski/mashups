package com.gintellect.chat.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ChatRoom implements IsSerializable{
	
	String name;
	long date;
	
	public ChatRoom(String chat, long date) {
		this.date = date;
		this.name = chat;
	}
	
	public ChatRoom(){
	}
	
	public String getName() {
		return this.name;
	}
	
	public long getLastMessageDate() {
		return this.date;
	}
	
	public void updateLastMessageDate(long d) {
		this.date = d;
	}
}
