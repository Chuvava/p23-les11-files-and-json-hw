package guru.qa.utils;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import guru.qa.tests.CheckFilesContentTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {

    private static ClassLoader cl = CheckFilesContentTest.class.getClassLoader();

    public static List<String> getListOfFileNamesInArchive(String zipPath) throws IOException {
        List<String> actualListOfFiles = new ArrayList<>();
        try(ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream(zipPath))) {
            ZipEntry entry;

            while((entry = zis.getNextEntry()) != null) {
                System.out.println(entry.getName());
                actualListOfFiles.add(entry.getName());
            }
        }

        return actualListOfFiles;
    }

    public static Object getFileFromZipByNameAndType(String zipPath, String fileName, FileType fileType) throws Exception {

        try(ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream(zipPath))) {
            ZipEntry entry;

            while((entry = zis.getNextEntry()) != null) {
                if(entry.getName().equals(fileName)) {
                    switch (fileType) {
                        case PDF:
                            return new PDF(zis);
                        case XLS:
                        case XLSX:
                            return new XLS(zis);
                        case CSV:
                            CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                            return csvReader.readAll();
                    }
                }
            }
        }

        throw new FileNotFoundException(String.format("File '%s' with type '%s' was NOT found!", fileName, fileType.name()));
    }
}
