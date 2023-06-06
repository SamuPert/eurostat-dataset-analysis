package com.samupert.univpm.eurostat.exception;

public class GunzipFileException extends Throwable {
    public GunzipFileException(String filepath) {
        super(String.format("Can't extract file \"%s\"", filepath));
    }
}
