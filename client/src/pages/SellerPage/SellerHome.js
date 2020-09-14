import React,{useEffect,useState} from 'react';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import {  Paper } from '@material-ui/core';
import Background from '../../img/background3.jpg'
import { Link } from 'react-router-dom';
import ArrowForwardIcon from '@material-ui/icons/ArrowForward';
import Zoom from '@material-ui/core/Zoom';


const useStyles = makeStyles((theme) => ({
    divStyle: {
        backgroundImage: `url(${Background})`,
        height: '400px',
        marginBottom : '80px',
    },
    heroContent: {
        padding: theme.spacing(8, 0, 6),
    },
    containerStyle: {
        backgroundColor: 'white',
        padding: theme.spacing(3, 3, 3),
    },
    buttonStyle: {
        float: 'right',
        marginTop: '8px',
        marginRight: '50px',
    },
    linkStyle: {
        color: 'rgb(104,104,106)',
        float : 'right',
    }

}));

const SellerHome = () => {
    const classes = useStyles();
    const [checked, setChecked] = useState(false);

    useEffect(() => {
        setChecked(true);
        return () => {
            setChecked(false);
        }
    }, [])

    return (
        <>
            <div className={classes.divStyle}>
                <Container>
                    <Grid container className={classes.heroContent}>
                        <Grid item xs={8}>
                            <Typography variant="h4" color="textSecondary" gutterBottom>
                                Try it!
                        </Typography>
                            <Typography variant="h3" color="textSecondary" paragraph>
                                When you want to have own website,<br />
                                Here is best solution.
                        </Typography>
                        </Grid>
                    </Grid>
                </Container>
            </div>
            <Container>
                <Grid container spacing={3} maxWidth="md">
                    <Grid item xs={12} sm={6} md={6}>
                    <Zoom in={checked} timeout={250}>
                        <Paper className={classes.containerStyle} elevation={3}>
                            <Typography variant="h5" color="textSecondary" gutterBottom>
                                is Guest?
                            </Typography>
                            <Typography variant="h6" color="textSecondary" paragraph>
                                전문가라도 다른 전문가에게 요청할 수 있습니다.
                                <Button component={Link} className={classes.linkStyle} to="/user/enroll">
                                    요청하기<ArrowForwardIcon fontSize="small" />
                                </Button>
                            </Typography>
                        </Paper>
                    </Zoom>
                    </Grid>
                    <Grid item xs={12} sm={6} md={6}>
                        <Zoom in={checked} timeout={500}>
                            <Paper className={classes.containerStyle} elevation={3}>
                                <Typography variant="h5" color="textSecondary" gutterBottom>
                                    is Expert?
                                </Typography>
                                <Typography variant="h6" color="textSecondary" paragraph>
                                    고객들의 요청을 확인하세요!
                                    <Button component={Link} className={classes.linkStyle} to="/seller/request">
                                        요청 리스트<ArrowForwardIcon fontSize="small" />
                                    </Button>
                                </Typography>
                            </Paper>
                        </Zoom>
                    </Grid>
                </Grid>
            </Container>
        </>
    );
}

export default SellerHome;