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
  }
}));

const JoinForm = ({ handleLogin }) => {

  const classes = useStyles();

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
        alert('이미 가입된 이메일입니다.')
      }
    })
    .catch(err=>{
      console.log(err);
    })
  }

  const onSubmitForm = (e) => {
    e.preventDefault();
    if (userInfo.name === '' || userInfo.email === '' || userInfo.pwd === '') {
      alert('빈 항목이 존재합니다.')
      

    } else {
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
              onChange={onChangeInfo}
            />
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
            />
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
            />
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