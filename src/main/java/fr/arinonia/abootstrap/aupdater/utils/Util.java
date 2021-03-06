package fr.arinonia.abootstrap.aupdater.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class Util {

    /**
     *
     * @param file the file you need from md5
     * @return the md5 of the file
     */
    public static String getMD5(final File file) {
        DigestInputStream stream = null;
        try {
            stream = new DigestInputStream(new FileInputStream(file), MessageDigest.getInstance("MD5"));
            final byte[] buffer = new byte[65536];

            int read = stream.read(buffer);
            while(read >= 1)
                read = stream.read(buffer);
        }
        catch(final Exception ignored) {
            return "";
        }
        finally {
            closeSilently(stream);
        }

        return String.format("%1$032x", new BigInteger(1, stream.getMessageDigest().digest()));
    }
    /**
     *
     * @param closeable Closeable
     */
    public static void closeSilently(final Closeable closeable) {
        if(closeable != null)
            try {
                closeable.close();
            }
            catch(final IOException ignored) {}
    }

}
