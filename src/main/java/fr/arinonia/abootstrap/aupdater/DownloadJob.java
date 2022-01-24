package fr.arinonia.abootstrap.aupdater;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadJob {

    private final String name;
    private final DownloadListener listener;

    private final Queue<DownloadTask> remainingFiles = new ConcurrentLinkedQueue<>();
    private final List<DownloadTask> allFiles = Collections.synchronizedList(new ArrayList<>());
    private final List<DownloadTask> failures = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger remainingThreads = new AtomicInteger();

    private ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

    private boolean started;

    public DownloadJob(final String name, final DownloadListener listener) {
        this.name = name;
        this.listener = listener;
    }

    public DownloadJob(final String name) {
        this(name, null);
    }

    public void addDownloadable(final File file, final String serverHash, final String url) {
        if (this.started) {
            throw new IllegalStateException("Cannot add to download job that has already started");
        }
        DownloadTask task = null;

        try {
            task = new DownloadTask(new URL(url), file, serverHash);
        } catch (MalformedURLException ignored) {}

        this.allFiles.add(task);
        this.remainingFiles.add(task);
    }

    public void popAndDownload() {
        DownloadTask task = null;

        while ((task = this.remainingFiles.poll()) != null) {
            if (task.getNunAttempts() > 5) {
                System.err.println("Gave up trying to download " + task.getUrl() + " for job '" + name + "'");
            } else {
                try {
                    final String result = task.download();
                    System.out.println("Finished downloading " + task.getDestination() + " for job '" + name + "'" + ": " + result);
                    if (this.listener != null) {
                        this.listener.onDownloadJobProgressChanged(this);
                    }
                } catch (final IOException e) {
                    System.err.println("Couldn't download " + task.getUrl() + " for job '" + name + "'");
                    this.remainingFiles.add(task);
                }
            }
        }
        if (this.remainingThreads.decrementAndGet() <= 0) {
            if (this.listener != null) {
                this.listener.onDownloadJobFinished(this);
            }
        }
    }

    public void startDownloading(final ThreadPoolExecutor executor) {
        if (this.started) {
            throw new IllegalStateException("Cannot start download job that as already started !");
        }
        this.started = true;
        if (this.allFiles.isEmpty()) {
            System.out.println("Download job '" + name + "' skipped as there are no files to download");
            if (this.listener != null) {
                this.listener.onDownloadJobFinished(this);
            }
        } else {
            final int threads = executor.getMaximumPoolSize();
            remainingThreads.set(threads);
            System.out.println("Download job '" + name + "' started (" + threads + " threads, " + allFiles.size() + " files)");
            if (this.listener != null) {
                this.listener.onDownloadJobStarted(this);
            }
            for (int i = 0; i < threads; i++) {
                executor.submit(this::popAndDownload);
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public ThreadPoolExecutor getExecutorService() {
        return this.executorService;
    }

    public Queue<DownloadTask> getRemainingFiles() {
        return this.remainingFiles;
    }

    public int getFailures() {
        return this.failures.size();
    }

    public List<DownloadTask> getAllFiles() {
        return this.allFiles;
    }

    public void setExecutorService(final int number){
        this.executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(number);
    }
    public boolean isComplete(){
        return this.started && this.remainingFiles.isEmpty() && this.remainingThreads.get() == 0;
    }

    @Override
    public String toString() {
        return "DownloadJob{" +
                "name='" + name + '\'' +
                ", remainingFiles=" + remainingFiles +
                ", allFiles=" + allFiles +
                ", failures=" + failures +
                ", remainingThreads=" + remainingThreads +
                ", executorService=" + executorService +
                ", started=" + started +
                '}';
    }
}
