package fr.arinonia.abootstrap.utils;

/**
 * @author Arinonia
 **/
public enum OperatingSystem {

    LINUX("linux", new String[] { "linux", "unix" }),
    WINDOWS("windows", new String[] { "win" }),
    MACOS("mac", new String[] { "macos", "osx", "mac" }),
    UNKNOWN("unknown", new String[0]);

    private final String name;

    private final String[] aliases;

    OperatingSystem(String name, String[] aliases) {
        this.name = name;
        this.aliases = (aliases == null) ? new String[0] : aliases;
    }


    public static OperatingSystem getCurrentPlatform() {
        String osName = System.getProperty("os.name").toLowerCase();
        for (OperatingSystem os : values()) {
            for (String alias : os.getAliases()) {
                if (osName.contains(alias))
                    return os;
            }
        }
        return UNKNOWN;
    }


    public String getName() {
        return this.name;
    }

    private String[] getAliases() {
        return this.aliases;
    }

    public boolean isSupported() {
        return (this != UNKNOWN);
    }
}
