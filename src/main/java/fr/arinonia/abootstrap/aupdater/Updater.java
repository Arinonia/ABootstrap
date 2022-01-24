package fr.arinonia.abootstrap.aupdater;

import com.google.gson.Gson;
import fr.arinonia.abootstrap.aupdater.jsonmodels.Data;
import fr.arinonia.abootstrap.aupdater.jsonmodels.DataFile;
import fr.arinonia.abootstrap.aupdater.utils.ProgressBarHelper;
import fr.arinonia.abootstrap.aupdater.utils.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Updater {

    private static final Gson GSON = new Gson();
    private final List<String> ignoredFiles = Collections.synchronizedList(new ArrayList<String>());
    private final Queue<DownloadManager> needToDownload = new LinkedList<>();
    private boolean fileDeleter = false;
    private boolean isInMaintenance = false;

    public void start() {
        int lenght = this.needToDownload.size();

        for (int i = 0; i < lenght; i++) {
            DownloadManager downloadManager = this.needToDownload.poll();

            if (!downloadManager.getFile().exists()) {
                if (!downloadManager.getFile().mkdirs()) {
                    System.err.println("Impossible to create folder " + downloadManager.getFile().getName());
                }
            }

            this.jsonDeserialization(this.getContentURL(downloadManager.getUrl()), downloadManager.getJob(), downloadManager.getFile());
            downloadManager.getJob().getExecutorService().shutdown();
            try {
                downloadManager.getJob().getExecutorService().awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void jsonDeserialization(final String jsonContent, DownloadJob job, File folder) {
        Data data = GSON.fromJson(jsonContent, Data.class);

        System.out.println("Maintenance: " + data.getMaintenance().isMaintenance());
        System.out.println("Maintenance message: " + data.getMaintenance().getMessage());

        if (data.getMaintenance().isMaintenance()) {
            this.isInMaintenance = true;
        }

        for (final DataFile file : data.getFiles()) {
            final File client_files = new File(folder, file.getPath());

            if (!client_files.exists() || !client_files.isFile()) {
                job.addDownloadable(client_files, file.getHash(), file.getUrl());
                ProgressBarHelper.incrementTotalBytesNeedToDownload(file.getSize());
                this.ignoredFiles.add(client_files.getAbsolutePath());
                continue;
            }
            try {
                if (!Util.getMD5(client_files).equals(file.getHash())) {
                    job.addDownloadable(client_files, file.getHash(), file.getUrl());
                    ProgressBarHelper.incrementTotalBytesNeedToDownload(file.getSize());
                    this.ignoredFiles.add(client_files.getAbsolutePath());
                } else {
                    this.ignoredFiles.add(client_files.getAbsolutePath());
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        for (final String ignore : data.getIgnoredFiles()) {
            File file = new File(folder, ignore);//TODO check recursively
            if (file.isDirectory()) {
                ArrayList<File> files = this.listFiles(file);
                for (final File f : files) {
                    this.ignoredFiles.add(f.getAbsolutePath());
                }
            } else {
                this.ignoredFiles.add(file.getAbsolutePath());
            }
        }

        if (this.fileDeleter) {
            this.checkingFiles(folder);
        }

        if (!this.isInMaintenance) {
            System.out.println("start downloading");
            job.startDownloading(job.getExecutorService());
        } else {
            System.err.println("Impossible to start updater, maintenance enable");
        }
    }

    private String getContentURL(final String theUrl) {
        final StringBuilder content = new StringBuilder();

        try {
            final URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            String redirect = urlConnection.getHeaderField("Location");

            if (redirect != null){
                urlConnection = new URL(redirect).openConnection();
            }

            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        if (!content.toString().startsWith("{")) {
            System.err.println(content);
            System.err.println("Bad json, can't parse your json");
        }
        return content.toString();
    }

    private ArrayList<File> listFiles(final File folder) {
        File[] files = folder.listFiles();
        ArrayList<File> list = new ArrayList<>();

        if (files == null) {
            return list;
        }

        for (final File f : files) {
            if (f.isDirectory()) {
                list.addAll(this.listFiles(f));
            } else {
                list.add(f);
            }
        }
        return list;
    }

    private boolean isIgnored(final File file) {
        for (final String path : this.ignoredFiles) {
            if (path.equals(file.getAbsolutePath())) {
                return true;
            }
        }
        return false;
    }


    private void checkingFiles(final File folder) {
        ArrayList<File> files = this.listFiles(folder);

        for (final File file : files) {
            if (!this.isIgnored(file)) {
                if(!file.delete()) {
                    System.err.println("Impossible to delete " + file.getName());
                }
            }
        }
    }

    public void addJobToDownload(final DownloadManager downloadManager){
        this.needToDownload.add(downloadManager);
    }

    public void setFileDeleter(final boolean fileDeleter) {
        this.fileDeleter = fileDeleter;
    }

    public List<String> getIgnoredFiles() {
        return this.ignoredFiles;
    }

    public Queue<DownloadManager> getNeedToDownload() {
        return this.needToDownload;
    }

    public boolean isInMaintenance() {
        return this.isInMaintenance;
    }

    @Override
    public String toString() {
        return "Updater{" +
                "ignoredFiles=" + ignoredFiles +
                ", needToDownload=" + needToDownload +
                ", fileDeleter=" + fileDeleter +
                ", isInMaintenance=" + isInMaintenance +
                '}';
    }
}
