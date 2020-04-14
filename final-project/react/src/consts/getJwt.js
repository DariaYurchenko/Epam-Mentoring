export const getJwt = () => {
    let user = localStorage.getItem('user');
    if(user) {
        return JSON.parse(user).jwt;
    }
};