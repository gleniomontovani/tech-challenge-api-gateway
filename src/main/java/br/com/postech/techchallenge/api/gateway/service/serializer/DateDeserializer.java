package br.com.postech.techchallenge.api.gateway.service.serializer;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import br.com.postech.techchallenge.api.gateway.service.util.Util;

public final class DateDeserializer implements JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonElement element, Type type, JsonDeserializationContext context)
			throws JsonParseException {

		Date date = null;
		if (element != null) {

			LocalDateTime localDateTime = Instant.ofEpochMilli(element.getAsLong()).atZone(ZoneId.systemDefault())
					.toLocalDateTime();

			date = Util.converterDate(localDateTime);
		}

		return date;
	}
}