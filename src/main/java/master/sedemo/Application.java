/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2013-2016 the original author or authors.
 */
package master.sedemo;

import master.sedemo.model.Test;
import master.sedemo.repository.TestRepository;
import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private FF4j ff4j;

    public FF4j initFF4j() {
        return new FF4j("ff4j-features.xml");
    }

    //gibt alle Filenamen in einem angegebenen Verzeichnis zurück
    private static String[] getAllFiles(File curDir) {

        File[] filesList = curDir.listFiles();
        List<String> allFiles = new ArrayList<String>();
        for(File f : filesList){
            if(f.isFile()){
                String name = f.getName();
                if(name.contains(".")) allFiles.add(name.split("\\.")[0]);
            }
        }
        String[] returnArr = new String[allFiles.size()];
        returnArr = allFiles.toArray(returnArr);
        return returnArr;
    }
    @Override
    public void run(String... args) throws Exception{
        ff4j = initFF4j();


//        ff4j.createFeature("TestLevel1", true, "Customer_100_ConstructorTest");
//        ff4j.createFeature("TestLevel2", true, "Customer_200_SetIdTest");
//        ff4j.createFeature("TestLevel3", true, "Customer_300_SetNameTest");
//        ff4j.createFeature("TestLevel4", true, "Customer_400_ContactsTest");
//        ff4j.createFeature("TestLevel5", true, "Customer_500_SetNameExtendedTest");
//        Map<String, Feature> features = ff4j.getFeatures();

        //Automatische vergabe der Feature Namen durch die im Verzeichnis vorhandenen Tests
        File f = new File("./src/test/java/master/sedemo/tasks");
        // array mit namen der test files
        String[] filesall = getAllFiles(f);
        Arrays.sort(filesall);
        System.out.println(filesall);
        for(String ob : filesall){
            if(ob.endsWith("Test")){
//                ff4j.createFeature(ob, true, ob);
//                Map<String, Feature> features = ff4j.getFeatures();
                Boolean activ = ff4j.check(ob);
                this.testRepository.save(new Test(ob, ob, ob, activ));
            }
        }
    }
}

