import React from 'react';
import menu from './consts/menu';
import Menu from "./Menu";
import axios from "axios";
import {initialLanguageSet, setLanguage} from "./i18n/i18nFunctions";
import {renderError} from "./consts/renderError";
import {setErrorState} from "./consts/setErrorState";
import {getFullHeader} from "./consts/getFullHeader";
import Language from "./Language";
import Header from "./Header";
import i18next from "i18next";
import Alert from "react-bootstrap/Alert";
import "./index.css";
import Table from "react-bootstrap/Table";

class PlayerWagers extends React.Component {

    constructor() {
        super();
        this.state = {
            wagers: []
        };
        this.deleteWager = this.deleteWager.bind(this);
    }

    componentWillMount() {
        let language = initialLanguageSet();
        setLanguage(language, this);

        let self = this;

        axios.get('http://localhost:8080/wagers', getFullHeader()).then(res => {
            this.setState({
                wagers: res.data
            });
        }).catch(function (error) {
            setErrorState(error, self);
        });
    }

    deleteWager(id) {
        let self = this;

        axios.delete(`http://localhost:8080/wagers/${id}`, getFullHeader()).then(res => {
            window.location.reload();
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
                {this.state.wagers.length ?
                    (<div className="tableContainer">
                        <Alert className="tableHeading">{i18next.t('wagers')}</Alert>
                        <div className="subcontainer">
                        <Table responsive hover>
                        <tr>
                            <th></th>
                            <th>#</th>
                            <th>{i18next.t('event_title')}</th>
                            <th>{i18next.t('event_type')}</th>
                            <th>{i18next.t('event_start_date')}</th>
                            <th>{i18next.t('outcome_value')}</th>
                            <th>{i18next.t('outcome_odd')}</th>
                            <th>{i18next.t('money_bet_on')}</th>
                            <th>{i18next.t('currency')}</th>
                            <th>{i18next.t('winner')}</th>
                            <th>{i18next.t('processed')}</th>
                        </tr>
                        { this.state.wagers.map((wager, index) => {
                            return (
                                <tr>
                                    <td><button onClick={() => this.deleteWager(wager.wagerId)}>{i18next.t('delete_wager')}</button></td>
                                    <td>{index + 1}</td>
                                    <td>{wager.eventTitle}</td>
                                    <td>{wager.eventType}</td>
                                    <td>{wager.startDate}</td>
                                    <td>{wager.outcomeValue}</td>
                                    <td>{wager.odd}</td>
                                    <td>{wager.amount}</td>
                                    <td>{wager.currency}</td>
                                    <td>{JSON.stringify(wager.win)}</td>
                                    <td>{JSON.stringify(wager.processed)}</td>
                                </tr>);
                        })
                        }
                    </Table>
                    </div>
                    </div>)
                        : (<h2>{i18next.t('you_dont_have_wagers_yet')}</h2>)}
                {renderError(this.state.error, i18next)}
                </div>
            </div>);
    }
}

export default PlayerWagers;