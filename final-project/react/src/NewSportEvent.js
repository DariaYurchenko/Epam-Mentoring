import React from 'react';
import menu from './consts/menu';
import Menu from "./Menu";
import axios from "axios";
import {initialLanguageSet, setLanguage} from "./i18n/i18nFunctions";
import {setErrorState} from "./consts/setErrorState";
import {getFullHeader} from "./consts/getFullHeader";
import Language from "./Language";
import Header from "./Header";
import InputGroup from "react-bootstrap/InputGroup";
import FormControl from "react-bootstrap/FormControl";
import Alert from "react-bootstrap/Alert";
import Button from "react-bootstrap/Button";
import i18next from "i18next";

const sportEventTypes = [
    {
        id:0,
        label: 'Tennis'
    },
    {
        id:1,
        label: 'Football'
    }
];

let betsInputForms = [];

class NewSportEvent extends React.Component{

    constructor() {
        super();
        this.state = {
            title: null,
            startDate: null,
            endDate: null,
            sportEventType: 0,
            bets: [
                {
                    description: null,
                    type: 0,
                    outcomes: [
                        {
                            value: null,
                            outcomeOdds: [
                                {
                                    odd: null,
                                    from: null,
                                    to: null
                                }
                            ]
                        }
                    ]
                }
            ]
        };
        this.chooseSportEventType = this.chooseSportEventType.bind(this);
        this.createRadioButtons = this.createRadioButtons.bind(this);
        this.addBetForm = this.addBetForm.bind(this);
        this.addOutcomeForm = this.addOutcomeForm.bind(this);
        this.addOutcomeOddForm = this.addOutcomeOddForm.bind(this);
        this.betInputOnChange = this.betInputOnChange.bind(this);
        this.outcomeInputOnChange = this.outcomeInputOnChange.bind(this);
        this.outcomeOddInputOnChange = this.outcomeOddInputOnChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.renderBets = this.renderBets.bind(this);
    }

    componentWillMount() {
        let language = initialLanguageSet();
        setLanguage(language, this);
    }

    chooseSportEventType = (event) => {
        this.setState({
            sportEventType: +event.target.value
        });
    };

    createRadioButtons = () => {
        let self = this;
        return (<div>
            {sportEventTypes.map((sportEventType) => {
                return (
                    <label className="mx-4"><input
                        type="radio"
                        value={sportEventType.id}
                        name="sport-event-type"
                        onChange={self.chooseSportEventType}
                        checked={+sportEventType.id === this.state.sportEventType}
                    /> {sportEventType.label}</label>
                );
            })
        }
        </div>);
        /* return sportEventTypes.map((sportEventType) => {
            return (
                <label>{sportEventType.label}
                    <input type='radio'
                           value={sportEventType.id}
                           name="sport-event-type"
                           checked={+sportEventType.id === this.state.sportEventType}
                           onChange={self.chooseSportEventType}
                    />
                </label>
            );
        }); */
    };

    addBetForm() {
        let bets = this.state.bets;
        bets.push({
            description : "",
            type : 0,
            outcomes : [
                {
                    value: "",
                    outcomeOdds: [
                        {
                            odd : "",
                            from : "",
                            to: ""
                        }
                    ]
                }
            ]
        });
        this.setState({
            bets
        });
    }

    addOutcomeForm(index1) {
        let bets = this.state.bets;
        bets[index1].outcomes.push({
            value: "",
            outcomeOdds: [
                {
                    odd: "",
                    from: "",
                    to: ""
                }]
        });
        this.setState({
            bets
        });
    }

    addOutcomeOddForm(index1, index2) {
        let bets = this.state.bets;
        bets[index1].outcomes[index2].outcomeOdds.push({
            odd: "",
            from: "",
            to: ""
        });
        this.setState({
            bets
        });
    }

    betInputOnChange(index1, role, value) {
        let bets = this.state.bets;
        bets[index1][role] = value !== "" ? value : null;
        this.setState({
            bets
        });
        console.log("bets", bets);
    }

    outcomeInputOnChange(index1, index2, role, value) {
        let bets = this.state.bets;
        bets[index1].outcomes[index2][role] = value !== "" ? value : null;
        this.setState({
            bets
        });
    }

    outcomeOddInputOnChange(index1, index2, index3, role, value) {
        let bets = this.state.bets;
        bets[index1].outcomes[index2].outcomeOdds[index3][role] = value !== "" ? value : null;
        this.setState({
            bets
        });
    }

