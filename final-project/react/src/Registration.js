import React from 'react';
import menu from './consts/menu';
import Menu from "./Menu";
import axios from "axios";
import {initialLanguageSet, setLanguage} from "./i18n/i18nFunctions";
import Language from "./Language";
import {setUnauthorizedMessage} from "./consts/unauthorized";
import {renderError} from "./consts/renderError";
import {setErrorState} from "./consts/setErrorState";
import {getFullHeader} from "./consts/getFullHeader";
import {getLanguageHeader} from "./consts/getLanguageHeader";
import Header from "./Header";
import './index.css';
import Alert from "react-bootstrap/Alert";
import FormControl from "react-bootstrap/FormControl";
import InputGroup from "react-bootstrap/InputGroup";
import Button from "react-bootstrap/Button";
import DropdownButton from "react-bootstrap/DropdownButton";
import Dropdown from "react-bootstrap/Dropdown";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import i18next from "i18next";

class Registration extends React.Component {

    constructor() {
        super();
        this.state = {
            name:null,
            email:null,
            password:null,
            dateOfBirth:null,
            balance:null,
            currency:null,
            disabled: false
        };
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentWillMount() {
        let language = initialLanguageSet();
        setLanguage(language, this);
    }

    handleSubmit(event) {
        event.preventDefault();

        this.setState({disabled:true});
        this.setState({error:null});

        let self = this;

        let userData = {
            name: this.state.name,
            email: this.state.email,
            password: this.state.password,
            dateOfBirth: this.state.dateOfBirth,
            balance: this.state.balance,
            currency: this.state.currency
        };

        function setDisable() {
            self.setState({
                disabled: false
            });
        }
        setTimeout(setDisable, 10000);

        axios.post("http://localhost:8080/users/register", userData, getLanguageHeader()).then(response => {
            self.setState({
                success: true
            });
        }).catch(function (error) {
            setErrorState(error, self);
        })
    }

    render() {
        return (
            <div>
                <Header language={this.state.language}/>
                <Menu items={menu} language={this.state.language} history={this.props.history}/>
                <Language parentContext={this}/>
                <div className="contentContainer">
                <div className="formContainer login my-2">
                    <Alert className="formHeading">{i18next.t('register')}</Alert>
                    <form>
                        <InputGroup className="d-flex">
                            <InputGroup.Prepend>
                                <InputGroup.Text id="basic-addon1">{i18next.t('name')}:</InputGroup.Text>
                            </InputGroup.Prepend>
                            <FormControl
                                type='text'
                                placeholder="Daria"
                                class="form-control"
                                onChange={(event) => this.setState({name:event.target.value})}
                            />
                        </InputGroup>
                        <InputGroup className="d-flex">
                            <InputGroup.Prepend>
                                <InputGroup.Text id="basic-addon1">{i18next.t('email')}:</InputGroup.Text>
                            </InputGroup.Prepend>
                            <FormControl
                                type='text'
                                placeholder="yurchenkod95@gmail.com"
                                class="form-control"
                                onChange={(event) => this.setState({email:event.target.value})}
                            />
                        </InputGroup>
                        <InputGroup className="d-flex">
                            <InputGroup.Prepend>
                                <InputGroup.Text id="basic-addon1">{i18next.t('password')}:</InputGroup.Text>
                            </InputGroup.Prepend>
                            <FormControl
                                type='password'
                                class="form-control"
                                onChange={(event) => this.setState({password:event.target.value})}
                            />
                        </InputGroup>
                        <InputGroup className="d-flex">
                            <InputGroup.Prepend>
                                <InputGroup.Text id="basic-addon1">{i18next.t('date_of_birth')}:</InputGroup.Text>
                            </InputGroup.Prepend>
                            <FormControl
                                type='date'
                                placeholder="1995-03-05"
                                class="form-control"
                                onChange={(event) => {this.setState({dateOfBirth:event.target.value})}}
                            />
                        </InputGroup>
                        <InputGroup className="d-flex">
                            <InputGroup.Prepend>
                                <InputGroup.Text id="basic-addon1">{i18next.t('balance')}:</InputGroup.Text>
                            </InputGroup.Prepend>
                            <FormControl
                                type='number'
                                placeholder="1000"
                                class="form-control"
                                min="0"
                                step="100"
                                onChange={(event) => this.setState({balance:event.target.value})}
                            />
                        </InputGroup>
                        <InputGroup className="d-flex currency-box">
                            <InputGroup.Prepend>
                                <InputGroup.Text id="basic-addon1">{i18next.t('currency')}:</InputGroup.Text>
                            </InputGroup.Prepend>
                            <select
                                className="custom-select my-1 mr-sm-2"
                                id="inlineFormCustomSelectPref"
                                onChange={(event) => this.setState({currency:event.target.value})}
                            >
                                <option selected>{i18next.t('currency')}</option>
                                <option value="UAH">UAH</option>
                                <option value="USD">USD</option>
                                <option value="EUR">EUR</option>
                            </select>
                        </InputGroup>
                        <Button
                            type='submit'
                            variation="primary"
                            disabled={this.state.disabled}
                            onClick={(event) => {this.handleSubmit(event)}}
                        >{i18next.t('register')}</Button>
                </form>
                </div>
                {renderError(this.state.error, i18next)}
                {this.state.success ? (<Alert variant="success" className="text-center mx-auto col-lg-4 col-md-6 col-sm-9"><h2>{i18next.t('email_was_sent')} {this.state.email}.</h2></Alert>) : null}
            </div>
            </div>
        );
    }
}

export default Registration;