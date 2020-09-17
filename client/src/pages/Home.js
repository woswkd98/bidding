import React, { useState } from 'react';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import { Slide, Dialog, Grid } from '@material-ui/core';
import Background from '../img/background3.jpg';
import Login from './Login';
import Join from './Join';


const useStyles = makeStyles((theme) => ({
    title: {
        color: 'rgb(104,104,106)',
    },
    nav: {
        display: 'inline-block',
        float: 'right',
        color: 'rgb(104,104,106)',
    },
    backgroundImage: {
        backgroundImage: `url(${Background})`,
        height: '100vh',
        overflow: 'hidden',
        margin: 0,
        padding: 0,
    },
    heroContent: {
        padding: "140px 10px",
        color: 'rgb(104,104,106)',
    },
    heroButtons: {
        marginTop: theme.spacing(4),
      },    
    navBar: {
        display: 'flex',
        alignItems: 'center',
        margin: '10px',
    },
    divStyle: {
        color: 'white',
        // backgroundImage: `url(${Background1})`,
        padding: theme.spacing(4, 4, 4),
    },
    buttonStyle: {
        marginTop: '8px',
        marginRight: '50px',
    }
}));

const Transition1 = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="right" ref={ref} {...props} />;
});

const Transition2 = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="left" ref={ref} {...props} />;
});


const Home = () => {
    const classes = useStyles();

    const [loginChecked, setLoginChecked] = useState(false);
    const [joinChecked, setJoinChecked] = useState(false);

    const handleHome = () => {
        setJoinChecked(false);
        setLoginChecked(false);
    }

    const handleLogin = () => {
        setJoinChecked(false);
        setLoginChecked(true);
    }

    const handleJoin = () => {
        setLoginChecked(false);
        setJoinChecked(true);
    }

    return (
        <div className={classes.backgroundImage}>
            <Container>
                <div className={classes.navBar}>
                    <Typography className={classes.title} onClick={handleHome} component={Button} variant="h4">
                        HELL
                    </Typography>
                </div>
            </Container>
            <Container className={classes.heroContent} maxWidth="sm">
                <Typography component="h1" variant="h2" align="center" color="textPrimary" gutterBottom>
                    HELL
                </Typography>
                <Typography variant="h5" align="center" color="textSecondary" paragraph>
                    웹사이트, 간단한 등록으로 전문가를 찾아보세요.<br/>
                    고객과 it 전문가를 연결해주는 중개 플랫폼
                </Typography>
                <div className={classes.heroButtons}>
                    <Grid container spacing={2} justify="center">
                        <Grid item>
                            <Button onClick={handleLogin} variant="contained" color="primary">
                                로그인
                            </Button>
                        </Grid>
                        <Grid item>
                            <Button onClick={handleJoin} variant="outlined" color="primary">
                                회원가입
                            </Button>
                        </Grid>
                    </Grid>
                </div>
            </Container>
            <Dialog open={loginChecked}
                TransitionComponent={Transition1}
                keepMounted
                onClose={handleHome}
                aria-labelledby="alert-dialog-slide-title"
                aria-describedby="alert-dialog-slide-description">
                <Login handleJoin={handleJoin} />
            </Dialog>
            <Dialog open={joinChecked}
                TransitionComponent={Transition2}
                keepMounted
                onClose={handleHome}
                aria-labelledby="alert-dialog-slide-title"
                aria-describedby="alert-dialog-slide-description">
                <Join handleLogin={handleLogin} />
            </Dialog>
        </div>
    );
}

export default Home;