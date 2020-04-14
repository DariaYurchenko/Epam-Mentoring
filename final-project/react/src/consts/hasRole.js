export const hasRole = (user, role) => {
    if(user) {
        return user.role === role;
    }
};