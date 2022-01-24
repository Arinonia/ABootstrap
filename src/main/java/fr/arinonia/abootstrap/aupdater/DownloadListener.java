package fr.arinonia.abootstrap.aupdater;

public interface DownloadListener {
    /**
     * call when your job is full downloaded
     * @param job DownloadJob
     */
    void onDownloadJobFinished(final DownloadJob job);

    /**
     * call for each file downloaded
     * @param job DownloadJob
     */
    void onDownloadJobProgressChanged(final DownloadJob job);

    /**
     * call when your job start to download
     * @param job DownloadJob
     */
    void onDownloadJobStarted(final DownloadJob job);
}
