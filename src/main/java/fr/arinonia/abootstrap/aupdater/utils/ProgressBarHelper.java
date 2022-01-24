package fr.arinonia.abootstrap.aupdater.utils;

import java.util.concurrent.atomic.AtomicLong;

public class ProgressBarHelper {

    public static AtomicLong downloadedBytes = new AtomicLong();
    private static long totalBytesNeedToDownload = 0;

    public static void incrementAndGetDownloadedBytes() {
        downloadedBytes.incrementAndGet();
    }

    public static long getDownloadedBytes() {
        return downloadedBytes.get();
    }

    public static long getTotalBytesNeedToDownload() {
        return totalBytesNeedToDownload;
    }
    public static void incrementTotalBytesNeedToDownload(final long size) {
        totalBytesNeedToDownload += size;
    }
}
