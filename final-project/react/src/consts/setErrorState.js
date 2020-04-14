export const setErrorState =  (error, self) => {
    if(error.response) {
        self.setState({
            error: error.response.data
        });
    }
};