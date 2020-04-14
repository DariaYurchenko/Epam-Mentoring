import React from 'react';
import menu from './consts/menu';
import Menu from "./Menu";
import axios from "axios";
import {setErrorState} from "./consts/setErrorState";
import Language from "./Language";
import {renderError} from "./consts/renderError";
import {initialLanguageSet, setLanguage} from "./i18n/i18nFunctions";
import {getFullHeader} from "./consts/getFullHeader";
import {getLanguageHeader} from "./consts/getLanguageHeader";
import Header from "./Header";
import Alert from "react-bootstrap/Alert";
import i18next from "i18next";

class EmailConfirmation extends React.Component {

    constructor(props) {
        super();
        this.props = props;
        this.state={};
    }

    componentWillMount() {
        let language = initialLanguageSet();
        setLanguage(language, this);

        let self = this;

        let searchParams = new URLSearchParams(this.props.location.search);
        let confirmData = {
            token: searchParams.get('token'),
            email: searchParams.get('email')
        };

        axios.post('http://localhost:8080/users/confirm', confirmData, getLanguageHeader()).then(res => {
            this.setState({
                success: true
            });
        }).catch(function (error) {
            setErrorState(error, self);
        });
    }

    render() {
        return (
            <div>
                <Header language={this.state.language}/>
                <Menu items={menu} language={this.state.language} history={this.props.history}/>
                <Language parentContext={this}/>
                <div className="contentContainer">
                {this.state.success ? (<Alert variant="success" className="col-lg-6 col-md-8 col-sm-11 text-center mx-auto"><h2>{i18next.t('registration_email_was_confirmed')}</h2></Alert>) : null}
                {renderError(this.state.error, i18next)}
                </div>
            </div>
        );
    }
}

export default EmailConfirmation;