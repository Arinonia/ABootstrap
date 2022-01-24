package fr.arinonia.abootstrap.aupdater.jsonmodels;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private DataMaintenance maintenance;
    private final List<DataFile> files = new ArrayList<>();
    private final List<String> ignoredFiles = new ArrayList<>();

    /**
     *
     * @return maintenance
     */
    public DataMaintenance getMaintenance() {
        return this.maintenance;
    }

    /**
     *
     * @return all files
     */
    public List<DataFile> getFiles() {
        return  this.files;
    }

    /**
     *
     * @return all ignored files (don't deleted by updater)
     */
    public List<String> getIgnoredFiles() {
        return  this.ignoredFiles;
    }

}
