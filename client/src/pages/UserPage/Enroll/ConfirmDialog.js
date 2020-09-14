import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Slide from '@material-ui/core/Slide';

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const AlertDialogSlide = ({open,setOpen,handleNext}) => {

  const handleClose = () => {
    setOpen(false);
  };

  const agree = () => {
    handleNext();
    handleClose();
  }

  return (
    <div>
      <Dialog
        open={open}
        TransitionComponent={Transition}
        keepMounted
        onClose={handleClose}
        aria-labelledby="alert-dialog-slide-title"
        aria-describedby="alert-dialog-slide-description"
      >
        <DialogTitle id="alert-dialog-slide-title">{"알림"}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-slide-description">
            태그를 입력하지 않았습니다.<br/>
            넘어가시겠습니까?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={agree} color="primary">
            확인
          </Button>
          <Button onClick={handleClose} color="primary">
            취소
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

export default AlertDialogSlide;