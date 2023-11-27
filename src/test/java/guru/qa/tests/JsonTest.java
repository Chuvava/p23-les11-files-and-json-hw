package guru.qa.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class JsonTest {

    private ClassLoader cl = JsonTest.class.getClassLoader();

    @Test
    void jsonParsingTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // objectMapper.readTree(new File("C:\\StudyRepository\\2023\\qa-guru\\23-potok\\home-works\\p23-les11-files-hw\\src\\test\\resources\\student.json"))

        try(InputStream is = cl.getResourceAsStream("student.json")) {
            JsonNode rootNode = objectMapper.readTree(is);
            JsonNode certificatesNode = rootNode.get("certificates");
            JsonNode departmentNode = rootNode.get("department");

            Assertions.assertEquals(2, rootNode.get("id").asInt());
            Assertions.assertEquals("Ali Z", rootNode.get("name").asText());
            Assertions.assertEquals("Computer Science", departmentNode.get("name").asText());

            Assertions.assertEquals("ORACLE",
                    certificatesNode.get(0).get("name").asText());
            Assertions.assertEquals(2000,
                    certificatesNode.get(0).get("year").asInt());
            Assertions.assertEquals("SUN",
                    certificatesNode.get(1).get("name").asText());
            Assertions.assertEquals(2015,
                    certificatesNode.get(1).get("year").asInt());

        }

//        JsonNode rootNode = objectMapper.readTree(jsonFile);

    }
}
