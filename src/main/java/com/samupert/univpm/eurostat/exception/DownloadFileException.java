package com.samupert.univpm.eurostat.exception;

public class DownloadFileException extends Throwable {
    public DownloadFileException(String url) {
        super(String.format("Can't download file from \"%s\"", url));
    }
}
