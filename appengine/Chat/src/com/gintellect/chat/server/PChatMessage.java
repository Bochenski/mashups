package com.gintellect.chat.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.gintellect.chat.client.ChatMessage;

@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class PChatMessage {
	
	public PChatMessage(){
	}
	
	public PChatMessage(String sender, String msg, String chatname){
		this.senderName = sender;
		this.message = msg;
		this.chat = chatname;
	}
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
		private Key key;
	
	@Persistent
		protected String senderName;
	
	@Persistent
		protected String message;
	
	@Persistent
		protected String chat;
	
	@Persistent
		protected long date;
	
	public Key getKey(){
		return key;
	}
	
	public ChatMessage asChatMessage() {
		ChatMessage result = new ChatMessage(senderName, message, chat);
		result.setDate(date);
		return result;
	}
	
	public String getSenderName(){
		return senderName;
	}
	
	public void setSenderName(String senderName){
		this.senderName = senderName;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public String getChat(){
		return chat;
	}
	
	public void setChat(String chat){
		this.chat = chat;
	}
	
	public long getDate(){
		return date;
	}
	
	public void setDate(long date){
		this.date = date;
	}
}

