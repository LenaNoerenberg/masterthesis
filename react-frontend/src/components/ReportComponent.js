import React, { useReducer } from 'react';
import TestService from '../services/TestService';
import { Markup } from 'interweave';

/**
* Report Component
* Stellt den generierten Report dar
*/
class ReportComponent extends React.Component {
    constructor(props){
        super(props)
        this.state = {
            showExternalHTML: []
        }
    }
    componentDidMount(){
        TestService.showResults().then((response) => {
            console.log("showResults " +response.data);
            this.setState({ showExternalHTML: response.data });
        })
    }
    //einfaches darstellen der html report datei aus dem backend
    createMarkup() {
        return {__html: this.state.showExternalHTML};
    };

    render() {
        return(
            <div dangerouslySetInnerHTML={this.createMarkup()}/>
        )
    }
}


export default ReportComponent;