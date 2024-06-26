package common.transfer;

import java.io.Serializable;

public record Response(Boolean ok, Object data) implements Serializable {}