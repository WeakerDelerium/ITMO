package common.transfer;

import common.ui.RouteReader;

import java.io.Serializable;

public record Request(String command, String[] arguments, RouteReader routeReader) implements Serializable {
}