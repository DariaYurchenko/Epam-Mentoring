import React from 'react';
import {setLanguage} from "./i18n/i18nFunctions";
import 'bootstrap/dist/css/bootstrap.min.css';
import Button from "react-bootstrap/Button";

class Language extends React.Component {

    constructor(props) {
        super();
        this.props = props;
    }

    render() {
        return (
            <div className="text-right">
                <Button bsStyle="primary" className="mx-2" onClick={() => setLanguage('en', this.props.parentContext)}>English</Button>
                <Button bsStyle="primary" className="mx-2" onClick={() => setLanguage('ru', this.props.parentContext)}>Русский</Button>
            </div>
        );
    }
}

export default Language;