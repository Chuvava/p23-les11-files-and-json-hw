package guru.qa.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import guru.qa.utils.FileType;
import guru.qa.utils.ZipUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class CheckFilesContentTest {

    private static ClassLoader cl = CheckFilesContentTest.class.getClassLoader();
    private final static String ZIP_PATH = "files.zip";

    @Test
    void checkZipArchiveContainsExpectedFiles() throws Exception {

        List<String> expectedListOfFiles = Arrays.asList("Clients.xlsx", "ClientsCsv.csv", "SimplePdfFile.pdf");
        List<String> actualListOfFiles = ZipUtils.getListOfFileNamesInArchive(ZIP_PATH);

        Assertions.assertIterableEquals(expectedListOfFiles, actualListOfFiles);
    }

    @Test
    void checkPdfFileContent() throws Exception {
        PDF pdfFile = (PDF) ZipUtils.getFileFromZipByNameAndType(ZIP_PATH, "SimplePdfFile.pdf", FileType.PDF);

        Assertions.assertTrue(pdfFile.text.contains("A Simple PDF File"));
    }

    @Test
    void checkXlsxFileContent() throws Exception {
        XLS xlsFile = (XLS) ZipUtils.getFileFromZipByNameAndType(ZIP_PATH, "Clients.xlsx", FileType.XLSX);

        Assertions.assertEquals("Petrov", xlsFile.excel.getSheetAt(0).getRow(3).getCell(2).toString());
        Assertions.assertEquals("Kebab", xlsFile.excel.getSheetAt(0).getRow(4).getCell(2).toString());
        Assertions.assertEquals("Maklouski", xlsFile.excel.getSheetAt(0).getRow(5).getCell(2).toString());
    }

    @Test
    void checkCsvFileContent() throws Exception {

        List<String[]> allDataFromCsv = (List<String[]>) ZipUtils.getFileFromZipByNameAndType(ZIP_PATH, "ClientsCsv.csv", FileType.CSV);

        Assertions.assertEquals(4, allDataFromCsv.size());
        Assertions.assertArrayEquals(
                new String[] {"Client Name", "Surname", "Age", "Country"},
                allDataFromCsv.get(0)
        );
        Assertions.assertArrayEquals(
                new String[] {"1", "Mike", "Petrov", "23", "Ukraine"},
                allDataFromCsv.get(1)
        );
        Assertions.assertArrayEquals(
                new String[] {"2", "Zahir", "Kebab", "25", "Bangladesz"},
                allDataFromCsv.get(2)
        );
        Assertions.assertArrayEquals(
                new String[] {"3", "Arek", "Maklouski", "19", "Poland"},
                allDataFromCsv.get(3)
        );
    }


}
