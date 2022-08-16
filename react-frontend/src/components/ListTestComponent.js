import React, { useReducer } from 'react';
import TestService from '../services/TestService';

class ListTestComponent extends React.Component {

    constructor(props){
        super(props)
        this.state = {
            tests: []
        }
    }

    componentDidMount(){
        TestService.getTests().then((response) => {
            console.log(response.data)
            this.setState({tests: response.data});
        });
    }

    executeTheTest(){
        console.log("about to execute test");
        TestService.executeTest().then((response) => {
            console.log(response.data);
            
        })
    }

    render(){
        return(
            <div>
                <h1 className='text-center'> Test List</h1>
                <table className='table table-stripped'>
                    <thead>
                        <tr>
                            <td>Test Id</td>
                            <td>Test Name</td>
                            <td>Test Description</td>
                            <td>Action</td>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.tests.map(
                                test =>
                                <tr key = {test.id}>
                                    <td> {test.id} </td>
                                    <td> {test.name} </td>
                                    <td> {test.description} </td>
                                    <td><button className="btn btn-primary" onClick={()=>this.executeTheTest()}>Execute Test</button></td>
                                    {/* <td><button className="btn btn-primary" onClick={this.executeTest()}>Execute Test</button></td> */}
                                </tr>
                            )
                        }
                    </tbody>
                </table>
            </div>
        )
    }
}

export default ListTestComponent;