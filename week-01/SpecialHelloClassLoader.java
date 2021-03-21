package jvm;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SpecialHelloClassLoader extends ClassLoader{

    public static void main(String[] args) {

        try {

            Class aHelloClass = new SpecialHelloClassLoader().findClass(args[0]);

            Object obj = aHelloClass.newInstance();

            Method helloMethod = aHelloClass.getDeclaredMethod("hello");

            helloMethod.invoke(obj);

        } catch (ClassNotFoundException | NoSuchMethodException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Class<?> findClass(String fileUrl) throws ClassNotFoundException {

        byte[] bytes = new byte[0];

        try {
            byte[] oldBytes = Files.readAllBytes(Paths.get(fileUrl));

            bytes = new byte[oldBytes.length];

            for (int idx = 0, len = oldBytes.length; idx < len; idx++) {

                bytes[idx] = (byte) (255 - oldBytes[idx]);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return defineClass(null, bytes, 0, bytes.length);
    }

}
