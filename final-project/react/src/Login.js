import React from 'react';
import axios from 'axios';
import menu from "./consts/menu";
import Menu from "./Menu";
import './index.css';
import {initialLanguageSet, setLanguage} from "./i18n/i18nFunctions";
import {setErrorState} from "./consts/setErrorState";
import {renderError} from "./consts/renderError";
import Language from "./Language";
import {getFullHeader} from "./consts/getFullHeader";
import {getLanguageHeader} from "./consts/getLanguageHeader";
import 'bootstrap/dist/css/bootstrap.min.css';
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";
import InputGroup from "react-bootstrap/InputGroup";
import FormControl from "react-bootstrap/FormControl";
import Header from "./Header";
import Link from "@material-ui/core/Link";
import i18next from "i18next";

export default class Login extends React.Component {

    constructor() {
        super();
        this.state={
            email:null,
            password:null
        };
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentWillMount() {
        let language = initialLanguageSet();
        setLanguage(language, this);
    }

    handleSubmit(event) {
        event.preventDefault();

        this.setState({
            error: null
        });

        let self = this;
        let loginData = {
            email: this.state.email,
            password: this.state.password
        };

        axios.post("http://localhost:8080/users/login", loginData, getLanguageHeader()).then(response => {
                 localStorage.setItem('user', JSON.stringify(response.data));
                 if(response.data.role === 'ROLE_PLAYER') {
                     this.props.history.push("/home");
                 }
                 else {
                     this.props.history.push("/events");
                 }
            }
            ).catch(function (error) {
                setErrorState(error, self);
            }
        )
    }

    render() {
        return (<div>
            <Header language={this.state.language}/>
            <Menu items={menu} language={this.state.language} history={this.props.history}/>
            <Language parentContext={this}/>
            <div className="contentContainer">
            <div className="logOrReg text-center my-4"><Link href={"/login"}>{i18next.t('call_to_login')}</Link> {i18next.t('or')} <Link href={"/register"}>{i18next.t('call_to_register')}</Link> {i18next.t('to_start')}</div>
            <div className="formContainer login">
                <Alert className="formHeading">{i18next.t('login')}</Alert>
                <form>
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
                    <Button
                        type='submit'
                        variation="primary"
                        onClick={(event) => {this.handleSubmit(event);}}
                    >{i18next.t('login')}</Button>
                </form>
            </div>
            </div>
            {renderError(this.state.error)}
        </div>)
    }
}
