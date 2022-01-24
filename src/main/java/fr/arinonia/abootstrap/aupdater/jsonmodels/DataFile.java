package fr.arinonia.abootstrap.aupdater.jsonmodels;

public class DataFile {

    private String path;
    private String hash;
    private String url;
    private long size;

    /**
     *
     * @param path client path
     * @param hash of file
     * @param url for download the file
     * @param size bytes
     */
    public DataFile(final String path, final String hash, final String url, final long size) {
        this.path = path;
        this.hash = hash;
        this.url = url;
        this.size = size;
    }

    /**
     *
     * @return the client path
     */
    public String getPath() {
        return this.path;
    }

    /**
     *
     * @return the file hash
     */
    public String getHash() {
        return this.hash;
    }

    /**
     *
     * @return downloadable url for resource
     */
    public String getUrl() {
        return this.url;
    }

    /**
     *
     * @return the size of file
     */
    public long getSize() {
        return this.size;
    }

    /**
     *
     * @param path of file
     */
    public void setPath(final String path) {
        this.path = path;
    }

    /**
     *
     * @param hash of file
     */
    public void setHash(final String hash) {
        this.hash = hash;
    }

    /**
     *
     * @param url for download the file
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     *
     * @param size of file
     */
    public void setSize(final long size) {
        this.size = size;
    }
}
