package com.yerti.core.utils

import java.io.*
import java.util.*

object SerializationUtils {
    /** Read the object from Base64 string.  */
    @Throws(IOException::class, ClassNotFoundException::class)
    fun fromString(s: String?): Any? {
        val data = Base64.getDecoder().decode(s)
        val ois = ObjectInputStream(
                ByteArrayInputStream(data))
        var o: Any? = null
        while (ois.readObject() != null) {
            o = ois.readObject()
        }
        ois.close()
        return o
    }

    /** Write the object to a Base64 string.  */
    @Throws(IOException::class)
    fun toString(o: Serializable?): String {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(o)
        oos.close()
        return Base64.getEncoder().encodeToString(baos.toByteArray())
    }
}