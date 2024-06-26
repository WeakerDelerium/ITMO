package common.transfer;

import java.io.Serializable;

public record Request(RequestTask task, CommandInfo commandInfo, UserInfo userInfo) implements Serializable {}
