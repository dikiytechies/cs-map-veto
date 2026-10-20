package com.dikiytechies.csmapveto;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.Optional;

@Component
public class RoomWebSocketHandler extends TextWebSocketHandler {
    private final RoomService roomService;

    public RoomWebSocketHandler(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = getRoomId(session);

        if (roomId == null) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        Optional<RoomEntity> room = roomService.getRoomById(roomId);

        if (room.isEmpty()) {
            session.close(new CloseStatus(4004, "Room not found"));
            return;
        }

        Optional<String> token = room.get().addPlayer(session);
        if (token.isEmpty()) {
            session.close(CloseStatus.POLICY_VIOLATION);
            return;
        }

        session.getAttributes().put("roomId", roomId);
        session.getAttributes().put("token", token.get());

        session.sendMessage(new TextMessage("{\"type\":\"CONNECTED\",\"token\":\"" + token + "\"}"));
    }

    private String getRoomId(WebSocketSession session) {
        URI uri = session.getUri();

        if (uri == null) {
            return null;
        }

        String[] parts = uri.getPath().split("/");

        if (parts.length != 3 || !parts[1].equals("room"))
            return null;

        return parts[2].isBlank()? null: parts[2];
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = (String) session.getAttributes().get("roomId");

        if (roomId != null) {
            roomService.getRoomById(roomId).ifPresent(r -> r.removePlayer(session));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }
}
