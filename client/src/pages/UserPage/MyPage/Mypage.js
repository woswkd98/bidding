import React, { useEffect,useState,useCallback } from 'react';
import { Link } from 'react-router-dom';
import MyRequest from './MyRequest';
import { Grid, Container,  Avatar, Typography } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import { useSelector } from 'react-redux';
import ProfileModal from '../../../components/Profile/ProfileModal';
import { makeStyles } from '@material-ui/core/styles';
import Axios from 'axios';

const useStyles = makeStyles((theme) => ({
    heroContent: {
        padding: theme.spacing(8, 0, 6),
        color: 'rgb(104,104,106)',

    },
    avatarStyle: {
        margin: 'auto',
        width: '180px',
        height: '180px',
        backgroundColor: 'rgb(104,104,106)',
        border: 'none',
    },
    gridStyle: {
        textAlign: 'center',
        marginRight: '30px',
    },
    buttonStyle: {
        width: '100%',
        margin: '1px'
    }
}));

const MyPage = () => {
    const classes = useStyles();
    const userName = localStorage.getItem('userName');
    const user_id = localStorage.getItem('user_id')
    const is_seller = useSelector(state => state.userAction.is_seller);
    const [open, setOpen] = useState(false);
    const [data, setData] = useState({});

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const getMyProfileImage = useCallback(() => {
        /*Axios.get('/users/image/' + localStorage.getItem("user_id"))
        .then(res => {
            console.log(res.data);
            setData(res.data);
        })
        .catch(err=>{
            console.log(err);
        })*/
    },[user_id])

    useEffect(()=>{
        getMyProfileImage();
    },[getMyProfileImage])

    return (
        <Container className={classes.heroContent}>
            <Grid container>
                <Grid className={classes.gridStyle} item xs={12} md={2}>
                    <Avatar className={classes.avatarStyle} src={localStorage.getItem("profileImage") } />
                    <br />
                    <Typography variant="h5" gutterBottom>{userName}</Typography>
                    {is_seller
                        &&
                    <Button className={classes.buttonStyle} variant="outlined" component={Link} to='/seller/mypage'>
                        판매 정보
                    </Button>
                    }
                    <Button className={classes.buttonStyle} variant="outlined" component={Link} to='/user/mypage'>
                        구매 정보
                    </Button>
                    <Button className={classes.buttonStyle} variant="outlined" onClick={handleClickOpen}>
                        나의 프로필
                    </Button>
                    <br />
                </Grid>
                <Grid item xs={12} md={9}>
                    <MyRequest />
                </Grid>
                <ProfileModal name={userName} open={open} onClose={handleClose} user_id={user_id} />
            </Grid>
        </Container>
    )
}

export default MyPage;