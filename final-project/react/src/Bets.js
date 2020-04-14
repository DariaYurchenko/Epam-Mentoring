import React from 'react';
import axios from "axios";
import menu from "./consts/menu";
import Menu from "./Menu";
import {getJwt} from "./consts/getJwt"
import {initialLanguageSet, setLanguage} from "./i18n/i18nFunctions";
import {getFullHeader} from "./consts/getFullHeader";
import {setErrorState} from "./consts/setErrorState";
import Language from "./Language";
import {renderError} from "./consts/renderError";
import {hasRole} from "./consts/hasRole";
import {isAuth} from "./consts/isAuth";
import Header from "./Header";
import "./index.css";
import Table from "react-bootstrap/Table";
import Alert from "react-bootstrap/Alert";
import InputGroup from "react-bootstrap/InputGroup";
import FormControl from "react-bootstrap/FormControl";
import Button from "react-bootstrap/Button";
import i18next from "i18next";

class Bets extends React.Component {

    constructor() {
        super();
        this.state = {
            bets: {},
            outcomeOddData: {},
            disabled: false
        };
        this.addWager = this.addWager.bind(this);
        this.addOutcomeOdd = this.addOutcomeOdd.bind(this);
        this.addOutcomeOddField = this.addOutcomeOddField.bind(this);
    }

    componentWillMount() {
        let language = initialLanguageSet();
        setLanguage(language, this);

        let self = this;

        self.setState({
            error: null
        });

        const sportEventId = this.props.match.params.id;
        axios.get(`http://localhost:8080/sport-events/${sportEventId}/bets`, getFullHeader()).then(res => {
            let outcomeOddData = [];
            res.data.betData.forEach((item, index) => {
                let odds = [];
                item.outcomeData.forEach((item, index) => {
                    odds.push({
                        outcomeId: item.id,
                        odd: null,
                        from: null,
                        to: null
                    });
                });
                outcomeOddData.push(odds);
            });
            this.setState({
                bets: res.data,
                outcomeOddData
            });

        }).catch(function (error) {
            setErrorState(error, self)
        });

    }

    renderOutcomeOdds(bet, hidden) {
        return !hidden ? (<ul>
            {bet.outcomeData.map((odd) => {
                return <li>{odd.value}</li>;
            })}
        </ul>) : '';
    }

    addWager(outcomeId) {
        this.state.success = false;

        let currency = localStorage.getItem('currency');
        let amount = prompt(i18next.t('enter_money_amount') + currency);
        let wager = {
            outcomeId: outcomeId,
            amount: amount
        };
        let self = this;

        self.setState({
            error: null
        });

        if (amount !== null) {
            axios.post(`http://localhost:8080/wagers`, wager, getFullHeader()).then(res => {
                this.setState({
                    successWager: true
                });

            }).catch(function (error) {
                setErrorState(error, self)
            });
        }
    }

    addOutcomeOdd(event, index1, index2) {
        event.preventDefault();
        this.setState({disabled:true});
        function setDisable() {
            self.setState({
                disabled: false
            });
        }
        setTimeout(setDisable, 10000);

        let self = this;

        self.setState({
            error: null
        });

        axios.post('http://localhost:8080/sport-events/odds', this.state.outcomeOddData[index1][index2], getFullHeader()).then((res) => {
            self.setState({
                successOdd: true
            });
        })
            .catch(function (error) {
                    setErrorState(error, self)
                }
            );
    }

    addOutcomeOddField(index1, index2, role, value) {
        let outcomeOddData = this.state.outcomeOddData;
        outcomeOddData[index1][index2][role] = value;
        this.setState({
            outcomeOddData
        });
    }

