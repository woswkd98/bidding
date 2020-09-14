import React from 'react'
import { Route, Redirect } from 'react-router-dom'
import Layout from './Layout';

const PrivateRoute = ({ component : Component, ...rest}) => {

    const is_login = () => {
        if (localStorage.getItem('is_login') === 'true') {
          return true
        } else {
          return false
        }
      }
    

    return (
        <Route
        {...rest}
        render={() => {
            if(is_login()){
                return (
                    <Layout>
                        <Component/>
                    </Layout>
                    )
            } else {
                return  <Redirect to='/' />
            }
        }}
        />
    )

}

export default PrivateRoute
