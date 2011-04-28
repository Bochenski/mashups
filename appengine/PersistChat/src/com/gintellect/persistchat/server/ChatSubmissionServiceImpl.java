package com.gintellect.persistchat.server;

import javax.jdo.PersistenceManager;

import org.datanucleus.store.query.Query;

import com.gintellect.persistchat.ChatMessage;

public class ChatSubmissionServiceImpl {
	
	@SuppressWarnings("unchecked")
	public List<ChatMessage> getMessages(String chat){
		PersistenceManager persister = Persister.getPersistenceManager();
		try{
			Query query = persister.newQuery(ChatMessage.class);
			query.setFilter("chat == desiredRoom");
			query.declareParameters("String desiredRoom");
			query.setOrdering("date");
			return (List<ChatMessage>)query.execute(chat);
		}
		finally{
			persister.close();
		}
	}
	public List<ChatMessage> postMessage(ChatMessage message){
		PersistenceManager persister = Persister.getPersistenceManager();
		persister.makePersistent(message);
		persister.close();
		return getMessages(message.getChat());
	}
}
