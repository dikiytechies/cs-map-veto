package com.dikiytechies.csmapveto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService service;

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    public RoomController(RoomService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable String id) {
        log.info("Requested room with id {}", id);

        return service.getRoomById(id)
                .map(r -> ResponseEntity.status(HttpStatus.FOUND).body(RoomDto.from(r)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<RoomDto> createRoom() {
        RoomEntity room = service.createRoom();

        log.info("Called createRoom for room {}", room);
        return ResponseEntity.status(HttpStatus.CREATED).body(RoomDto.from(room));
    }
}
