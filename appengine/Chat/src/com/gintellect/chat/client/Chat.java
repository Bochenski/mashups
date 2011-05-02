package com.gintellect.chat.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class Chat implements EntryPoint {

	private final ChatServiceAsync chatService = GWT.create(ChatService.class);
	
	private String currentChat;
	private long lastMessageTime;
	private TextArea text;
	private String user = null;
	
	@Override
	public void onModuleLoad() {
		final VerticalPanel mainVert = new VerticalPanel();
		final VerticalPanel topPanel = new VerticalPanel();
		final HorizontalPanel midPanel = new HorizontalPanel();
		final HorizontalPanel bottomPanel = new HorizontalPanel();
		
		mainVert.add(topPanel);
		mainVert.add(midPanel);
		mainVert.add(bottomPanel);
		
		final Label title = new Label("AppEngine Client");
		final Label subtitle = new Label(new Date().toString());
		
		title.addStyleName("title");
		topPanel.add(title);
		topPanel.add(subtitle);
		
		final VerticalPanel chatList = new VerticalPanel();
		chatList.setBorderWidth(2);
		
		final Label chatLabel = new Label("Chats");
		chatLabel.addStyleName("emphasized");
		chatList.add(chatLabel);
		chatList.setWidth("10em");
		populateChats(chatList);
		
		//TextArda text is defined as a field of the class
		//so that the textarea can be reference by handelr methods
		text = new TextArea();
		text.addStyleName("messages");
		text.setWidth("60em");
		text.setHeight("20em");
		midPanel.add(chatList);
		midPanel.add(text);
		
		final Label label = new Label("Enter Message:");
		label.addStyleName("bold");
		
		final TextBox messageBox = new TextBox();
		messageBox.setWidth("60em");
		
		final Button sendButton = new Button("send");
		
		bottomPanel.add(label);
		bottomPanel.add(messageBox);
		bottomPanel.add(sendButton);
		setupSendMessageHandlers(sendButton,messageBox);
		
		RootPanel.get().add(mainVert);
		//focus the cursor on the message box.
		messageBox.setFocus(true);
		messageBox.selectAll();
		setupTimedUpdate();
		
	}
	
	public void populateChats(final VerticalPanel chatListPanel){
		chatService.getChats(new AsyncCallback<List<ChatRoom>>() {
			public void onFailure(Throwable caught) {
				chatListPanel.add(new Label("Couldn't retrieve chats: " + caught));
			}
			public void onSuccess(List<ChatRoom> chats) {
				for (ChatRoom chat : chats) {
					Button chatButton = new Button(chat.getName());
					chatListPanel.add(chatButton);
					Chat.this.setupChatClickHandler(chatButton, chat.getName());
				}
				setCurrentChat(chats.get(0).getName());
			}
		});
	}
	
	protected void setupChatClickHandler(final Button chatButton, final String chat) {
		chatButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				setCurrentChat(chat);
			}
		});
	}
	
	public void setCurrentChat(String chat) {
		System.err.println(">>>>>>> Setting current chat to: " + chat);
		
		text.setText("Current chat: " + chat + "\n");
		currentChat = chat;
		chatService.getMessages(getCurrentChat(), new MessageListCallback());
	}
	
	private void setupSendMessageHandlers(final Button sendButton, final TextBox messageBox) {
		//create a handler for the send button and nameField
		class SendMessageHandler implements ClickHandler, KeyUpHandler {
			//fired when the user click son the send button.
			public void onClick(ClickEvent event) {
				sendMessageToServer();
			}
			
			//fired when the user types in the nameField.
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendMessageToServer();
				}
			}
			
			//send a chat message to the server
			private void sendMessageToServer() {
				ChatMessage chatmsg = new ChatMessage(user, messageBox.getText(), getCurrentChat());
				messageBox.setText("");
				chatService.postMessage(chatmsg, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						Chat.this.addNewMessage(new ChatMessage( "System" , "Error sending message: " + caught.getMessage(), getCurrentChat()));				
					}
					
					public void onSuccess(Void v) {
						chatService.getMessagesSince(getCurrentChat(), lastMessageTime, new MessageListCallback());
					}

				});
			}
		}
		
		SendMessageHandler handler = new SendMessageHandler();
		sendButton.addClickHandler(handler);
		messageBox.addKeyUpHandler(handler);
	}
	
	public class MessageListCallback implements AsyncCallback<ChatMessageList> {
		public void onFailure(Throwable caught){}
		public void onSuccess(ChatMessageList result) {
			addNewMessages(result);
		}
		
	}
	
	protected void addNewMessages(ChatMessageList newMessages) {
		lastMessageTime = newMessages.getTimestamp();
		StringBuilder content = new StringBuilder();
		content.append(text.getText());
		for (ChatMessage cm : newMessages.getMessages()) {
			content.append(renderChatMessage(cm));
		}
		text.setText(content.toString());
	}
		
	protected String renderChatMessage(ChatMessage msg) {
		Date d = new Date(msg.getDate());
		@SuppressWarnings("deprecation")
		String dateStr = d.getMonth() + "/" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + "." + d.getSeconds();
		return "[From: " + msg.getSenderName() + " at " + dateStr + "]: " + msg.getMessage() + "\n";
	}
		
	protected void addNewMessage(ChatMessage newMessage) {
		text.setText(text.getText() + renderChatMessage(newMessage));
	}
	
	private void setupTimedUpdate() {
		//Create a new timer
		Timer elapsedTimer = new Timer() {
			public void run() {
				chatService.getMessagesSince(getCurrentChat(), lastMessageTime, new MessageListCallback());
			}
		};
		//scheduled the time for every 1/2 second
		elapsedTimer.scheduleRepeating(500);
	}
	
	public String getUser() {
		return user;
	}
	
	protected String getCurrentChat() {
		return currentChat;
	}
}
