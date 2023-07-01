package com.samupert.univpm.eurostat.exception;

/**
 * Exception thrown when a file can't be downloaded
 */
public class DownloadFileException extends Throwable {
    /**
     * Create a new {@link DownloadFileException}.
     *
     * @param url The url of the file that can't be downloaded.
     */
    public DownloadFileException(String url) {
        super(String.format("Can't download file from \"%s\"", url));
    }
}
