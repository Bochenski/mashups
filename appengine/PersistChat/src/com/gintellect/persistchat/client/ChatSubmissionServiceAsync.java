package com.gintellect.persistchat.client;

import java.util.Date;
import java.util.List;
import com.gintellect.persistchat.ChatMessage;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChatSubmissionServiceAsync {
	
	void postMessage(ChatMessage messages, AsyncCallback<List<ChatMessage>> callback);
	
	void getMessages(String chatroom, AsyncCallback<List<ChatMessage>> callback);
	
	void getMessagesSince(String chat, Date timestamp, AsyncCallback<List<ChatMessage>> callback);

}
