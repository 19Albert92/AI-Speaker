package com.QwertyNetworks.ai_speaker.db.socket;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

import com.QwertyNetworks.ai_speaker.MainActivity;
import com.QwertyNetworks.ai_speaker.R;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SocketHandler {
    WebSocket ws = null;
    private int countMessage = 0;
    private static String thisMessage = "connected";

    public int setCountMessage(int count) {
        countMessage = count;
        return count;
    }
    public void getSockets(Context context) {
        WebSocketFactory factory = new WebSocketFactory();

        try {
            ws = factory.createSocket("ws://95.216.57.29:8765");
            ws.addListener(new WebSocketAdapter() {
                @Override
                public void onTextMessage(WebSocket websocket, String text) throws Exception {
                    super.onTextMessage(websocket, text);
                    if (!websocket.isOpen()) {
                        websocket.connect();
                    }

                    if (!text.equals("connected")) {
                    countMessage++;
                        natificationSend(context, text, countMessage);
//                        thisMessage = text;
                    }

                    System.out.println("connected: " + websocket.isOpen());
                    System.out.println("text result : " + text);


                }

                @Override
                public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
                    super.onConnected(websocket, headers);
                    websocket.sendText("@1042939:qaim.me");
                }

                @Override
                public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
                    super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
                    System.out.println("server disconnect: " + closedByServer);
                    if (!closedByServer) {
                        websocket.connect();
                        onConnected(websocket, null);
                    }
                }

                @Override
                public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                    super.onPongFrame(websocket, frame);
                }

                @Override
                public void onPingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                    super.onPingFrame(websocket, frame);
                    websocket.sendText("{\"user_id\": \"@qn1042939:qaim.me\"}");
                }
            });
            ws.connectAsynchronously();

            if (ws.isOpen()) {
                ws.sendText("Message from Android");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void natificationSend(Context context, String message, int count) {
        int numMessages = 1;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "natification");
        builder.setSmallIcon(R.drawable.ainotbg);
        builder.setContentTitle("У вас новое сообщение");
        builder.setContentText(message);
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

        for (int i=0; i < count; i++) {
            if (count < 3) {
                inboxStyle.addLine(message);
            } else {
                break;
            }
        }

        if (count >= 2) {
            inboxStyle.setBigContentTitle("У вас новое сообщение");
        } else {
            inboxStyle.setBigContentTitle("У вас " + count + " новое сообщение");
        }

        builder.setStyle(inboxStyle);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
