import React from 'react';
import logo from './logo.svg';
import './App.css';
import ApiService from './ApiService';
import {Route} from "react-router-dom";
import Home from "./Home";
import Login from "./Login";
import Registration from "./Registration";
import EmailConfirmation from "./EmailConfirmation";
import SportEvents from "./SportEvents";
import Bets from "./Bets";
import PlayerWagers from "./PlayerWagers";
import NotFound from "./NotFound";


class App extends React.Component {
    constructor() {
        super();
        this.state = {
            user: {name: 'User'}
        };
        this.apiService = new ApiService();
    }

    componentWillMount() {
        this.apiService.fetchUser('http://localhost:8080/users/get').then(res => {
            this.setState({
                user: res.data
            });
        });
    }

    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo" />
                    <p>
                        Edit <code>src/App.js</code> and save to reload.
                    </p>
                    <a
                        className="App-link"
                        href="https://reactjs.org"
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        Learn React
                    </a>
                </header>
                <h1>Hello, {this.state.user.name}</h1>
            </div>
        );
    }
}

export default App;
