package com.gintellect.chat.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath; 

@RemoteServiceRelativePath("chat")
public interface ChatService extends RemoteService {
	List<ChatRoom> getChats();
	void addChat (String chatname);
	void postMessage(ChatMessage messages);
	ChatMessageList getMessages(String room);
	ChatMessageList getMessagesSince(String chat, long timestamp);
}
