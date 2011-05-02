package com.gintellect.chat.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

//import com.google.appengine.api.labs.taskqueue.QueueFactory;
//import com.google.appengine.api.labs.taskqueue.TaskOptions;
//import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;

//import static com.google.appengine.api.labs.taskqueue.TaskOptions.Builder.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.gintellect.chat.client.ChatMessage;
import com.gintellect.chat.client.ChatMessageList;
import com.gintellect.chat.client.ChatRoom;
import com.gintellect.chat.client.ChatService;

@SuppressWarnings("serial")
public class ChatServiceImpl extends RemoteServiceServlet implements ChatService {
	@SuppressWarnings("unchecked")
	public List<ChatRoom> getChats() {
		PersistenceManager persister = Persister.getPersistenceManager();
		try {
			Query query = persister.newQuery(PChatRoom.class);
			query.setOrdering("date");
			List<PChatRoom> rooms = (List<PChatRoom>)query.execute();
			if (rooms.isEmpty()){
				return initializeChats(persister);
			}
			else {
				List<ChatRoom> result = new  ArrayList<ChatRoom>();
				for (PChatRoom pchatroom : rooms) {
					result.add(pchatroom.asChatRoom());
				}
				return result;
			}
		}
		finally {
			persister.close();
		}
	}
	
	public void addChat(String chat) {
		PersistenceManager persister = Persister.getPersistenceManager();
		try{
			PChatRoom newchat = new PChatRoom(chat, System.currentTimeMillis());
			persister.makePersistent(newchat);
		}
		finally {
			persister.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public ChatMessageList getMessages(String chat) {
		PersistenceManager persister = Persister.getPersistenceManager();
		try {
			Query query = persister.newQuery(PChatMessage.class);
			query.setFilter("chat == desiredRoom");
			query.declareParameters("String desiredRoom");
			query.setOrdering("date");
			List<PChatMessage> messages = (List<PChatMessage>)query.execute(chat);
			//Get the most recent message
			ChatMessageList result = null;
			if (messages.size() > 1) {
				PChatMessage lastMessage = messages.get(messages.size() -1);
				result = new ChatMessageList(chat, lastMessage.getDate());
				for (PChatMessage pchatmsg : messages) {
					result.addMessage(pchatmsg.asChatMessage());
				}
			}
			else {
				result = new ChatMessageList(chat, System.currentTimeMillis());
			}
			return result;
		}
		finally {
			persister.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void postMessage(ChatMessage message) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			return;
		}
		PersistenceManager persister = Persister.getPersistenceManager();
		try {
			PChatMessage pmessage = new PChatMessage(user.getNickname(),message.getMessage(),message.getChat());
			long timestamp = System.currentTimeMillis();
			pmessage.setDate(timestamp);
			persister.makePersistent(pmessage);
			
			Query query = persister.newQuery(PChatRoom.class);
			query.setFilter("name == " + message.getChat());
			List<PChatRoom> chats = (List<PChatRoom>)query.execute();
			PChatRoom chat = chats.get(0);
			chat.updateLastMessageDate(timestamp);
		}
		finally {
			persister.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public ChatMessageList getMessagesSince(String chat, long timestamp) {
		PersistenceManager persister = Persister.getPersistenceManager();
		try{
			Query query = persister.newQuery(PChatMessage.class);
			query.declareParameters("String desiredRoom, int earliest");
			query.setFilter("chat == desiredRoom && date > earliest");
			query.setOrdering("date");
			List<PChatMessage> messages = (List<PChatMessage>)query.execute(chat,timestamp);
			
			ChatMessageList msgList = null;
			//get the most recent message
			if (messages.size() >= 1) {
				PChatMessage lastMessage = messages.get(messages.size() -1);
				msgList = new ChatMessageList(chat, lastMessage.getDate());
			}
			else {
				msgList = new ChatMessageList(chat, System.currentTimeMillis());
			}
			for (PChatMessage msg : messages) {
				msgList.addMessage(msg.asChatMessage());
			}
			return msgList;
		}
		finally {
			persister.close();
		}
	}
	
	static final String[] DEFAULT_ROOMS = 
		new String[] {"chat", "book", "java", "python" };
	
	public List<ChatRoom> initializeChats(PersistenceManager persister) {
		List<ChatRoom> rooms = new ArrayList<ChatRoom>();
		List<PChatRoom> prooms = new ArrayList<PChatRoom>();
		long now = System.currentTimeMillis();
		for (String name : DEFAULT_ROOMS) {
			PChatRoom r = new PChatRoom(name, now);
			prooms.add(r);
			rooms.add(r.asChatRoom());
			persister.makePersistent(r);
		}
		return rooms;
	}
}
