export const setUnauthorizedMessage = (error, self) => {
    if(error && error.response && error.response.status === 401) {
        self.setState({
            unauthorized: error
        });
    }
};