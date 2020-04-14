export const getLanguageHeader = () => {
    const lang = localStorage.getItem('language');
    console.log("LAN", lang);
    return {
        headers: {
            "Accept-Language": lang
        }
    };
};