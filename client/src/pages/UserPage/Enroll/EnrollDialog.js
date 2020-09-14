import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Backdrop from '@material-ui/core/Backdrop';
import CircularProgress from '@material-ui/core/CircularProgress';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: '#fff',
  },
}));


export default function AlertDialog({ open, onClickSendRequest, setOpen, loading }) {

  const classes = useStyles();

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
        <Dialog
          open={open}
          onClose={handleClose}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description"
        >
          <DialogTitle id="alert-dialog-title">{"전문가들에게 견적을 보내시겠습니까?"}</DialogTitle>
          <DialogContent>
            <DialogContentText id="alert-dialog-description">
              최대 10명의 전문가들이 입찰을 할 것입니다.<br />
            선택하기 전에 전문가와 1:1 상담을 하실 수 있습니다.
          </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={onClickSendRequest} color="primary" autoFocus>
              보내기
          </Button>
            <Button onClick={handleClose} color="primary">
              취소
          </Button>
          </DialogActions>
        </Dialog>
      }
    </div>
  );
}
