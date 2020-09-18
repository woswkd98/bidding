import React, { useEffect, useState, useCallback } from 'react';
import { CircularProgress, Backdrop, Divider, IconButton, Grid } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import ChatBox from './ChatBox';
import DialogTitle from '@material-ui/core/DialogTitle';
import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import CloseIcon from '@material-ui/icons/Close';
import Paper from '@material-ui/core/Paper';
import Draggable from 'react-draggable';
import Axios from 'axios';

const useStyles = makeStyles((theme) => ({
    backdrop: {
        zIndex: theme.zIndex.drawer + 1,
        color: '#fff',
    },
}));

function PaperComponent(props) {
    return (
        <Draggable handle="#draggable-dialog-title" cancel={'[class*="MuiDialogContent-root"]'}>
            <Paper {...props} />
        </Draggable>
    );
}

const Main = ({ request, seller, open, onClose, avatarSrc }) => {

    const classes = useStyles();

    const [data, setData] = useState({});

    const [loading, setLoading] = useState(true);

    const room_id = request + "/" + seller;

    const getMyRoom = useCallback(() => {
        Axios.put('/rooms', {
            request_id: request,
            seller_id: seller,
            room_id : room_id
        })
            .then(res => {
                setData(res.data);
                setLoading(false);
            })
            .catch(err => {
                console.log(err);
            })
    },[request,seller])

    useEffect(() => {
        if (open) {
            getMyRoom();
        }
        return () => {
            if(request !== null) Axios.delete("/rooms/" + room_id);
            console.log('disconnect');
        }
    }, [open, getMyRoom])

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
        <Dialog
            open={open}
            onClose={onClose}
            PaperComponent={PaperComponent}
            aria-labelledby="draggable-dialog-title"
        >
            <DialogTitle style={{ cursor: 'move', padding: '0px' }} id="draggable-dialog-title">
                <Grid container>
                    <Grid item xs={6}>
                        <div style={{ marginLeft: '14px', marginTop: '10px' }}>채팅</div>
                    </Grid>
                    <Grid item xs={6}>
                        <IconButton onClick={onClose} style={{ float: 'right' }}>
                            <CloseIcon />
                        </IconButton>
                    </Grid>
                </Grid>
            </DialogTitle>
            <Divider />
            <DialogContent style={{ backgroundColor: 'lightgray' }}>
                <ChatBox userInfo={{
                    room: room_id
                   // messages: data.messages,
                }}
                    avatarSrc={avatarSrc}
                />
            </DialogContent>
        </Dialog>
    )
}

export default Main;