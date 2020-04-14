import React from 'react';
import menu from './consts/menu';
import Menu from "./Menu";
import axios from "axios";
import Link from "@material-ui/core/Link";
import {hasRole} from "./consts/hasRole";
import {initialLanguageSet, setLanguage} from "./i18n/i18nFunctions";
import {getFullHeader} from "./consts/getFullHeader";
import {setErrorState} from "./consts/setErrorState";
import Language from "./Language";
import {renderError} from "./consts/renderError";
import Table from "react-bootstrap/Table";
import Header from "./Header";
import Alert from "react-bootstrap/Alert";
import './index.css';
import {isAuth} from "./consts/isAuth";
import i18next from "i18next";

const user = JSON.parse(localStorage.getItem('user'));

class SportEvents extends React.Component{

    constructor() {
        super();
        this.state = {
            events: []
        };
    }

    componentWillMount() {
        let language = initialLanguageSet();
        setLanguage(language, this);

        let self = this;
        axios.get('http://localhost:8080/sport-events', getFullHeader()).then(res => {
            this.setState({
                events: res.data
            });
        }).catch(function (error) {
            setErrorState(error, self);
        });
    }

    render() {
        let self = this;
        return (
            <div>
                <Header language={this.state.language}/>
                <Menu items={menu} language={this.state.language} history={this.props.history}/>
                <Language parentContext={this}/>
                <div className="contentContainer">
                <div className="tableContainer">
                    <Alert className="tableHeading">{i18next.t('sport_events')}</Alert>
                <div className="subcontainer">
                    {this.state.events.length ? (
                        <Table responsive hover className="eventsTable">
                            <thead>
                            <tr>
                        <th>#</th>
                                <th>{i18next.t('event_title')}</th>
                        <th>{i18next.t('event_type')}</th>
                        <th>{i18next.t('event_start_date')}</th>
                        <th>{i18next.t('event_end_date')}</th>
                    </tr>
                            </thead>
                        <tbody>
                            {this.state.events.map((event, index) => {
                        return (<tr onClick={
                            () => self.props.history.push(`/bets/${event.sportEventId}`)
                        }>
                            <td>{index + 1}</td>
                            <td>{event.eventTitle}</td>
                            <td>{event.eventType}</td>
                            <td>{event.startDate}</td>
                            <td>{event.endDate}</td>
                        </tr>);
                    })}
                        </tbody>
                        </Table>) : <h2>{i18next.t('no_sport_events_message')}</h2>}
                </div>
                </div>
                {renderError(this.state.error, i18next)}
                {hasRole(isAuth(), 'ROLE_ADMIN') ? <Link href='/new-sport-event' className="btn btn-primary text-white">{i18next.t('add_new_sport_event')}</Link> : null}
                {hasRole(isAuth(), 'ROLE_ADMIN') ? <Link href="/stage-event" className="btn btn-primary text-white mx-2">{i18next.t('stage_event')}</Link> : null}
            </div>
            </div>

        );
    }
}

export default SportEvents;