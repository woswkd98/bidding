import React, { useState } from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Backdrop from '@material-ui/core/Backdrop';
import CircularProgress from '@material-ui/core/CircularProgress';
import { makeStyles } from '@material-ui/core/styles';
import { useSelector } from 'react-redux';
import Axios from 'axios';

const useStyles = makeStyles((theme) => ({
    backdrop: {
        zIndex: theme.zIndex.drawer + 1,
        color: '#fff',
    },
}));


const Bidding = ({ open, setOpen, data }) => {

    const classes = useStyles();

    const [price, setPrice] = useState(0);

    const [loading, setLoading] = useState(false);

    const user_id = useSelector(state => state.userAction.user_id);

    const userName = useSelector(state => state.userAction.userName);

    const sendBid = () => {
        console.log(data);
        console.log(localStorage.getItem("is_seller"));
        console.log(data.request_id);
        Axios.put('/biddings', {
            requestId: data.request_id,
            sellerId: localStorage.getItem("is_seller"),
            price: price,
            state : " 거래 진행중",
        })
        .then(res => {
            alert(res.data);
            setLoading(false);
            setOpen(false);
        })
        .catch(err => {
            console.log(err);
        })
    }



    const onChangePrice = (e) => {
        setPrice(Number(e.target.value));
    }

    const onSubmitForm = (e) => {
        e.preventDefault();
        setLoading(true);
        sendBid();
    }

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div>
            {loading
                ?
                <Backdrop className={classes.backdrop} open={loading}>
                    <CircularProgress color="inherit" />
                </Backdrop>
                :
                <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">입찰하기</DialogTitle>
                    <DialogContent>
                        <DialogContentText>
                            입력하신 가격과 함께 프로필이 구매자에게 전달됩니다.
                    </DialogContentText>
                        <form onSubmit={onSubmitForm}>
                            <TextField
                                autoFocus
                                margin="dense"
                                id="price"
                                label="총 금액"
                                type="number"
                                fullWidth
                                onChange={onChangePrice}
                            />
                        </form>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={onSubmitForm} color="primary">
                            입찰
                        </Button>
                        <Button onClick={handleClose} color="primary">
                            취소
                        </Button>
                    </DialogActions>
                </Dialog>
            }
        </div>
    )
}

export default Bidding;