import axios from "axios";

const TEST_REST_API_URL = 'http://localhost:8080/test/all';
const CHECK_FEATURES_URL = 'http://localhost:8080/test/checkForFeatures';
const SHOW_RESULTS = "http://localhost:8080/test/showResults";
const EXECUTE_TESTS = "http://localhost:8080/test/executeTests";

class TestService {

    getTests(){
        return axios.get(TEST_REST_API_URL)
    }

    checkForFeatures(){
        return axios.get(CHECK_FEATURES_URL);
    }

    getFeatureCheck(uid){

        const id = uid;

        return axios.get('http://localhost:8080/test/featureCheck/uid?=${id}');
    }

    showResults(){
        return axios.get(SHOW_RESULTS);
    }

    executeTests(){
        return axios.post(EXECUTE_TESTS);
    }
    
}

export default new TestService()