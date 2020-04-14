import React from 'react';
import ReactDOM from 'react-dom';
import 'react-router-dom';
import './index.css';
import Home from './Home';
import Login from "./Login";
import Registration from "./Registration";
import EmailConfirmation from "./EmailConfirmation";
import * as serviceWorker from './serviceWorker';
import {Route, Router, IndexRoute, Redirect} from "react-router";
import {BrowserRouter, Switch} from "react-router-dom";
import SportEvents from "./SportEvents";
import Bets from "./Bets";
import PlayerWagers from "./PlayerWagers";
import NotFound from "./NotFound";
import NewSportEvent from "./NewSportEvent";
import StageEventButton from "./StageEventButton";
import {hasRole} from "./consts/hasRole";
import { createBrowserHistory } from 'history';
import Forbidden from "./Forbidden";
import {isAuth} from "./consts/isAuth";

const history = createBrowserHistory();

ReactDOM.render(
    <BrowserRouter>
        <div>
            <Switch>
                <Route exact path='/' component={Login} />
                <Route exact path='/home' render={(props) => isAuth() ? (hasRole(isAuth(), 'ROLE_PLAYER') ? (<Home {...props}/>) : <Redirect to='/forbidden'/>) :  <Redirect to='/login'/>}/>
                <Route exact path='/login' component={Login} />
                <Route path='/register' component={Registration} />
                <Route path='/confirm-email' component={EmailConfirmation} />
                <Route path='/events' render={(props) => isAuth() ? (<SportEvents {...props} />) : <Redirect to='/login'/>} />
                <Route path='/bets/:id' render={(props) => isAuth() ? (<Bets {...props} />) : <Redirect to='/login'/>} />
                <Route path='/show-wagers' render={(props) => isAuth() ? (hasRole(isAuth(), 'ROLE_PLAYER') ? (<PlayerWagers {...props}/>) : <Redirect to='/forbidden'/>) :  <Redirect to='/login'/>} />
                <Route path='/new-sport-event' render={(props) => isAuth() ? (hasRole(isAuth(), 'ROLE_ADMIN') ? (<NewSportEvent {...props}/>) : <Redirect to='/forbidden'/>) :  <Redirect to='/login'/>}/>
                <Route path='/stage-event' component={StageEventButton} />
                <Route path='/forbidden' component={Forbidden}/>
                <Route component={NotFound} />
            </Switch>
        </div>
    </BrowserRouter>,
    document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
