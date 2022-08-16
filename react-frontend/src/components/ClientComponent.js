import React, { useReducer } from 'react';
import ClientService from '../services/ClientService';

class ClientComponent extends React.Component {

    constructor(props){
        super(props)
        this.state = {
            clients: []
        }
    }

    componentDidMount(){
        ClientService.getClients().then((response) => {
            console.log(response);
            this.setState({clients: response.data});
        });
    }

    render(){
        return(
            <div>
                <h1 className='text-center'> Clients List</h1>
                <table className='table table-stripped'>
                    <thead>
                        <tr>
                            <td>Client Id</td>
                            <td>Client Name</td>
                            <td>Client Email</td>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.clients.map(
                                client =>
                                <tr key = {client.id}>
                                    <td> {client.id} </td>
                                    <td> {client.name} </td>
                                    <td> {client.email} </td>
                                </tr>
                            )
                        }
                    </tbody>
                </table>
            </div>
        )
    }
}

export default ClientComponent;