package common.transfer;

import common.ui.RouteBuilder;

import java.io.Serializable;

public record CommandInfo(String command, String[] arguments, RouteBuilder routeBuilder) implements Serializable {}
