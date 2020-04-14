import i18next from "i18next";

function changeLanguage(language, self) {
    localStorage.setItem('language', language);
    self.setState({
        language
    });
}

function initialLanguageSet() {
    let languageInLS = localStorage.getItem('language');
    return languageInLS ? languageInLS : 'en';
}

function setLanguage(language, self) {
    changeLanguage(language, self);
    i18next.init({
        lng: language,
        resources: require(`./json/${language}.json`)
    });
}

export {setLanguage, initialLanguageSet};
