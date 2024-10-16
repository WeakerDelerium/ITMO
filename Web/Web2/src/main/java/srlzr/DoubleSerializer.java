package srlzr;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import util.Transformer;

import java.io.IOException;

public class DoubleSerializer extends JsonSerializer<Double> {
    @Override
    public void serialize(Double value, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeNumber(Transformer.doubleTransform.apply(value));
    }
}
