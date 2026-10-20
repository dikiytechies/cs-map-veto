package com.dikiytechies.csmapveto;

import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RoomEntity {
    private final String id;
    private final Map<String, WebSocketSession> players = new ConcurrentHashMap<>();

    public synchronized Optional<String> addPlayer(WebSocketSession session) {
        if (players.size() >= 2)
            return Optional.empty();

        String token = UUID.randomUUID().toString();

        if (players.putIfAbsent(token, session) == null)
            return Optional.of(token);

        return Optional.empty();
    }

    public synchronized void removePlayer(WebSocketSession session) {
        players.entrySet().removeIf(e -> e.getValue().equals(session));
    }

    public RoomEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public synchronized Map<String, WebSocketSession> getPlayers() {
        return Map.copyOf(players);
    }
}
