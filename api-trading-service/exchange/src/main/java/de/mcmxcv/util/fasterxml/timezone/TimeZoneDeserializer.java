package de.mcmxcv.util.fasterxml.timezone;

import java.io.IOException;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class TimeZoneDeserializer extends JsonDeserializer<TimeZone> {
	@Override
    public TimeZone deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String timeZoneID = p.getText();
        return TimeZone.getTimeZone(timeZoneID);
    }
}