    render() {
        return (
            <div>
                <Header language={this.state.language}/>
                <Menu items={menu} language={this.state.language} history={this.props.history}/>
                <Language parentContext={this}/>
                <div className="contentContainer">
                {Object.keys(this.state.bets).length ? (
                <div>
                    <div className="tableContainer">
                        <Alert className="tableHeading">{i18next.t('sport_event_data')}</Alert>
                        <div className="subcontainer">
                            <Table responsive hover>
                                <thead>
                                <tr>
                                    <th>{i18next.t('event_title')}</th>
                                    <th>{i18next.t('event_type')}</th>
                                    <th>{i18next.t('event_start_date')}</th>
                                    <th>{i18next.t('event_end_date')}</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>{this.state.bets.eventTitle}</td>
                                        <td>{this.state.bets.eventType}</td>
                                        <td>{this.state.bets.startDate}</td>
                                        <td>{this.state.bets.endDate}</td>
                                    </tr>
                                </tbody>
                            </Table>
                        </div>
                    </div>
                    <div className="tableContainer">
                        <Alert className="tableHeading">{i18next.t('sport_events')}</Alert>
                        <div className="subcontainer">
                    <Table responsive hover>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>{i18next.t('bet_type')}</th>
                            <th>{i18next.t('bet_description')}</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.state.bets.betData.map((item, index1) => {
                            item.optionsHidden = true;
                            return (<tr>
                                <td>{index1 + 1}</td>
                                <td>{item.betType}</td>
                                <td onClick={() => {
                                    item.optionsHidden = !item.optionsHidden;
                                }}>
                                    {`${item.description}`}
                                    <details>
                                        <summary>{i18next.t('outcomes')}</summary>
                                        <ul className="outcomesList">
                                            {item.outcomeData.map((item, index2) => {
                                                return (
                                                    <li className="my-2">{hasRole(isAuth(), 'ROLE_PLAYER') ? (<Button variant="primary" className="mx-3" onClick={() => this.addWager(item.id)}>{i18next.t('add_wager')}</Button>) : null} {item.value}
                                                        {hasRole(isAuth(), 'ROLE_ADMIN') ? (
                                                            <details>
                                                                <summary>{i18next.t('add_an_outcome_odd')}</summary>
                                                                <div className="formContainer">
                                                                    <Alert className="formHeading">{i18next.t('add_an_outcome_odd')}</Alert>
                                                                <form onSubmit={(event) => this.addOutcomeOdd(event, index1, index2)}>
                                                                    <InputGroup className="mb-3 d-flex">
                                                                        <InputGroup.Prepend>
                                                                            <InputGroup.Text id="basic-addon1">{i18next.t('outcome_odd')}:</InputGroup.Text>
                                                                        </InputGroup.Prepend>
                                                                        <FormControl
                                                                            type="number"
                                                                            step="0.1"
                                                                            min="1"
                                                                            placeholder="Odd"
                                                                            aria-label="Odd"
                                                                            onInput={(event) => this.addOutcomeOddField(index1, index2, "odd", event.target.value)}
                                                                        />
                                                                    </InputGroup>
                                                                    <InputGroup className="mb-3 d-flex">
                                                                        <InputGroup.Prepend>
                                                                            <InputGroup.Text id="basic-addon1">{i18next.t('outcome_odd_from_date')}:</InputGroup.Text>
                                                                        </InputGroup.Prepend>
                                                                        <FormControl
                                                                            type="date"
                                                                            placeholder="From date"
                                                                            aria-label="From date"
                                                                            aria-describedby="basic-addon1"
                                                                            onInput={(event) => this.addOutcomeOddField(index1, index2, "from", event.target.value)}
                                                                        />
                                                                    </InputGroup>
                                                                    <InputGroup className="mb-3 d-flex">
                                                                        <InputGroup.Prepend>
                                                                            <InputGroup.Text id="basic-addon1">{i18next.t('outcome_odd_to_date')}:</InputGroup.Text>
                                                                        </InputGroup.Prepend>
                                                                        <FormControl
                                                                            type="date"
                                                                            placeholder="To date"
                                                                            aria-label="To date"
                                                                            aria-describedby="basic-addon1"
                                                                            onInput={(event) => this.addOutcomeOddField(index1, index2, "to", event.target.value)}
                                                                        />
                                                                    </InputGroup>
                                                                    <Button
                                                                        type="submit"
                                                                        disabled={this.state.disabled}
                                                                    >{i18next.t('add_an_outcome_odd')}</Button>
                                                                </form>
                                                                </div>
                                                            </details>
                                                        ) : null}
                                                    </li>
                                                );
                                            })}
                                        </ul>
                                    </details>
                                </td>
                            </tr>);
                        })}
                        </tbody>
                    </Table>
                </div>
                    </div>
                    </div>
            ) : (<h2>{i18next.t('there_are_no_bets_yet')}</h2>)}
            </div>
            {renderError(this.state.error, i18next)}
                {this.state.successOdd ? (<Alert variant="success" className="text-center mx-auto col-lg-4 col-md-6 col-sm-9"><h2>{i18next.t('new_odd_was_added')}</h2></Alert>) : null}

                {this.state.successWager ? (<Alert variant="success" className="text-center mx-auto col-lg-4 col-md-6 col-sm-9"><h2>{i18next.t('new_wager_was_added')}</h2></Alert>) : null}
            </div>);
    }
}

export default Bets;