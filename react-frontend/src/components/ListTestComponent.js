import React from 'react';
import axios from "axios";
import TestService from '../services/TestService';
import './component.css';
import Spinner from 'react-bootstrap/Spinner';
import Modal from 'react-bootstrap/Modal';
/**
* Test List Component
* Erstellt eine Liste an vorhandenen Tests,
* welche durch Funktionen ausgeführt werden können.
*/
class ListTestComponent extends React.Component {

    //Konstruktor erstellt leeres Tests Array und bool Werte für den Spinner und das Modal
    constructor(props){
        super(props)
        this.state = {
            tests: [],
            loading: false,
            showModal: false
        }
    }

    //als erstes werden alle Tests geladen
    componentDidMount(){
        TestService.getTests().then((response) => {
            console.log(response.data)
            this.setState({tests: response.data});
        });
    }

    // ausführen aller tests
     executeTests(){
            console.log("about to all tests selected");
            return TestService.executeTests().then((response) => {
                console.log(response.data);

            })
        }
    //ausführen einzelner Tests
    executeTest(uid){
        return axios.get('http://localhost:8080/test/executeTest/'+uid)
            .then(res => {
                 console.log("res", res)
            })
            .catch(error => {
                console.log(error);
            })
    }

    //check Feature für jeden Test
    getFeatureCheck(uid){
         let active = null;
         axios.get('http://localhost:8080/test/featureCheck/'+uid)
            .then(res => {
                 active = res.data;
                 this.setState({featureActive: res.data})
            })
            .catch(error => {
                console.log(error);
            })
         return active;
    }

    render(){
        //Variablen für das sichtbar machen des Spinners und des Modals
        const isLoading = this.state.loading;
        const showModal = this.state.showModal;
        // Funktionen zum ein- und ausblenden des Modals
        const handleClose = () => this.setState({showModal: false});
        const handleShow = () => this.setState({showModal: true});

        //Test Button für jede Zeile der Tabelle
        const renderTestButton = (id) => {
          return <td><button className="btn btn-outline-primary" onClick={()=>executeOneButton(id)}>Execute Test</button></td>;
        }
        //Anzeigen ob ein Feature aktiv oder inaktiv ist
        const featureActive = (activeF) => {
            if(activeF){
                return <td> <p className="text-success">Aktiv</p> </td>
            } else {
                return <td> <p className="text-danger">Inaktiv</p> </td>
            }
        }
        //alle tests ausführen und derweile den spinner anzeigen
        const executeButton = () => {
            this.setState({loading: true})
            let test = this.executeTests()
            test.then((result) => {
                this.setState({loading: false})
                handleShow()
            })
        }

        //einzelne tests ausführen und derweile den spinner anzeigen
        const executeOneButton = (id) => {
            this.setState({loading: true})
            let test = this.executeTest(id)
            test.then((result) => {
                this.setState({loading: false})
                handleShow()
            })
        }

        return(
            <>
                <div className="container">
                     <div className="row">
                        <h1 className='text-center'>Masterthesis</h1>

                        <div className="mt-3">
                            <h2> </h2>
                            <a href="http://localhost:8080/ff4j-web-console/features" target="_blank">
                                <input type="button" className="btn btn-outline-dark col-md-6 float-end" value="Open Feature Toggle Management" />
                            </a>
                        </div>
                     </div>

                    <table className='table table-stripped'>
                        <thead>
                            <tr>
                                <td>#</td>
                                <td>Test Name</td>
                                <td>Feature Toggle Aktiv</td>
                                <td>Execute Single Test</td>
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
                                               {featureActive(test.hasFeatureEnabled)}
                                               {renderTestButton(test.name)}
                                           </tr>
                                        )

                                    }

                                )
                            }
                        </tbody>
                    </table>

                    <div className="container">
                        <div className="row">
                            <button className="btn btn-outline-success col-md-6" onClick={()=>executeButton()}>Execute Selected Tests</button>
                            <a href="/report" className = "btn btn-outline-danger col-md-6 float-end" target="_blank">Open Report</a>
                        </div>
                        <div className="row mt-3"></div>
                        <div className="flex-row">
                            { isLoading
                                ? <Spinner animation="border" className="align-self-center" variant="secondary" />
                                : <Spinner animation="border" className="align-self-center visually-hidden" variant="secondary" />
                            }
                        </div>
                        <div className="flex-row">
                            { isLoading
                                ? <span> Executing Tests </span>
                                : <span className="visually-hidden"> Executing Tests </span>
                            }
                        </div>

                    </div>

                </div>

                <Modal show={showModal} onHide={handleClose}>
                    <Modal.Header closeButton>
                      <Modal.Title>All Tests were executed!</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>Report was generated, you can now evaluate the results!</Modal.Body>
                    <Modal.Footer>
                      <button className="btn btn-outline-secondary" onClick={handleClose}>
                        Close
                      </button>
                      <a href="/report" className = "btn btn-outline-primary" target="_blank">View Report</a>
                    </Modal.Footer>
                </Modal>
            </>
        )
    }
}


export default ListTestComponent;