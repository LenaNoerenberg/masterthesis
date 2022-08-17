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

import master.sedemo.repository.ClientRepository;
import master.sedemo.model.Client;
import master.sedemo.model.Test;
import master.sedemo.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TestRepository testRepository;
    @Override
    public void run(String... args) throws Exception{
        this.clientRepository.save(new Client("lena","lena@gmail.com" ));
        this.clientRepository.save(new Client("julius","julius@gmail.com" ));

        this.testRepository.save(new Test("lena", "Das ist Test 1"));
        this.testRepository.save(new Test("julius", "das ist test 2"));
        this.testRepository.save(new Test("lena", "Das ist Test 1"));
        this.testRepository.save(new Test("julius", "das ist test 2"));
        this.testRepository.save(new Test("lena", "Das ist Test 1"));
        this.testRepository.save(new Test("julius", "das ist test 2"));
    }
}

