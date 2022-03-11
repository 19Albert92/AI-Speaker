package com.QwertyNetworks.ai_speaker.db.socket;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import org.json.*;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

import com.QwertyNetworks.ai_speaker.ui.main.view.MainActivity;
import com.QwertyNetworks.ai_speaker.R;
import com.QwertyNetworks.ai_speaker.db.preferences.PreferencesOther;
import com.QwertyNetworks.ai_speaker.ui.constance.Constance;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SocketHandler {
    WebSocket ws = null;
    private int countMessage = 0;
    PreferencesOther preferencesOther = new PreferencesOther();

    public int setCountMessage(int count) {
        countMessage = count;
        return count;
    }
    public void getSockets(Context context) {
        WebSocketFactory factory = new WebSocketFactory();

        try {
            ws = factory.createSocket("ws://136.243.60.211:8999");
            ws.addListener(new WebSocketAdapter() {
                @Override
                public void onTextMessage(WebSocket websocket, String text) throws Exception {
                    super.onTextMessage(websocket, text);
                    if (!websocket.isOpen()) {
                        websocket.connect();
                    }

                    System.out.println("text result : " + text);
                    Boolean isSystem = preferencesOther.getToSharedBoolean(Constance.IS_USER_SYSTEM,"isUserSystem", context);

                    JSONObject obj = new JSONObject(text);
                    JSONArray arr = obj.getJSONArray("unread");

                    if (!text.equals("connected")) {
                        if (!isSystem) {
                            for (int i = 0; i < arr.length(); i++) {
                                if (arr.get(i) != "") {
                                    String message = (String) arr.get(i);
                                    System.out.println(message);
                                    countMessage++;
                                    natificationSend(context, arr, countMessage);
                                }
                            }
                        }
                    }

                    System.out.println("connected: " + websocket.isOpen());
                }

                @Override
                public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
                    super.onConnected(websocket, headers);
                    String userid = preferencesOther.getToSharedString(Constance.USER_ID_KEY,"User_information",context);

                    String stringUserId = String.format("{\"user_id\": \"%s\"}", userid);
                    websocket.sendText(stringUserId);
                    System.out.println("server disconnect connect frame: " + websocket.isOpen());
                }

                @Override
                public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
                    super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
                    System.out.println("server disconnect: " + closedByServer);
                    Thread.sleep(3000);
                    websocket.connect();
                    websocket.connectAsynchronously();
                    System.out.println("server disconnect2: " + closedByServer);
                }

                @Override
                public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                    super.onPongFrame(websocket, frame);
                }

                @Override
                public void onPingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                    super.onPingFrame(websocket, frame);
                    String userid = preferencesOther.getToSharedString(Constance.USER_ID_KEY,"User_information",context);

                    String stringUserId = String.format("{\"user_id\": \"%s\"}", userid);
                    websocket.sendText(stringUserId);
                }
            });
            ws.connectAsynchronously();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void natificationSend(Context context, JSONArray message, int count) {
        int numMessages = 0;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "natification");
        builder.setSmallIcon(R.drawable.ainotbg);
        builder.setContentTitle("У вас новое сообщение");
//        builder.setContentText(message);
        builder.setNumber(++numMessages);
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        Intent contentIntent = new Intent(context, MainActivity.class);

        PendingIntent pending = PendingIntent.getActivity(context, 0, contentIntent, 0);
        builder.setContentIntent(pending);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        for (int i = 0; i < message.length(); i++) {
//            if (count < 2) {
            try {
                inboxStyle.addLine(message.get(i).toString());
                inboxStyle.setBigContentTitle("У вас " + message.get(i) + " новое сообщение");
                builder.setContentText(message.get(i).toString());
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        builder.setStyle(inboxStyle);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(-1, builder.build());
    }
}
