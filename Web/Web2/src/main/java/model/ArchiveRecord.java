package model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import srlzr.BooleanSerializer;
import srlzr.DoubleSerializer;

import java.time.LocalTime;

@JsonPropertyOrder({"x", "y", "r", "hit", "sent", "exec"})
public class ArchiveRecord {
    @JsonSerialize(using = DoubleSerializer.class)
    private final double x;

    @JsonSerialize(using = DoubleSerializer.class)
    private final double y;

    private final double r;

    @JsonSerialize(using = BooleanSerializer.class)
    private boolean hit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private final LocalTime sent;

    @JsonSerialize(using = DoubleSerializer.class)
    private double exec;

    @JsonCreator
    public ArchiveRecord(@JsonProperty(value = "x", required = true) double x,
                         @JsonProperty(value = "y", required = true) double y,
                         @JsonProperty(value = "r", required = true) double r,
                         @JsonProperty(value = "sent", required = true) LocalTime sent) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.sent = sent;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public LocalTime getSent() {
        return sent;
    }

    public double getExec() {
        return exec;
    }

    public void setExec(double exec) {
        this.exec = exec;
    }
}
