package com.gintellect.chat.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChatServiceAsync {
	void getChats(AsyncCallback<List<ChatRoom>> chats);
	void addChat(String chatname, AsyncCallback<Void> callback); 
	void postMessage(ChatMessage message, AsyncCallback<Void> callback);
	void getMessages(String chatroom, AsyncCallback<ChatMessageList> callback);
	void getMessagesSince(String chat, long timestamp, AsyncCallback<ChatMessageList> callback);
}
