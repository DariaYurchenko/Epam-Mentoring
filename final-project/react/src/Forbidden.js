import React from 'react';
import menu from './consts/menu';
import Menu from "./Menu";
import Language from "./Language";
import {initialLanguageSet, setLanguage} from "./i18n/i18nFunctions";
import Header from "./Header";
import i18next from "i18next";

class Forbidden extends React.Component{

    componentWillMount() {
        let language = initialLanguageSet();
        setLanguage(language, this);
    }

    render() {
        return (
            <div>
                <Header language={this.state.language}/>
                <Menu items={menu} language={this.state.language} history={this.props.history}/>
                <Language parentContext={this}/>
                <div className="contentContainer">
                <h1 className="text-danger text-center my-4">{i18next.t('forbidden')}</h1>
                </div>
            </div>
        );
    }
}

export default Forbidden;