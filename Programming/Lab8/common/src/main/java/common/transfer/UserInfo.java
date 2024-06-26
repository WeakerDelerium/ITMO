package common.transfer;

import java.io.Serializable;

public record UserInfo(String username, String passwd) implements Serializable {}
