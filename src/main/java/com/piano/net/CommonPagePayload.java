package com.piano.net;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@JsonSerialize(using = CommonPagePayload.Serializer.class)
@Getter
@Setter
@AllArgsConstructor
public class CommonPagePayload <TPagePayload>{
    private int pageIndex;
    private int totalPage;
    private long totalCount;
    private List<TPagePayload> payloadList;
    private String listName;

    public static class Serializer extends StdSerializer<CommonPagePayload> {
        public Serializer(){
          super(CommonPagePayload.class);
        }

        @Override
        public void serialize(CommonPagePayload value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeNumberField("pageIndex", value.pageIndex);
            gen.writeNumberField("totalPage", value.totalPage);
            gen.writeNumberField("totalCount", value.totalCount);
            gen.writeArrayFieldStart(value.listName);
            for (Object e : value.payloadList) {
                gen.writeObject(e);
            }
            gen.writeEndArray();
            gen.writeEndObject();
        }
    }
}
