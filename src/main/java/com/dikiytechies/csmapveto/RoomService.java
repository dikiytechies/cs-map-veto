package com.dikiytechies.csmapveto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService {
    private final RoomRepository repository;

    private static Logger log = LoggerFactory.getLogger(RoomService.class);

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public Optional<RoomEntity> getRoomById(String id) {
        return repository.findById(id);
    }

    public RoomEntity createRoom() {
        while (true) {
            String id = UUID.randomUUID().toString().substring(0, 8);

            RoomEntity roomEntity = new RoomEntity(id);

            if (repository.saveIfAbsent(roomEntity))
                return roomEntity;
        }
    }
}
