import axios from 'axios';

const CLIENTS_REST_API_URL = 'http://localhost:8080/clients/allClients'

class ClientService {

    getClients(){
        return axios.get(CLIENTS_REST_API_URL)
    }

    
}

export default new ClientService()