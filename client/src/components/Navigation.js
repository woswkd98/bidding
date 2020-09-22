import React from 'react';
import { Link, useHistory } from 'react-router-dom';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { useSelector, useDispatch } from 'react-redux';
import {  Container, Toolbar } from '@material-ui/core';
import Axios from 'axios';

const useStyles = makeStyles((theme) => ({
    root: {
        padding: '10px',
    },
    title: {
        flexGrow: 1,
        display: 'inline-block',
        fontSize: '30px',
        paddingLeft: '10px',
        color : 'rgb(104,104,106)',
    },
    navStyle: {
        paddingTop : '5px',
        display: 'inline-block',
        float: 'right',
    },
    menuStyle: {
        textDecoration: 'none',
        color : 'rgb(104,104,106)',
        padding : '10px',
        transition : 'all 0.1s',
        '&:hover':{
            backgroundColor : '#F2F3F4',
        }
    },
}));




function Navigation() {

    const classes = useStyles();
    const is_login = useSelector(state => state.userAction.is_login);
    const is_seller = useSelector(state => state.userAction.is_seller);
    const history = useHistory();
    const dispatch = useDispatch();
    const logout = () => {
        localStorage.clear();
        Axios.head("/logout");
        dispatch({ type: 'LOGOUT' })
        history.push('/');
        alert('로그아웃');
    }
    const loginMenu = () => {
        if (is_login) {
            if (is_seller) {
                return (
                    <Typography>
                        <Link className={classes.menuStyle} to='/seller'>HOME</Link>
                        <Link className={classes.menuStyle} to='/user/enroll'>ENROLL</Link>
                        <Link className={classes.menuStyle} to='/seller/request'>LIST</Link>
                        <Link className={classes.menuStyle} to='/seller/mypage'>MYPAGE</Link>
                        <Link className={classes.menuStyle} to='#' onClick={logout}>LOGOUT</Link>
                    </Typography>
                )
            } else {
                return (
                    <Typography>
                        <Link className={classes.menuStyle} to='/user'>HOME</Link>
                        <Link className={classes.menuStyle} to='/user/enroll'>ENROLL</Link>
                        <Link className={classes.menuStyle} to='/user/mypage'>MYPAGE</Link>
                        <Link className={classes.menuStyle} to='#' onClick={logout}>LOGOUT</Link>
                    </Typography> 
                )
            }
        } else {
            return (
                <Typography>
                    <Link className={classes.menuStyle} to='/login'>LOGIN</Link>
                    <Link className={classes.menuStyle} to='/join'>JOIN</Link>
                </Typography>
            )
        }
    }

    return (
        <div style={{ backgroundColor: '#f9f9f9' }}>
            <Container className={classes.root}>
                <Toolbar>
                    <Typography className={classes.title}>
                        SELECTPERT
                    </Typography>
                    <div className={classes.navStyle}>
                        {loginMenu()}
                    </div>
                </Toolbar>
            </Container>
        </div>
    )
}

export default Navigation;