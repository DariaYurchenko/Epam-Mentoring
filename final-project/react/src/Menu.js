import React from 'react';
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import {ListItemText} from "@material-ui/core";
import Link from "@material-ui/core/Link";
import axios from "axios";
import i18next from "i18next";
import {renderError} from "./consts/renderError";
import {getJwt} from "./consts/getJwt";
import {setErrorState} from "./consts/setErrorState";
import {hasRole} from "./consts/hasRole";
import 'bootstrap/dist/css/bootstrap.min.css';

const user = JSON.parse(localStorage.getItem('user'));
function isAuth () {
    return user;
}

class Menu extends React.Component {

    constructor(props) {
        super();
        this.props = props;
        this.state = {
            menu: props.items,
            language: 'en'
        };
        this.renderMenuElement = this.renderMenuElement.bind(this);
        this.handleOnClick = this.handleOnClick.bind(this);
    }

    componentWillMount() {
        i18next.init({
            lng: this.props.language,
            resources: require(`./i18n/json/${this.props.language}.json`)
        });
    }

    renderMenuElement(item) {
        let user = JSON.parse(localStorage.getItem('user'));
        if(item.name === 'logout' && user) {
            return <Link href="" onClick={this.handleOnClick}>{i18next.t('logout')}</Link>;
        }
        if(item.name === 'login' && !user) {
            return (<Link href={item.path}>{item.label}</Link>);
        }
        if(item.name === 'events' && JSON.parse(localStorage.getItem('user'))) {
            return (<Link href={item.path}>{i18next.t('events')}</Link>);
        }
        if(item.name === 'home' && JSON.parse(localStorage.getItem('user'))) {
            if(hasRole(isAuth(), "ROLE_PLAYER")) {
                return (<Link href={item.path}>{i18next.t('home')}</Link>);
            } else {
                return null;
            }
        }
        if(item.name !== 'login' && item.name !== 'language' && item.name !== 'logout' && item.name !== 'home' && item.name !== 'events') {
            return (<Link href={item.path}>{i18next.t(item.name)}</Link>);
        }
    }

    handleOnClick(event) {
        event.preventDefault();
        let self = this;
        let jwt = {
            jwt:getJwt()
        };
        axios.post('http://localhost:8080/users/log-out', jwt).then(res => {
            localStorage.clear();
            self.props.history.push("/login");
        }).catch(function (error) {
            setErrorState(error, self);
        });
    }

    render() {
        return (
            <div>
                <List class="d-flex flex-row">
                    {this.state.menu.map((item) => {
                        let renderMenuElement = this.renderMenuElement(item);
                        return renderMenuElement ? (<ListItem button>
                                    <ListItemText>
                                        {renderMenuElement}
                                    </ListItemText>
                                 </ListItem>) : null;
                            }
                        )
                    }
                    {renderError(this.state.error, i18next)}
                </List>
            </div>
        )
    }
}

export default Menu;
