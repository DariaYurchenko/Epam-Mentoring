import React from 'react';
import './index.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import i18next from "i18next";

class Header extends React.Component {
    constructor(props) {
        super();
        this.props = props;
    }

    componentWillMount() {
        i18next.init({
            lng: this.props.language,
            resources: require(`./i18n/json/${this.props.language}.json`)
        });
    }

    render() {
        return (
            <div className="header text-center">
                <h1>{i18next.t('welcome_to_sportsbet')}</h1>
                <div><small>{i18next.t('header_small_text')}</small></div>
            </div>
        );
    }
}

export default Header;