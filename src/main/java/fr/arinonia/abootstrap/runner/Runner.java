package fr.arinonia.abootstrap.runner;

/**
 * @author Arinonia
 * Created at 16/01/2022 - 16:50
 **/
public class Runner {

    private final String mainClass;
    private final String classPath;

    public Runner(final String mainClass, final String classPath) {
        this.mainClass = mainClass;
        this.classPath = classPath;
    }

    public String getMainClass() {
        return this.mainClass;
    }

    public String getClassPath() {
        return this.classPath;
    }
}
