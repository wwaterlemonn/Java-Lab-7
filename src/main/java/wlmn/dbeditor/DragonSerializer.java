package wlmn.dbeditor;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import wlmn.character.Dragon;
import wlmn.character.Person;

/**
 * Кастомный сериализатор для сериализации объекта {@link Dragon} в JSON.
 * @deprecated Библиотека Jackson выполняет функции этого сериализатора самостоятельно своим функционалом по умолчанию.
 */
@Deprecated
public class DragonSerializer extends StdSerializer<Dragon>{
    public DragonSerializer(){
        this(null);
    }

    public DragonSerializer(Class<Dragon> t){
        super(t);
    }
    
    @Override
    public void serialize(Dragon value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException{
        jgen.writeStartObject();

        jgen.writeStringField("name", value.getName());

        jgen.writeArrayFieldStart("coordinates");
        jgen.writeNumber(value.getCoordinates().getX());
        jgen.writeNumber(value.getCoordinates().getY());
        jgen.writeEndArray();

        jgen.writeNumberField("age", value.getAge());
        jgen.writeNumberField("weight", value.getWeight());
        jgen.writeBooleanField("speaking", value.isSpeaking());
        jgen.writeStringField("color", value.getColor().toString());
        
        Person killer = value.getKiller();
        if (killer == null){
            jgen.writeNullField("killer");
        }
        else{
            jgen.writeObjectFieldStart("killer");
            jgen.writeStringField("name", killer.getName());
            jgen.writeStringField("birthday", killer.getBirthday().toString());
            jgen.writeStringField("hairColor", killer.getHairColor().toString());
            jgen.writeStringField("nationality", killer.getNationality().toString());

            jgen.writeArrayFieldStart("location");
            jgen.writeNumber(killer.getLocation().getX());
            jgen.writeNumber(killer.getLocation().getY());
            jgen.writeNumber(killer.getLocation().getZ());
            jgen.writeEndArray();

            jgen.writeEndObject(); 
        }
        
        jgen.writeEndObject();
    }
}
