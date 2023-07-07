package com.samupert.univpm.eurostat.exception;

/**
 * Exception thrown when the extraction of a file fails.
 */
public class GunzipFileException extends Throwable {
    /**
     * Create a new {@link GunzipFileException}.
     *
     * @param filepath The path of the file that couldn't be extracted
     */
    public GunzipFileException(String filepath) {
        super(String.format("Can't extract file \"%s\"", filepath));
    }
}
