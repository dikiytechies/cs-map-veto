package com.dikiytechies.csmapveto;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RoomRepository {
    private final Map<String, RoomEntity> rooms = new ConcurrentHashMap<>();


    public void save(RoomEntity roomEntity) {
        rooms.put(roomEntity.getId(), roomEntity);
    }

    public boolean saveIfAbsent(RoomEntity roomEntity) {
        return rooms.putIfAbsent(roomEntity.getId(), roomEntity) == null;
    }

    public void removeById(String id) {
        rooms.remove(id);
    }

    public Optional<RoomEntity> findById(String id) {
        return Optional.ofNullable(rooms.get(id));
    }
}
