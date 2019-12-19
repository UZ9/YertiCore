package com.yerti.core.utils;

import java.io.*;
import java.util.Base64;

public class SerializationUtils {

    /**
     * Attempts to read an object from a Base64 serialized string
     * @param s serialized string
     * @return unserialized object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object fromString( String s ) throws IOException ,
            ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o = null;
        while (ois.readObject() != null) {
            o  = ois.readObject();
        }

        ois.close();
        return o;
    }

    /**
     * Attempts to serialize an {@link Serializable} object
     * @param o
     * @return the serialized object
     * @throws IOException
     */
    public static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
