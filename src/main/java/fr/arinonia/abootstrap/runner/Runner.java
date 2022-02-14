package fr.arinonia.abootstrap.runner;

/**
 * @author Arinonia
 * Created at 16/01/2022 - 16:50
 **/
public class Runner {

    private final String mainClass;
    private final String classPath;

    /**
     *
     * @param mainClass main class of your jar like fr.arinonia.launcher.Main
     * @param classPath all the class path like C:\azauth-1.0-SNAPSHOT.jar;C:\fontawesomefx-8.9.jar;C:\gson-2.8.9.jar;C:\jfoenix-8.0.8.jar;C:\launcher.jar
     */
    public Runner(final String mainClass, final String classPath) {
        this.mainClass = mainClass;
        this.classPath = classPath;
    }

    /**
     *
     * @return your main class
     */
    public String getMainClass() {
        return this.mainClass;
    }

    /**
     *
     * @return your class path
     */
    public String getClassPath() {
        return this.classPath;
    }
}
