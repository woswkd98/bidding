
const initailState = {
    is_login : false,
    user_id : '',
    userName : '',
    is_seller : null,
    userProfileImage : "",
}

const login_reducer = (state = initailState, action) => {
    switch(action.type){
        case 'LOGIN':
            return {
                is_login : true,
                user_id : action.payload.user_id,
                userName : action.payload.userName,
                is_seller : action.payload.is_seller,
            }
        case 'LOGOUT':
            return{
                is_login : false,
                user_id : '',
                userName : '',
                is_seller : null,
            }
        default:
            return state;
    }
}


export default login_reducer;