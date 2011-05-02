package com.gintellect.chat.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.gintellect.chat.client.ChatRoom;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PChatRoom {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	String name;
	
	@Persistent
	long date;
	
	public PChatRoom() {
	}
	
	public PChatRoom(String chat, long date) {
		this.date = date;
		this.name = chat;
	}
	
	public ChatRoom asChatRoom() {
		return new ChatRoom(name, date);
	}
	
	public String getName() {
		return name;
	}
	
	public Key getKey() {
		return key;
	}
	
	public long getLastMessageDate() {
		return date;
	}
	
	public void updateLastMessageDate(long d) {
		date = d;
	}
	
}
