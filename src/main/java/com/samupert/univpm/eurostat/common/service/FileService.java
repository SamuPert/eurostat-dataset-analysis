package com.samupert.univpm.eurostat.common.service;

import com.samupert.univpm.eurostat.exception.DownloadFileException;
import com.samupert.univpm.eurostat.exception.GunzipFileException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.zip.GZIPInputStream;

@Service
@AllArgsConstructor
@Slf4j
public class FileService {

    private final RestTemplate restTemplate;

    /**
     * Downloads the file from the provided url to a temporary file.
     *
     * @param url The URL of the file to download.
     * @param prefix The prefix of the temporary file.
     * @param suffix The suffix of the temporary file.
     * @return The downloaded File.
     *
     * @throws DownloadFileException Thrown when the download fails.
     */
    public File download(String url, String prefix, String suffix) throws DownloadFileException {

        log.debug("Downloading file from {}", url);

        try{
            File outputFile = restTemplate.execute(url, HttpMethod.GET, null, clientHttpResponse -> {
                File ret = File.createTempFile(prefix, suffix);
                StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
                log.debug("Downloaded file to {}", ret.getAbsolutePath());
                return ret;
            });

            if (outputFile == null) throw new DownloadFileException(url);

            return outputFile;

        }catch(RestClientException e){
            throw new DownloadFileException(url);
        }
    }


    /**
     * Extract the provided zipped file to a temporary file.
     *
     * @param zippedFile The zipped file to extract.
     * @param prefix The prefix of the temporary file.
     * @param suffix The suffix of the temporary file.
     * @return The extracted file.
     *
     * @throws FileNotFoundException Thrown when the provided file does not exist.
     * @throws GunzipFileException Thrown when there's an error in the extraction process.
     */
    public File gunzipFile(File zippedFile, String prefix, String suffix
    ) throws FileNotFoundException, GunzipFileException {
        log.debug("Gunzip file: {}", zippedFile.getAbsolutePath());

        try (GZIPInputStream zis = new GZIPInputStream(new FileInputStream(zippedFile))) {
            File outputFile = File.createTempFile(prefix, suffix);
            StreamUtils.copy(zis, new FileOutputStream(outputFile));
            log.debug("Gunzip completed to: {}", outputFile.getAbsolutePath());
            return outputFile;
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw new GunzipFileException(zippedFile.getAbsolutePath());
        }
    }

}
