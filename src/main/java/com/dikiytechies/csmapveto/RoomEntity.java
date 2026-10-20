package com.dikiytechies.csmapveto;

import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RoomEntity {
    private final String id;
    private final List<WebSocketSession> players = new ArrayList<>();

    public boolean join(WebSocketSession session) {
        if (players.size() >= 2 || players.contains(session))
            return false;

        return players.add(session);
    }

    public RoomEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<WebSocketSession> getPlayers() {
        return players;
    }
}
