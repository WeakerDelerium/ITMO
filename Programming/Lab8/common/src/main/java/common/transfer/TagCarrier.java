package common.transfer;

import java.io.Serializable;

public record TagCarrier(String tag, Object data) implements Serializable {}
