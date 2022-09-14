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

package master.sedemo.config;

import java.io.InputStream;

import master.sedemo.controller.HomeController;
import org.ff4j.FF4j;
import org.ff4j.audit.repository.EventRepository;
import org.ff4j.audit.repository.InMemoryEventRepository;
import org.ff4j.conf.FF4jConfiguration;
import org.ff4j.core.FeatureStore;
import org.ff4j.parser.yaml.YamlParser;
import org.ff4j.property.store.InMemoryPropertyStore;
import org.ff4j.property.store.PropertyStore;
import org.ff4j.store.InMemoryFeatureStore;
import org.ff4j.strategy.el.ExpressionFlipStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * Definition of FF4j Bean. This definition should be done not only
 * in the backend with web console and rest API but also in your microservices.
 */
@Configuration
public class FF4jConfig {

    /** Some logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Bean
    public FF4j getFF4j() {
        //return new FF4j("ff4j-features.xml");
        /*
         * 0. Create bean needed for your stores based on the technology you want to use
         *
         * Because I pick in memory store, I want to load init values from a file I can use
         * YAML, XML or properties File. This is optional you can start with empty store
         * or even create feature programmatically.
         */
        InputStream dataSet = FF4j.class.getClassLoader().getResourceAsStream("ff4j-init-dataset.yml");
        // We imported ff4j-config-yaml to have this
        FF4jConfiguration initConfig = new YamlParser().parseConfigurationFile(dataSet);
        LOGGER.info("Default features have been loaded {}", initConfig.getFeatures().keySet());

        // 1. Define the store you want for Feature, Properties, Audit among 20 tech
        FeatureStore  featureStore  = new InMemoryFeatureStore("ff4j-features.xml");
        PropertyStore propertyStore = new InMemoryPropertyStore(initConfig);
        EventRepository logsAudit   = new InMemoryEventRepository();

        // 2. Build FF4j
        FF4j ff4jBean = new FF4j();
        ff4jBean.setPropertiesStore(propertyStore);
        ff4jBean.setFeatureStore(featureStore);
        ff4jBean.setEventRepository(logsAudit);

        // 3. Complete setup
        ff4jBean.setEnableAudit(true);
        ff4jBean.setAutocreate(true);

        return ff4jBean;

        //you can do the same in 1 line
//        return new FF4j(new YamlParser(), "ff4j-init-dataset.yml").audit(true);
    }
}

//import org.ff4j.FF4j;
//import org.ff4j.core.Feature;
//import org.ff4j.property.PropertyInt;
//import org.ff4j.property.PropertyString;
//import org.ff4j.strategy.el.ExpressionFlipStrategy;
//import org.ff4j.utils.Util;
//import org.ff4j.web.ApiConfig;
//import org.ff4j.web.FF4jDispatcherServlet;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//@Primary
//@Configuration
//@ConditionalOnClass({FF4j.class})
//@ComponentScan(value = {"org.ff4j.spring.boot.web.api", "org.ff4j.services", "org.ff4j.aop", "org.ff4j.spring"})
//public class FF4jConfig {
//
//    @Value("${ff4j.webapi.authentication}")
//    private boolean authentication = false;
//
//    @Value("${ff4j.webapi.authorization}")
//    private boolean authorization = false;
//
//    @Bean
//    public FF4j getFF4j() {
//        FF4j ff4j = new FF4j()
//                .createFeature("TestLevel1");
//        Feature exp = new Feature("exp");
//        exp.setFlippingStrategy(new ExpressionFlipStrategy("exp", "f1 & f2 | !f1 | f2"));
//        ff4j.createFeature(exp);
//        return ff4j;
//    }
//
////    @Bean
////    public FF4jDispatcherServlet getFF4JServlet() {
////        FF4jDispatcherServlet ds = new FF4jDispatcherServlet();
////        ds.setFf4j(getFF4j());
////        return ds;
////    }
////
////    @Bean
////    public ApiConfig getApiConfig() {
////        ApiConfig apiConfig = new ApiConfig();
////
////        // Enable Authentication on API KEY
////        apiConfig.setAuthenticate(false);
////        apiConfig.createApiKey("apikey1", true, true, Util.set("ADMIN", "USER"));
////        apiConfig.createApiKey("apikey2", true, true, Util.set("ADMIN", "USER"));
////        apiConfig.createUser("userName","password", true, true, Util.set("ADMIN", "USER"));
////        apiConfig.createUser("user","userPass", true, true, Util.set("ADMIN", "USER"));
////        apiConfig.createUser("a","a", true, true, Util.set("ADMIN", "USER"));
////        apiConfig.createUser("b","b", true, true, Util.set("ADMIN", "USER"));
////
////        // Check Autorization as well
////        apiConfig.setAutorize(false);
////        apiConfig.setWebContext("/api");
////        apiConfig.setPort(8080);
////        apiConfig.setFF4j(getFF4j());
////        return apiConfig;
////    }
//
//}

