package com.gintellect.chat.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ChatMessageList implements IsSerializable {
	
	private List<ChatMessage> messages;
	private long time;
	private String chat;
	
	public ChatMessageList() {
		messages = new ArrayList<ChatMessage>();
		time = System.currentTimeMillis();
		chat = null;
	}
	
	public ChatMessageList(String chat, long time) {
		this.chat = chat;
		this.time = time;
		this.messages = new ArrayList<ChatMessage>();
	}
	
	public String getChat() {
		return chat;
	}
	
	public List<ChatMessage> getMessages() {
		return messages;
	}
	
	public long getTimestamp() {
		return time;
	}
	
	public void addMessage(ChatMessage msg) {
		messages.add(msg);
	}
	
	public void addMessages(List<ChatMessage> messages){
		this.messages.addAll(messages);
	}
	
}
