package com.dikiytechies.csmapveto;

public record RoomDto(String id, int playerCount) {
    public static RoomDto from(RoomEntity roomEntity) {
        return new RoomDto(roomEntity.getId(), roomEntity.getPlayers().size());
    }
}
