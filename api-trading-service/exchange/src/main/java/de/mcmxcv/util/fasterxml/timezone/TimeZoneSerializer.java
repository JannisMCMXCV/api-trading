package de.mcmxcv.util.fasterxml.timezone;

import java.io.IOException;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class TimeZoneSerializer extends JsonSerializer<TimeZone> {
	@Override
    public void serialize(TimeZone value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.getID());
        }
    }
}
