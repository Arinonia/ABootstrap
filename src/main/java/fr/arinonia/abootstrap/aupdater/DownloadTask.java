package fr.arinonia.abootstrap.aupdater;

import fr.arinonia.abootstrap.aupdater.utils.ProgressBarHelper;
import fr.arinonia.abootstrap.aupdater.utils.Util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask {

    private final URL url;
    private final File destination;
    private final String hashServer;

    private int nunAttempts = 0;

    public DownloadTask(final URL url, final File destination, final String hashServer) {
        this.url = url;
        this.destination = destination;
        this.hashServer = hashServer;
    }

    public String download() throws IOException {
        String hashLocal = null;
        nunAttempts++;
        destination.getParentFile().mkdirs();
        if (destination.isFile())
            hashLocal = Util.getMD5(destination);

        if (destination.isFile() && !destination.canWrite())
            throw new RuntimeException("Do not have write permissions for " + destination + " - aborting!");

        try {
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");

            final int status = connection.getResponseCode();

            if (status / 100 == 2) {
                DataInputStream dis = new DataInputStream(connection.getInputStream());

                byte[] fileData = new byte[connection.getContentLength()];

                for (int x = 0; x < fileData.length; x++) {
                    ProgressBarHelper.incrementAndGetDownloadedBytes();
                    fileData[x] = dis.readByte();
                }

                dis.close();

                BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(destination));
                fos.write(fileData);

                fos.close();

                hashLocal = Util.getMD5(destination);

                if(hashServer.contains("-"))
                    return "Didn't have hash so assuming our copy is good";
                if(hashServer.equalsIgnoreCase(hashLocal))
                    return "Downloaded successfully and hash matched";
                throw new IOException(String.format("Hash did not match downloaded MD5 (Hash was %s, downloaded %s)", hashServer, hashLocal));
            }

            if(destination.isFile())
                return "Couldn't connect to server (responded with " + status + ") but have local file, assuming it's good";
            throw new IOException("Server responded with " + status);
        }
        catch (IOException e) {
            if(destination.isFile())
                return "Couldn't connect to server (" + e.getClass().getSimpleName() + ": '" + e.getMessage() + "') but have local file, assuming it's good";
            throw e;
        }
    }

    public URL getUrl() {
        return this.url;
    }

    public File getDestination() {
        return this.destination;
    }

    public String getHashServer() {
        return this.hashServer;
    }

    public int getNunAttempts() {
        return this.nunAttempts;
    }

    @Override
    public String toString() {
        return "DownloadTask{" +
                "url=" + url +
                ", destination=" + destination +
                ", hashServer='" + hashServer + '\'' +
                ", nunAttempts=" + nunAttempts +
                '}';
    }
}