    handleSubmit(event) {
        this.setState({
            error : null
        });
        let self = this;
        event.preventDefault();
        axios.post('http://localhost:8080/sport-events', this.state, getFullHeader())
            .then((resp) => {
                self.setState({
                    success:true
                });
                }
            )
            .catch(function (error) {



                setErrorState(error, self);
                }
            )
    }

    renderBets() {
        return this.state.betsInputForms.length ? this.state.betsInputForms.map((bet, index) => {
            return (
                <label>Put in a bet description: <input type="text" value={bet.value}/></label>
            );
        }) : null;
    }

    render() {
        return (
            <div>
                <Header language={this.state.language}/>
                <Menu items={menu} language={this.state.language} history={this.props.history}/>
                <Language parentContext={this}/>
                <div className="contentContainer">
                <form onSubmit={this.handleSubmit}>
                    <div className="formContainer">
                        <Alert className="formHeading">{i18next.t('put_in_a_sport_event_data')}</Alert>
                        <div className="subcontainer">
                        <InputGroup className="mb-3">
                            <InputGroup.Prepend>
                                <InputGroup.Text>{i18next.t('event_title')}: </InputGroup.Text>
                            </InputGroup.Prepend>
                            <FormControl
                                type="text"
                                aria-label="Title"
                                onChange={(event) => this.setState({title:event.target.value})}
                            />
                        </InputGroup>
                        <InputGroup className="mb-3">
                            <InputGroup.Prepend>
                                <InputGroup.Text>{i18next.t('event_start_date')}: </InputGroup.Text>
                            </InputGroup.Prepend>
                            <FormControl
                                type="date"
                                aria-label="Start Date"
                                onChange={(event) => this.setState({startDate:event.target.value})}
                            />
                        </InputGroup>
                        <InputGroup className="mb-3">
                            <InputGroup.Prepend>
                                <InputGroup.Text>{i18next.t('event_end_date')}: </InputGroup.Text>
                            </InputGroup.Prepend>
                            <FormControl
                                type="date"
                                aria-label="End Date"
                                onChange={(event) => this.setState({endDate:event.target.value})}
                            />
                        </InputGroup>
                        <InputGroup className="d-flex align-items-center mb-3">
                            <InputGroup.Prepend>
                                <InputGroup.Text>{i18next.t('event_type')}: </InputGroup.Text>
                            </InputGroup.Prepend>
                            <div>
                                {this.createRadioButtons()}
                            </div>
                        </InputGroup>
                    </div>
                    </div>

                    <details className="p-2 my-2">
                        <summary>{i18next.t('bet_forms')}</summary>
                    {this.state.bets.length ? this.state.bets.map((bet, index1) => {
                        return (
                            <div>
                            <div className="formContainer">
                                <Alert className="formHeading">{i18next.t('put_in_a_bet_data')}</Alert>
                                <div className="subcontainer">
                                <InputGroup className="mb-3">
                                    <InputGroup.Prepend>
                                        <InputGroup.Text>{i18next.t('bet_description')}:</InputGroup.Text>
                                    </InputGroup.Prepend>
                                    <FormControl
                                        as="textarea"
                                        aria-label="With textarea"
                                        onInput={(event) => this.betInputOnChange(index1, "description", event.target.value)}
                                    />
                                </InputGroup>
                                <InputGroup className="d-flex align-items-center mb-3">
                                    <InputGroup.Prepend>
                                        <InputGroup.Text>{i18next.t('bet_type')}:</InputGroup.Text>
                                    </InputGroup.Prepend>
                                    <div>
                                    <span className="mx-4"><input type="radio" checked={+this.state.bets[index1].type === 0} name={`bet-type-${index1}`} value="0" onChange={(event) => this.betInputOnChange(index1, "type", event.target.value)}/> Goals</span>
                                    <span className="mx-4"><input type="radio" checked={+this.state.bets[index1].type === 1} name={`bet-type-${index1}`} value="1" onChange={(event) => this.betInputOnChange(index1, "type", event.target.value)}/> Winner</span>
                                    <span className="mx-4"><input type="radio" checked={+this.state.bets[index1].type === 2} name={`bet-type-${index1}`} value="2" onChange={(event) => this.betInputOnChange(index1, "type", event.target.value)}/> Player score</span>
                                    </div>
                                </InputGroup>
                            </div>
                            </div>
                                    <details className="p-4  my-2">
                                        <summary>{i18next.t('outcome_forms')}</summary>
                                {bet.outcomes.map((outcome, index2) => {
                                    return (
                                        <div>
                                        <div className="formContainer">
                                            <Alert className="formHeading">{i18next.t('put_in_an_outcome_data')}</Alert>
                                            <div className="subcontainer">
                                            <InputGroup className="mb-3">
                                                <InputGroup.Prepend>
                                                    <InputGroup.Text id="basic-addon1">{i18next.t('outcome_value')}: </InputGroup.Text>
                                                </InputGroup.Prepend>
                                                <FormControl
                                                    placeholder="Username"
                                                    aria-label="Username"
                                                    onInput={(event) => this.outcomeInputOnChange(index1, index2, "value", event.target.value)}
                                                />
                                            </InputGroup>
                                        </div>
                                        </div>
                                            <details className="p-4 my-2">
                                                <summary>{i18next.t('outcome_odd_forms')}</summary>
                                            {outcome.outcomeOdds.map((outcomeOdd, index3) => {
                                                return (
                                                    <div className="formContainer">
                                                        <Alert className="formHeading">{i18next.t('put_in_an_outcome_odd_data')}</Alert>
                                                        <div className="subcontainer">
                                                        <InputGroup className="mb-3">
                                                            <InputGroup.Prepend>
                                                                <InputGroup.Text id="basic-addon1">{i18next.t('outcome_odd')}: </InputGroup.Text>
                                                            </InputGroup.Prepend>
                                                            <FormControl
                                                                type="number"
                                                                step="0.1"
                                                                min="1"
                                                                placeholder="odd"
                                                                aria-label="Username"
                                                                onInput={(event) => this.outcomeOddInputOnChange(index1, index2, index3, "odd", event.target.value)}
                                                            />
                                                        </InputGroup>
                                                        <InputGroup className="mb-3">
                                                            <InputGroup.Prepend>
                                                                <InputGroup.Text id="basic-addon1">{i18next.t('outcome_odd_from_date')}: </InputGroup.Text>
                                                            </InputGroup.Prepend>
                                                            <FormControl
                                                                type="date"
                                                                placeholder="2020-04-04"
                                                                aria-label="Username"
                                                                onInput={(event) => this.outcomeOddInputOnChange(index1, index2, index3, "from", event.target.value)}
                                                            />
                                                        </InputGroup>
                                                        <InputGroup className="mb-3">
                                                            <InputGroup.Prepend>
                                                                <InputGroup.Text id="basic-addon1">{i18next.t('outcome_odd_to_date')}: </InputGroup.Text>
                                                            </InputGroup.Prepend>
                                                            <FormControl
                                                                type="date"
                                                                placeholder="2020-04-05"
                                                                aria-label="Username"
                                                                onInput={(event) => this.outcomeOddInputOnChange(index1, index2, index3, "to", event.target.value)}
                                                            />
                                                        </InputGroup>
                                                    </div>
                                                    </div>
                                                );
                                            })}
                                            <Button type="button" onClick={() => this.addOutcomeOddForm(index1, index2)}>{i18next.t('add_an_outcome_odd')}</Button>
                                            </details>
                                        </div>
                                    );
                                })}
                                <Button type="button" onClick={() => this.addOutcomeForm(index1)}>{i18next.t('add_an_outcome')}</Button>
                                    </details>
                        </div>
                        );
                    }) : null}
                    <Button type="button" onClick={this.addBetForm}>{i18next.t('add_a_bet')}</Button>
                </details>
                    <Button type="submit">{i18next.t('add_a_new_sport_event')}</Button>
                </form>
                {this.state.error ? this.state.error.violations ? (<Alert variant="danger" className="text-center mx-auto col-lg-4 col-md-6 col-sm-9">
                    <h2>{i18next.t('errors_occured')}</h2>
                    {this.state.error.violations.map((violation) => {
                        return (<p>{violation.message}</p>)
                    })}
                </Alert>): (<Alert variant="danger" className="text-center mx-auto col-lg-4 col-md-6 col-sm-9">
                    <h2>{i18next.t('error_occured')}</h2>
                    <p>{this.state.error.message}</p>
                </Alert>) : null}
                {this.state.success ? <Alert variant="success" className="text-center mx-auto col-lg-4 col-md-6 col-sm-9"><h2>Event was added</h2></Alert> : null}
            </div>
            </div>
        )
    }

}
export default NewSportEvent;