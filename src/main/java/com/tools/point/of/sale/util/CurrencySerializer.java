package com.tools.point.of.sale.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Custom serializer for converting Float values into currency format for JSON output.
 * This serializer formats the float value as a currency string according to the US locale.
 *
 * @author melessweldemichael
 */
public class CurrencySerializer extends JsonSerializer<Float> {

    @Override
    public void serialize(Float value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedCurrency = currencyFormat.format(value);
        gen.writeString(formattedCurrency);
    }
}
