import {getJwt} from "./getJwt";

export const getFullHeader = () => {
    const jwt = getJwt();
    const lang = localStorage.getItem('language');
    return {
        headers: {
            Authorization : "Bearer " + jwt,
            "Accept-Language": lang
        }
    };
};