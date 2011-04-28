package com.gintellect.persistchat;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class ChatMessage {
	
	public ChatMessage(){
	}
	
	public ChatMessage(String sender, String msg, String chatname){
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

