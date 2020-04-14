import React from 'react';
import axios from "axios";
import {getFullHeader} from "./consts/getFullHeader";
import {setErrorState} from "./consts/setErrorState";
import Alert from "react-bootstrap/Alert";
import Table from "react-bootstrap/Table";
import {initialLanguageSet, setLanguage} from "./i18n/i18nFunctions";
import i18next from "i18next";
import Header from "./Header";
import Menu from "./Menu";
import menu from "./consts/menu";
import Language from "./Language";

class StageEventButton extends React.Component {
    constructor() {
        super();
        this.state = {
            error: null,
            sportEvents: []
        };
    }

    componentWillMount() {
        let language = initialLanguageSet();
        setLanguage(language, this);

        let self = this;
        axios.get('http://localhost:8080/sport-events/stage', getFullHeader()).then((res) => {
            self.setState({
                sportEvents: res.data.results
            });
        })
            .catch((error) => {
                if(error.response.status === 401) {
                    self.setState({unauthorized: true})
                }
                else {
                    setErrorState(error, self);
                }
            });
    }

    render() {
        return (<div>
            <Header language={this.state.language}/>
            <Menu items={menu} language={this.state.language} history={this.props.history}/>
            <Language parentContext={this}/>
            <div className="contentContainer">
                {this.state.sportEvents.map((sportEvent, index) => {
                return (
                    <div className="tableContainer">
                        <Alert className="tableHeading">{index + 1}. {sportEvent.sportEventTitle}</Alert>
                        <div className="subcontainer">
                            <Table responsive hover>
                                <thead>
                                    <tr>
                                        <th>{i18next.t('bet_type')}</th>
                                        <th>{i18next.t('value')}</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {sportEvent.winningOutcomes.map((outcome) => {
                                        return (<tr>
                                            <td>{outcome.betType}</td>
                                            <td>{outcome.value}</td>
                                        </tr>);
                                    })}
                                </tbody>
                            </Table>
                        </div>
                    </div>
                );
            })}
            </div>
        </div>);
    }
}

export default StageEventButton;
