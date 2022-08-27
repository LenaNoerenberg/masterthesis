import axios from "axios";

const TEST_REST_API_URL = 'http://localhost:8080/test/all'
const EXECUTE_TEST_REST_API_URL_LEVEL1 = 'http://localhost:8080/test/executeTestLevel1'
const EXECUTE_TEST_REST_API_URL_LEVEL2 = 'http://localhost:8080/test/executeTestLevel2'


class TestService {

    getTests(){
        return axios.get(TEST_REST_API_URL)
    }

    executeTestLevel1() {
        return axios.post(EXECUTE_TEST_REST_API_URL_LEVEL1);
    }

    executeTestLevel2() {
        return axios.post(EXECUTE_TEST_REST_API_URL_LEVEL2);
    }
    
}

export default new TestService()