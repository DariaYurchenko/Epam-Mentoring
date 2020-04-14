import React from 'react';
import menu from './consts/menu';
import Menu from "./Menu";
import Language from "./Language";
import Header from "./Header";
import {initialLanguageSet, setLanguage} from "./i18n/i18nFunctions";
import i18next from "i18next";

class NotFound extends React.Component{

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
                <h1 className="text-primary text-center my-4">{i18next.t('page_not_found')}</h1>
                </div>
            </div>
        )
    }

}
export default NotFound;