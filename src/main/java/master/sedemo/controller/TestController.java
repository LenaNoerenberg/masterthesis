package master.sedemo.controller;

import master.sedemo.model.Test;
import master.sedemo.repository.TestRepository;
import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("test/")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    // Toggles
    private static final String CUSTOMER_TEST_LEVEL_1 = "Customer_100_ConstructorTest";
    private static final String CUSTOMER_TEST_LEVEL_2 = "Customer_200_SetIdTest";
    private static final String CUSTOMER_TEST_LEVEL_3 = "Customer_300_SetNameTest";
    private static final String CUSTOMER_TEST_LEVEL_4 = "Customer_400_ContactsTest";
    private static final String CUSTOMER_TEST_LEVEL_5 = "Customer_500_SetNameExtendedTest";
    private static final String ARTICLE_TEST_LEVEL_1 = "Article_100_ConstructorTest";
    private static final String ARTICLE_TEST_LEVEL_2 = "Article_200_SetIdTest";
    private static final String ARTICLE_TEST_LEVEL_3 = "Article_300_DescriptionTest";
    private static final String ARTICLE_TEST_LEVEL_4 = "Article_400_UnitPriceTest";
    private static final String ARTICLE_TEST_LEVEL_5 = "Article_500_CurrencyTest";
    private static final String ARTICLE_TEST_LEVEL_6 = "Article_600_TAXTest";

    //JPA Instanz - für Tests
    private final TestRepository testRepository;

    boolean isWindows = System.getProperty("os.name")
            .toLowerCase().startsWith("windows");
    //FF4J Instanz um Funktionen wie zb check() verwenden zu können
    @Autowired
    private FF4j ff4j;
    //Konstruktor
    public TestController(TestRepository testRepository) {
        // initialisieren des TestRepositorys und der FF4J Instanz
        this.testRepository = testRepository;
        ff4j = initFF4j();
    }


    public FF4j initFF4j() {
        return new FF4j("ff4j-features.xml");
    }

    //Hilfe für Test Namen im gleichen Verzeichnes
    private static String[] getAllFiles(File curDir) {

        File[] filesList = curDir.listFiles();
        List<String> allFiles = new ArrayList<String>();
        for(File f : filesList){
            if(f.isFile()){
                String name = f.getName();
                System.out.println(name);
                allFiles.add(name);
            }
        }
        String[] returnArr = new String[allFiles.size()];
        returnArr = allFiles.toArray(returnArr);
        return returnArr;
    }

    // Funktion zum Erstellen eines Surefire Reports
    private static void generateSurefireHTML() {
        boolean isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows");
        File f = new File("./");
        getAllFiles(f);
        ProcessBuilder processBuilder = new ProcessBuilder();

        String command = "mvn -Dmaven.test.skip=true surefire-report:report";
        System.out.println("-----------------------");
        System.out.println(command);
        processBuilder.command("/bin/sh", "-c", command);
        if (isWindows) {
            System.out.println("windows");

            processBuilder.command("cmd.exe", "/c", command);
        } else {
            processBuilder.command("/bin/sh", "-c", command);

        }
        try {

            Process process = processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Checken eines einzelnen Features
    @RequestMapping(value = "/featureCheck/{uid}", method = RequestMethod.GET)
    public ResponseEntity getFeatureCheck(@PathVariable String uid){ //(@RequestParam("uid") String uid) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "http://localhost:3000");
        Boolean featureCheck = ff4j.check(uid);
        System.out.println("DAAA   " + featureCheck);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(featureCheck);
    }

    // Ausführen aller in FF4J als aktiv markierten Tests
    @RequestMapping(value = "/executeTests", method = RequestMethod.POST)
    public ResponseEntity executeTests() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "http://localhost:3000");
        System.out.println("About to execute tests");
        Map<String, Feature> featuresMAp = ff4j.getFeatures();
        System.out.println(featuresMAp);
        StringBuilder features = new StringBuilder();
        System.out.println(ff4j.check(CUSTOMER_TEST_LEVEL_1));
        List<String>testLevel = new ArrayList<String>();
        if (ff4j.check(CUSTOMER_TEST_LEVEL_1)) {
            testLevel.add(CUSTOMER_TEST_LEVEL_1);
            if(features.length()>0) {
                features.append(",Customer_100_ConstructorTest");
            }else{
                features.append("Customer_100_ConstructorTest");
            }
        }
        if (ff4j.check(CUSTOMER_TEST_LEVEL_2)) {
            testLevel.add(CUSTOMER_TEST_LEVEL_2);
            if(features.length()>0) {
                features.append(",Customer_200_SetIdTest");
            } else {
                features.append("Customer_200_SetIdTest");
            }
        }
        if (ff4j.check(CUSTOMER_TEST_LEVEL_3)) {
            testLevel.add(CUSTOMER_TEST_LEVEL_3);
            if(features.length()>0) {
                features.append(",Customer_300_SetNameTest");
            } else {
                features.append("Customer_300_SetNameTest");
            }
        }
        if (ff4j.check(CUSTOMER_TEST_LEVEL_4)) {
            testLevel.add(CUSTOMER_TEST_LEVEL_4);
            if(features.length()>0) {
                features.append(",Customer_400_ContactsTest");
            } else {
                features.append("Customer_400_ContactsTest");
            }
        }
        if (ff4j.check(CUSTOMER_TEST_LEVEL_5)) {
            testLevel.add(CUSTOMER_TEST_LEVEL_5);
            if(features.length()>0) {
                features.append(",Customer_500_SetNameExtendedTest");
            }else{
                features.append("Customer_500_SetNameExtendedTest");
            }
        }
        if (ff4j.check(ARTICLE_TEST_LEVEL_1)) {
            testLevel.add(ARTICLE_TEST_LEVEL_1);
            if(features.length()>0) {
                features.append(",Article_100_ConstructorTest");
            } else {
                features.append("Article_100_ConstructorTest");
            }
        }
        if (ff4j.check(ARTICLE_TEST_LEVEL_2)) {
            testLevel.add(ARTICLE_TEST_LEVEL_2);
            if(features.length()>0) {
                features.append(",Article_200_SetIdTest");
            } else {
                features.append("Article_200_SetIdTest");
            }
        }
        if (ff4j.check(ARTICLE_TEST_LEVEL_3)) {
            testLevel.add(ARTICLE_TEST_LEVEL_3);
            if(features.length()>0) {
                features.append(",Article_300_DescriptionTest");
            } else {
                features.append("Article_300_DescriptionTest");
            }
        }
        if (ff4j.check(ARTICLE_TEST_LEVEL_4)) {
            testLevel.add(ARTICLE_TEST_LEVEL_4);
            if(features.length()>0) {
                features.append(",Article_400_UnitPriceTest");
            } else {
                features.append("Article_400_UnitPriceTest");
            }
        }
        if (ff4j.check(ARTICLE_TEST_LEVEL_5)) {
            testLevel.add(ARTICLE_TEST_LEVEL_5);
            if(features.length()>0) {
                features.append(",Article_500_CurrencyTest");
            } else {
                features.append("Article_500_CurrencyTest");
            }
        }
        if (ff4j.check(ARTICLE_TEST_LEVEL_6)) {
            testLevel.add(ARTICLE_TEST_LEVEL_6);
            if(features.length()>0) {
                features.append(",Article_600_TAXTest");
            } else {
                features.append("Article_600_TAXTest");
            }
        }

        //Erstellen eines Kommandozeilenbefehls für die Ausführung aller Tests
        ProcessBuilder processBuilder = new ProcessBuilder();
        String command = "mvn surefire:test -Dtest="+features.toString();

        if (isWindows) {
            System.out.println("windows");

            processBuilder.command("cmd.exe", "/c", command);
        } else {

            processBuilder.command("/bin/sh","-c", command);
        }

        try {

            Process process = processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);
            generateSurefireHTML();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .headers(responseHeaders)
                    .body(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .headers(responseHeaders)
                    .body(e);
        }

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(features);
    }

    // Ausführen eines einzelnen Tests
    @RequestMapping(value = "/executeTest/{uid}", method = RequestMethod.GET)
    public ResponseEntity getExecuteTest(@PathVariable String uid){ //(@RequestParam("uid") String uid) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "http://localhost:3000");
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (isWindows) {
            processBuilder.command("cmd.exe", "/c", "-c", "mvn surefire:test -Dtest="+uid);
        } else {
            processBuilder.command("bash", "-c", "mvn surefire:test -Dtest="+uid);
        }

        try {

            Process process = processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);
            generateSurefireHTML();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("Ping send.");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("done");
    }


    // Rückgabe aller vorhandenen Tests
    @GetMapping("all")
    public ResponseEntity getTests(){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "http://localhost:3000");
        List<Test> response = testRepository.findAll();
        for (Test test: response) {
            boolean activ = ff4j.check(test.getUid());
            test.setHasFeatureEnabled(activ);
        }
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(response);
    }

    // Rückgabe einer HTML Datei zur Darstellung des Reports
    @RequestMapping(value = "/showResults", method = RequestMethod.GET)
    public ResponseEntity showResults() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "http://localhost:3000");

        String content = "";
        try{
            content = new String(Files.readAllBytes(Paths.get("./target/surefire-reports/junit-noframes.html")), "UTF-8");
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(content);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(content);


    }
}
