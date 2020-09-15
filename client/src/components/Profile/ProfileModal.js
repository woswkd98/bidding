import React, { useEffect, useState, useCallback } from 'react'
import CircularProgress from '@material-ui/core/CircularProgress';
import Profile from './Profile';
import Backdrop from '@material-ui/core/Backdrop';
import { makeStyles } from '@material-ui/core/styles';
import Axios from 'axios';

const useStyles = makeStyles((theme) => ({
    backdrop: {
        zIndex: theme.zIndex.drawer + 1,
        color: '#fff',
    },
}));


const ProfileModal = ({ onClose, open, user_id }) => {

    const classes = useStyles();
    const [data, setData] = useState({});
    const [loading, setLoading] = useState(true);

    const getMyProfile = useCallback(() => {
        const sellerId = localStorage.getItem("is_seller")
        console.log(sellerId);
        Axios.get('/sellers/' + sellerId)
            .then(res => {
                console.log("profileModal");
                console.log(res.data);
                setData(res.data);
                setLoading(false);
            })
            .catch(err => {
                console.log(err);
            })
    },[user_id])


    useEffect(() => {
        if (open) {
            getMyProfile();
        }
    }, [open,getMyProfile])


    if (!open) {
        return <></>
    }

    if (loading) {
        return (
            <Backdrop className={classes.backdrop} open={loading}>
                <CircularProgress color="inherit" />
            </Backdrop>
        )
    }


    return (
        <Profile profile={data} onClose={onClose} open={open} user_id={user_id} />
    )


}


export default ProfileModal
