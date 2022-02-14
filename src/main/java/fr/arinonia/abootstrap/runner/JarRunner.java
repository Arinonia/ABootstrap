package fr.arinonia.abootstrap.runner;

import fr.arinonia.abootstrap.utils.OperatingSystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Arinonia
 * Created at 16/01/2022 - 16:50
 **/
public class JarRunner {

    private String javaCommand;
    private String[] args = new String[0];

    /**
     *
     * @param runner Write your main class and your class path
     * @return the process who be launch
     * @throws JarRunnerException if the process can be started
     */
    public Process launch(final Runner runner) throws JarRunnerException {

        final ProcessBuilder processBuilder = new ProcessBuilder();
        final List<String> commands = new ArrayList<>();

        commands.add(this.getJavaCommand());
        commands.addAll(Arrays.asList(this.getSpecialArgs()));

        commands.add("-cp");
        commands.add(runner.getClassPath());
        commands.add(runner.getMainClass());
        commands.addAll(Arrays.asList(this.getArgs()));
        processBuilder.command(commands);

        final StringBuilder entireCommand = new StringBuilder();
        for (final String command : commands)
            entireCommand.append(command).append(" ");

        System.out.println(entireCommand);

        try {
            return processBuilder.start();
        } catch (IOException e) {
            throw new JarRunnerException("Cannot launch !", e);
        }
    }

    /**
     *
     * @param javaCommand java path
     */
    public void setJavaCommand(final String javaCommand) {
        this.javaCommand = javaCommand;
    }

    /**
     *
     * @return java path
     */
    private String getJavaCommand() {
        if (this.javaCommand == null) {
            File java;
            if (OperatingSystem.getCurrentPlatform() == OperatingSystem.WINDOWS) {
                java = new File(System.getProperty("java.home"), "bin" + File.separator + "javaw");
            } else {
                java = new File(System.getProperty("java.home"), "bin" + File.separator + "java");
            }
            return java.getAbsolutePath();
        } else {
            return this.javaCommand;
        }
    }

    /**
     *
     * @return -XX:-UseAdaptiveSizePolicy: asking the JVM to adjust the heap size dynamically at runtime<br>
     * -XX:+UseConcMarkSweepGC: see more here https://docs.oracle.com/javase/8/docs/technotes/guides/vm/gctuning/cms.html
     */
    private String[] getSpecialArgs() {
        return new String[]{"-XX:-UseAdaptiveSizePolicy", "-XX:+UseConcMarkSweepGC"};
    }

    public void setArgs(final String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return this.args;
    }
}
