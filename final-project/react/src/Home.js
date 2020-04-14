import React from 'react';
import menu from './consts/menu';
import Menu from "./Menu";
import axios from "axios";
import Link from "@material-ui/core/Link";
import i18next from "i18next";
import {initialLanguageSet, setLanguage} from "./i18n/i18nFunctions";
import Language from "./Language";
import {setUnauthorizedMessage} from "./consts/unauthorized";
import {setErrorState} from "./consts/setErrorState";
import {getFullHeader} from "./consts/getFullHeader";
import {renderError} from "./consts/renderError";
import Header from "./Header";
import Alert from "react-bootstrap/Alert";
import InputGroup from "react-bootstrap/InputGroup";
import FormControl from "react-bootstrap/FormControl";
import Button from "react-bootstrap/Button";



export default class Home extends React.Component {

    constructor() {
        super();
        this.state = {};
    }

    componentWillMount() {
        let language = initialLanguageSet();
        setLanguage(language, this);

        let self = this;
        axios.get('http://localhost:8080/users/home', getFullHeader()).then(res => {
            this.setState({
                userData: res
            });
            localStorage.setItem('currency', this.state.userData.data.currency);
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
                {this.state.userData ? (
                    <div className="formContainer home my-2">
                        <Alert className="formHeading">{i18next.t('account_details')}</Alert>
                        <form>
                            <InputGroup className="d-flex">
                                <InputGroup.Prepend>
                                    <InputGroup.Text>{i18next.t('name')}: </InputGroup.Text>
                                </InputGroup.Prepend>
                                <FormControl
                                    type='text'
                                    value={this.state.userData.data.name}
                                    readOnly={true}
                                    class="form-control"
                                />
                            </InputGroup>
                            <InputGroup className="d-flex">
                                <InputGroup.Prepend>
                                    <InputGroup.Text>{i18next.t('email')}: </InputGroup.Text>
                                </InputGroup.Prepend>
                                <FormControl
                                    type='text'
                                    value={this.state.userData.data.email}
                                    readOnly={true}
                                    class="form-control"
                                />
                            </InputGroup>
                            <InputGroup className="d-flex">
                                <InputGroup.Prepend>
                                    <InputGroup.Text>{i18next.t('account_number')}: </InputGroup.Text>
                                </InputGroup.Prepend>
                                <FormControl
                                    type='text'
                                    value={this.state.userData.data.accountNumber}
                                    readOnly={true}
                                    class="form-control"
                                />
                            </InputGroup>
                            <InputGroup className="d-flex">
                                <InputGroup.Prepend>
                                    <InputGroup.Text>{i18next.t('date_of_birth')}: </InputGroup.Text>
                                </InputGroup.Prepend>
                                <FormControl
                                    type='text'
                                    value={this.state.userData.data.dateOfBirth}
                                    readOnly={true}
                                    class="form-control"
                                />
                            </InputGroup>
                            <InputGroup className="d-flex">
                                <InputGroup.Prepend>
                                    <InputGroup.Text>{i18next.t('balance')}: </InputGroup.Text>
                                </InputGroup.Prepend>
                                <FormControl
                                    type='number'
                                    value={this.state.userData.data.balance}
                                    readOnly={true}
                                    class="form-control"
                                />
                            </InputGroup>
                            <InputGroup className="d-flex currency-box">
                                <InputGroup.Prepend>
                                    <InputGroup.Text>{i18next.t('currency')}: </InputGroup.Text>
                                </InputGroup.Prepend>
                                <FormControl
                                    type='text'
                                    value={this.state.userData.data.currency}
                                    readOnly={true}
                                    class="form-control"
                                />
                            </InputGroup>
                        </form>
                    </div>) : null
                }
                {renderError(this.state.error, i18next)}
                <Link href="/show-wagers">{i18next.t('show_wagers')}</Link>
            </div>
            </div>
        )
    }
}