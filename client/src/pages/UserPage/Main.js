import React from 'react'
import { Switch, Route } from 'react-router-dom';
import Enroll from './Enroll';
import UserHome from './UserHome';
import MyPage from './MyPage';
import RequestDetail from './MyPage/MyRequest/RequestDetail';

const Main = () => {
    return (
        <Switch>
            <Route exact path='/user' component={UserHome} />
            <Route path='/user/enroll' component={Enroll} />
            <Route path='/user/mypage' component={MyPage} />
            <Route path='/user/detail' component={RequestDetail}/>
        </Switch>
    )
}

export default Main;