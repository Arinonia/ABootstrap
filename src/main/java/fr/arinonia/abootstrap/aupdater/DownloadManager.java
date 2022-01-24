package fr.arinonia.abootstrap.aupdater;

import java.io.File;

public class DownloadManager {

    private final String url;
    private final DownloadJob job;
    private final File file;

    /**
     *
     * @param url of your json
     * @param job who be downloaded
     * @param file where the job is downloaded
     */
    public DownloadManager(String url, DownloadJob job, File file) {
        this.url = url;
        this.job = job;
        this.file = file;
    }

    /**
     *
     * @return your download url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @return the job who be downloaded
     */
    public DownloadJob getJob() {
        return job;
    }

    /**
     *
     * @return the folder where your job will be downloaded
     */
    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "DownloadManager{" +
                "url='" + url + '\'' +
                ", job=" + job +
                ", file=" + file +
                '}';
    }
}
