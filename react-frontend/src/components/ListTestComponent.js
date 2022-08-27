import React from 'react';
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

    executeTheTestLevel1(){
        console.log("about to execute test");
        TestService.executeTestLevel1().then((response) => {
            console.log(response.data);
            
        })
    }
    executeTheTestLevel2(){
            console.log("about to execute test2");
            TestService.executeTestLevel2().then((response) => {
                console.log(response.data);

            })
        }

    render(){

        const renderAuthButton = (id) => {
          if (id == 2) {
            return <td><button className="btn btn-primary" onClick={()=>this.executeTheTestLevel1()}>Execute Test</button></td>;

          } else {
            return <td><button className="btn btn-primary" onClick={()=>this.executeTheTestLevel2()}>Execute Test</button></td>;
          }
        }
        return(
            <div>
                <h1 className='text-center'> Test List</h1>mvn
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
                                test => {
                                    return(
                                        <tr key = {test.id}>
                                           <td> {test.id} </td>
                                           <td> {test.name} </td>
                                           <td> {test.description} </td>
                                           {renderAuthButton(test.id)}
                                           {/* <td><button className="btn btn-primary" onClick={this.executeTest()}>Execute Test</button></td> */}
                                       </tr>
                                    )

                                }

                            )
                        }
                    </tbody>
                </table>
            </div>
        )
    }
}

export default ListTestComponent;