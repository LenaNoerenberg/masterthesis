import axios from "axios";

const TEST_REST_API_URL = 'http://localhost:8080/test/all'
const EXECUTE_TEST_REST_API_URL = 'http://localhost:8080/test/execute'

class TestService {

    getTests(){
        return axios.get(TEST_REST_API_URL)
    }

    executeTest() {
        return axios.post(EXECUTE_TEST_REST_API_URL);
    }

    
}

export default new TestService()