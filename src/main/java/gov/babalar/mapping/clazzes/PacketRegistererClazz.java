package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;
import gov.babalar.utils.ReflectionUtils;
import gov.babalar.helpers.Mapper;

import java.lang.reflect.Field;
import java.util.Arrays;

public class PacketRegistererClazz extends MappingClazz {



    @Override
    public void setUp() {
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("private static final java.util.Map"),
                        new FieldFilter("private static int"),
                        new FieldFilter("private static int"),
                        new FieldFilter("private final java.util.Map")
                },
                -1,
                "net.minecraft"

        );
        Class<?> clazz = classMatcher.matchFrom(Myth.classes)[0];
        setClazz(clazz);
    }

    @Override
    public void generateFields() {
        try {
            Object stateClient = MainMapping.map.get("EnumConnectionState").getClazz().getField("CLIENTBOUND").get(null);
            Object stateServer = MainMapping.map.get("EnumConnectionState").getClazz().getField("SERVERBOUND").get(null);
            this.getFields().put("playPackets", Arrays.stream(ReflectionUtils.getAllFields(this.getClazz())).filter(field -> field.toString().contains("public static final") && field.toString().contains("PLAY")).findFirst().orElse(null));
            this.getMethods().put("getPacketFromID", Arrays.stream(ReflectionUtils.getAllMethods(this.getClazz())).filter(method -> method.toString().contains("java.lang.InstantiationException")).findFirst().orElse(null));
            Object play = this.getFields().get("playPackets").get(null);
            for(int i = 0; i < 41; i++)
            {
                Object packet = this.getMethods().get("getPacketFromID").invoke(play, stateClient, i);
                Myth.CLIENTBOUND_packets.put(i, packet);
            }
            for(int i = 0; i < 26; i++)
            {
                Object packet = this.getMethods().get("getPacketFromID").invoke(play, stateServer, i);
                Myth.SERVERBOUND_packets.put(i, packet);
                if(i == 2)
                {
                    for (Field field : ReflectionUtils.getAllFields(packet.getClass())) {
                        if(field.getType().isEnum())
                        {
                            for (Field ff : ReflectionUtils.getAllFields(field.getType())) {
                                if(ff.toString().contains("ATTACK"))
                                    Mapper.fields.put("actionAttack", ff);
                            }
                        }
                    }
                }
            }
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
    }
}
