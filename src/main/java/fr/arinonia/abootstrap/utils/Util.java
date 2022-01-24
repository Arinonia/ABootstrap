package fr.arinonia.abootstrap.utils;

import java.io.File;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * @author Arinonia
 * Created at 18/01/2022 - 01:24
 **/
public class Util {
    /**
     *
     * @param value int
     * @param maximum int
     * @param coefficient int
     * @return percentage
     */
    public static int crossMult(final int value, final int maximum, final int coefficient) {
        return (int)((double)value / (double)maximum * (double)coefficient);
    }

    /**
     * create file passed in parameter if he doesn't exist
     * if the file object exist, but he's not a dir, he will be deleted and created as folded
     * @param file need to be checked
     * @return true if your folder exist and he's a dir
     */
    public static boolean checkFolder(final File file) {
        if (!file.exists()) {
            return file.mkdirs();
        }
        if (!file.isDirectory()) {
            if (file.delete()) {
                return file.mkdir();
            }
        }
        return file.exists() && file.isDirectory();
    }

    /**
     *
     * @param bytes downloaded bytes
     * @return formatted bytes, 14506Ko will be replaced by 1.4Mo
     */
    public static String convertBytesCount(double bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }
        final CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format("%.1f %cB", bytes / 1000.0, ci.current());
    }

    /**
     *
     * @param javaVersion Version of java needed to launch your launcher
     * @return true if user have the good java version passed in parameter (like if he needs java 8 you need to write Util.checkJavaVersion("1.8");
     */
    public static boolean checkJavaVersion(final String javaVersion) {
        if (!System.getProperty("java.version").contains(javaVersion)) {
            return false;
        } else {
            return System.getProperty("sun.arch.data.model").contains("64");
        }
    }
}
