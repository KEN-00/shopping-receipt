package dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import constant.PurchaseLocation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PrintReceiptRequest {
    @JsonDeserialize(using = PurchaseLocationDeserializer.class)
    private PurchaseLocation location;
    private List<PurchaseItem> items;

    public static class PurchaseLocationDeserializer extends JsonDeserializer<PurchaseLocation> {
        @Override
        public PurchaseLocation deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            ObjectCodec oc = jp.getCodec();
            String value = oc.readValue(jp, String.class);
            return PurchaseLocation.valueOf(value);
        }
    }
}

