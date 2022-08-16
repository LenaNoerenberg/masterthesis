package org.lena.noerenberg.controller;

import org.lena.noerenberg.model.Test;
import org.lena.noerenberg.repository.TestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.List;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("test/")
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    // feature toggle for test a4
    private static final String FEATURE_A4 = "A4";
    private final TestRepository testRepository;

    boolean isWindows = System.getProperty("os.name")
            .toLowerCase().startsWith("windows");

    public TestController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @GetMapping("all")
    public List<Test> getTests(){ return testRepository.findAll(); }


    @PostMapping("execute")
    public ResponseEntity executeTest() throws URISyntaxException {
        System.out.println("Test in Post");
//        Test currentTest = testRepository.findById(id).orElseThrow(RuntimeException::new);
        ProcessBuilder processBuilder = new ProcessBuilder();
//        if (isWindows) {
//            processBuilder.command("cmd.exe", "/c", "ping -n 3 google.com");
//        } else {
//            processBuilder.command("bash", "-c", "ping -c 3 google.com");
//        }

        // Run this on Windows, cmd, /c = terminate after this run
        processBuilder.command("bash", "-c", "mvn -Dtest=PlayNumbersTest test");

        try {

            Process process = processBuilder.start();

            // blocked :(
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
        LOGGER.info("Ping send.");

        return ResponseEntity.ok("currentTest");
    }
}
