import React, { useState } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import Axios from 'axios';


const useStyles = makeStyles((theme) => ({
  paper: {
    padding: theme.spacing(7),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    backgroundColor: '#F2F3F4',
    borderRadius: '10px',
    color: 'rgb(104,104,106)',
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing(3),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
  button: {
    color: 'rgb(104,104,106)',
    padding: 0,
  },
  smallText : {
    color : 'red',
  }
}));

const JoinForm = ({ handleLogin }) => {

  const classes = useStyles();

  const [nameChecked, setNameChecked] = useState(true);
  const [emailChecked, setEmailChecked] = useState(true);
  const [pwdChecked, setPwdChecked] = useState(true);

  const [userInfo, setInfo] = useState({
    name: '',
    email: '',
    pwd: '',

  })


  const onChangeInfo = (e) => {
    setInfo({
      ...userInfo,
      [e.target.name]: e.target.value,
    })
  }

  const {name,email,pwd} = userInfo;

  const checkName = () => {
    const reg = /^[가-힣]{2,}$/.test(name);
    if(reg){
      setNameChecked(true);
    } else {
      setNameChecked(false);
    }
  }

  const checkEmail = () => {
    const reg = /^[a-z0-9_+.-]+@([a-z0-9-]+\.)+[a-z0-9]{2,4}$/.test(email);
    if(reg){
      setEmailChecked(true);
    } else {
      setEmailChecked(false);
    }
  }

  const checkPwd = () => {
    const reg = /[A-Za-z0-9!@#$%^&*()-_+=]{8,}$/.test(pwd);
    if(reg){
      setPwdChecked(true);
    } else {
      setPwdChecked(false);
    }
  }

  const join = () => {
    Axios.put('/users', {
      userName: userInfo.name,
      userEmail: userInfo.email,
      userPassword: userInfo.pwd,
    })
    .then(res=>{
      if (res.data) {
        handleLogin();
      } else {
        alert(res.data)
      }
    })
    .catch(err=>{
      console.log(err);
    })
  }

  const onSubmitForm = (e) => {
    e.preventDefault();
    checkName();
    checkEmail();
    checkPwd();
    if (nameChecked && emailChecked && pwdChecked) {
      join();
    }
  }

  return (
    <Container className={classes.paper} component="main" maxWidth="xs">
      <Avatar className={classes.avatar}>
        <LockOutlinedIcon />
      </Avatar>
      <Typography component="h1" variant="h5">
        회원가입
        </Typography>
      <form className={classes.form} noValidate onSubmit={onSubmitForm}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
              autoComplete="name"
              name="name"
              variant="outlined"
              required
              fullWidth
              id="name"
              label="이름"
              autoFocus
              autoComplete="name"
              onChange={onChangeInfo}
              onBlur={checkName}
            />
            {!nameChecked && <small className={classes.smallText}>2글자 이상의 한글로 작성해 주세요.</small>}
          </Grid>
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              id="email"
              label="이메일"
              name="email"
              autoComplete="email"
              type="email"
              onChange={onChangeInfo}
              onBlur={checkEmail}
            />
            {!emailChecked && <small className={classes.smallText}>올바른 이메일 형식이 아닙니다.</small>}
          </Grid>
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              name="pwd"
              label="비밀번호"
              type="password"
              id="password"
              autoComplete="current-password"
              onChange={onChangeInfo}
              onBlur={checkPwd}
            />
            {!pwdChecked && <small className={classes.smallText}>8자 이상 입력해주세요.</small>}
          </Grid>
        </Grid>
        <Button
          type="submit"
          fullWidth
          variant="contained"
          color="primary"
          className={classes.submit}
        >
          회원가입
          </Button>
        <Grid container justify="flex-end">
          <Grid item>
            <Button className={classes.button} onClick={handleLogin}>
              이미 아이디가 있으신가요? 로그인
              </Button>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}

export default JoinForm;