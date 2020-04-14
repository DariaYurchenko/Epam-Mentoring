import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Alert from "react-bootstrap/Alert";
import i18next from "i18next";

export const renderError = (error) => {
    if(error) {
        if(error.violations) {
            return (<Alert variant="danger" className="text-center mx-auto col-lg-4 col-md-6 col-sm-9">
                <h2>{i18next.t('errors_occured')}</h2>
                {error.violations.map((violation) => {
                    return (<p>{violation.message}</p>)
                })}
            </Alert>);
        } else {
            return (<Alert variant="danger" className="text-center mx-auto col-lg-4 col-md-6 col-sm-9">
                <h2>{i18next.t('error_occured')}</h2>
                <p>{error.message}</p>
            </Alert>);
        }
    }
};