package com.gintellect.chat.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ChatMessage implements IsSerializable {

	public ChatMessage() {}
	
	public ChatMessage(String sender, String msg, String chatname) {
		this.senderName = sender;
		this.message = msg;
		this.chat = chatname;
	}
	
	protected String senderName;
	protected String message;
	protected String chat;
	protected long date;
	
	public String getSenderName() {
		return senderName;
	}
	
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getChat() {
		return chat;
	}
	
	public void setChat(String chat) {
		this.chat = chat;
	}
	
	public long getDate(){
		return date;
	}
	
	public void setDate(long date) {
		this.date = date;	
	}
}
