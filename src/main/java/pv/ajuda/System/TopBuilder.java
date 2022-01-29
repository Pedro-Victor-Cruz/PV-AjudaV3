package pv.ajuda.System;

import java.util.UUID;

public class TopBuilder {

    private UUID uuid;
    private String name;
    private int points;

    public TopBuilder(String uuid, String name, int points) {
        this.uuid = UUID.fromString(uuid);
        this.name = name;
        this.points = points;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }
}
