/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.kaisar.geolocation.services;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import lombok.Getter;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import nl.kaisar.geolocation.config.GlobalVariables;
import nl.kaisar.geolocation.model.UKPostCode;
import nl.kaisar.geolocation.repo.UKPostCodeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kaisar
 */
public class CsvDataFillManager {

    UKPostCodeRepo postCodeRepo;

    @Getter
    private boolean inProgress = false;

    @Getter
    private int numberOfPostCodeAdded;

    private DataFiller dataFiller;
    public String DOWNLOAD_URL = GlobalVariables.DOWNLOAD_URL;
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvDataFillManager.class);

    private CsvDataFillManager() {
    }

    public int fillData(String url, UKPostCodeRepo postCodeRepo) {
        if (inProgress) {
            throw new RuntimeException("Process busy!!!");
        }
        if (url != null) {
            DOWNLOAD_URL = url;
        }
        this.inProgress = true;
        LOGGER.info("Data fill is starting ....");
        this.postCodeRepo = postCodeRepo;
        this.dataFiller = new DataFiller();
        Thread threadedDataFiller = new Thread(this.dataFiller);
        threadedDataFiller.start();
        return this.numberOfPostCodeAdded;

    }

    public static CsvDataFillManager getInstance() {
        return CsvDataFillManagerHolder.INSTANCE;
    }

    private static class CsvDataFillManagerHolder {

        private static final CsvDataFillManager INSTANCE = new CsvDataFillManager();
    }

    private class DataFiller implements Runnable {

        public String doDownload(String url) throws MalformedURLException, IOException {
            LOGGER.info("Start downloading!!!!");
            URL website = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            File myFile = new File(url.split("/")[url.split("/").length - 1]);
            FileOutputStream fos = new FileOutputStream(myFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            System.out.println(myFile.getAbsolutePath());
            return myFile.getAbsolutePath();
        }

        public String unzip(String source, String destination) {

            LOGGER.info("Start unziping!!!!");
            String fileName = "";
            try {
                ZipFile zipFile = new ZipFile(source);
                for (FileHeader fileHeader : (List<FileHeader>) zipFile.getFileHeaders()) {
                    if (fileHeader.getFileName().contains(".csv")) {
                        zipFile.extractFile(fileHeader, destination);
                        fileName = fileHeader.getFileName();
                    }
                }
            } catch (ZipException e) {
                e.printStackTrace();
            }
            return fileName;
        }

        @Override
        public void run() {
            LOGGER.info("Started data fill...");
            try {
                String downloadedPath = this.doDownload(DOWNLOAD_URL);
                File target = new File(downloadedPath);
                String fileName = this.unzip(downloadedPath, target.getParent());

                BeanListProcessor<UKPostCode> rowProcessor = new BeanListProcessor<>(UKPostCode.class);

                CsvParserSettings parserSettings = new CsvParserSettings();
                parserSettings.setRowProcessor(rowProcessor);
                parserSettings.setHeaderExtractionEnabled(true);

                CsvParser parser = new CsvParser(parserSettings);
                parser.parse(new File(target.getParent() + "/" + fileName));

                List<UKPostCode> beans = rowProcessor.getBeans();
                numberOfPostCodeAdded = beans.size();
                LOGGER.info("DB operation ongoing...");
                postCodeRepo.saveAll(beans);
                inProgress = false;

                LOGGER.info("Data fill done!!!");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
