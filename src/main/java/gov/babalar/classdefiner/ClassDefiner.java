package gov.babalar.classdefiner;

import gov.babalar.Main;
import gov.babalar.Myth;
import gov.babalar.mapping.s.DefineMapping;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.notes.AddAsMethod;
import gov.babalar.notes.MapThisClass;
import gov.babalar.notes.MapThisMethod;
import gov.babalar.utils.ClassUtils;
import gov.babalar.utils.ReflectionUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.TryCatchBlockSorter;
import org.objectweb.asm.tree.*;
import sun.misc.Unsafe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ----------
 * 9/16/2023
 * 8:00 PM
 * ----------
 **/
public class ClassDefiner implements Opcodes {
    public static Class<?> defineDesignedClass(Class<?> targetClass) throws Exception
    {
        byte[] bytes = ClassUtils.getClazzBytes(targetClass);
        ClassReader classReader = new ClassReader(bytes);
        ClassNode node = new ClassNode();
        classReader.accept(node , 0);

        transform(targetClass , node);

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        node.accept(classWriter);
        byte[] toDefine = classWriter.toByteArray();
        File mas = new File("C:\\Users\\" + System.getProperty("user.name") + String.format("\\Desktop\\%s.class" , targetClass.getSimpleName()));
        if(mas.exists())
            mas.delete();
        Files.write(mas.toPath(), toDefine);

        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        Class<?> loaded = unsafe.defineClass(node.name, toDefine,0, toDefine.length , Main.currClassLoader , null);
        DefineMapping.env.put(node.name , loaded);
        return loaded;

    }
    public static void transform(Class<?> original ,ClassNode classNode)
    {
        try {
            MapThisClass mapThisClass = original.getAnnotation(MapThisClass.class);
            HashMap<String, MapThisMethod> methodKeys = new HashMap<>();
            ArrayList<MethodNode> methodNodes = new ArrayList<>();
            ArrayList<String> toRemove = new ArrayList<>();
            for (Method method : ReflectionUtils.getAllMethods(original)) {
                MapThisMethod mapThisMethod = method.getAnnotation(MapThisMethod.class);
                AddAsMethod addAsMethod = method.getAnnotation(AddAsMethod.class);
                if(addAsMethod != null)
                {
                    toRemove.add(method.getName());
                    methodNodes.add((MethodNode) method.invoke(null));
                    continue;
                }
                if (mapThisMethod != null) {
                    methodKeys.put(method.getName(), mapThisMethod);
                }
            }
            for (MethodNode method : methodNodes) {
                classNode.methods.removeIf(methodNode -> methodNode.name.equals(method.name));
                classNode.methods.add(method);
            }
            if (mapThisClass != null) {
                classNode.name = normalize(mapThisClass.className());
                classNode.superName = normalize(mapThisClass.superClass());
                classNode.visibleAnnotations.clear();
            }
            for (MethodNode method : classNode.methods) {
                for (AbstractInsnNode instruction : method.instructions) {
                    if(instruction instanceof MethodInsnNode)
                    {
                        MethodInsnNode methodInsnNode = (MethodInsnNode) instruction;
                        if(methodInsnNode.owner.contains(original.getSimpleName())) {
                            methodInsnNode.owner = classNode.name;
                        }
                    }
                    if(instruction instanceof FieldInsnNode)
                    {
                        FieldInsnNode fieldInsnNode = (FieldInsnNode) instruction;
                        if(fieldInsnNode.owner.contains(original.getSimpleName())) {
                            fieldInsnNode.owner = classNode.name;
                        }
                    }
                }
                if(method.name.equals("<init>") && mapThisClass != null)
                {
                    for (AbstractInsnNode instruction : method.instructions) {
                        if(instruction.getOpcode() == INVOKESPECIAL)
                        {
                            MethodInsnNode methodInsnNode = (MethodInsnNode) instruction;
                            methodInsnNode.owner = normalize(mapThisClass.superClass());
                        }
                    }
                    continue;
                }
                if (!methodKeys.containsKey(method.name))
                    continue;
                MapThisMethod mapAnno = methodKeys.get(method.name);
                method.name = normalize(mapAnno.methodName());
                method.desc = normalize(mapAnno.descriptor());
                method.visibleAnnotations.clear();
            }
            classNode.methods.removeIf(methodNode -> toRemove.contains(methodNode.name));
        }catch (Exception e)
        {
            Myth.logger.logException(e);
        }
    }
    public static String normalize(String s) {
        ClassName: {
            Pattern pattern = Pattern.compile("%[A-Za-z0-9]+%");
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                String matched = matcher.group();
                System.out.println(matched);
                String key = matched.replace("%" , "");
                System.out.println(key);
                s = s.replace(matched, MainMapping.map.get(key).getClazz().getName().replace('.', '/'));
            }
        }
        MethodName: {
            Pattern pattern = Pattern.compile("#[A-Za-z0-9]+#");
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()){
                String matched = matcher.group();
                System.out.println(matched);
                String key = matched.replace("#" , "");
                System.out.println(key);
                String[] splitted = key.split("0");
                s = s.replace(matched , MainMapping.map.get(splitted[0]).getMethods().get(splitted[1]).getName());
            }
        }
        return s;
    }
}