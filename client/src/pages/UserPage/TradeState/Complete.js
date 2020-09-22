import React, {useState} from 'react'
import RequestCard from '../../../components/RequestCard'
import { Container, Grid, Typography,  makeStyles, Avatar, Button } from '@material-ui/core'
import Rating from '@material-ui/lab/Rating';
import PersonIcon from '@material-ui/icons/Person';
import CheckCircleIcon from '@material-ui/icons/CheckCircle';
import EditIcon from '@material-ui/icons/Edit';
import Review from '../Review';


const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
        color: 'rgb(104,104,106)'
    },
    gridStyle: {
        margin: '4% auto',
        width: "80%",
    },
    loadingStyle: {
        display: 'block',
        margin: '4% auto',
    },
    large: {
        width: '100px',
        height: '100px',
    },
    icon: {
        display: 'block',
        fontSize: 50,
        marginTop: '50px',
        margin: 'auto',
        color: "#4caf50",
    }
}));

const Complete = ({ data, requestData }) => {

    const classes = useStyles();

    const [open, setOpen] = useState(false);

    const onClickReviewOpen = () => {
        console.log(data,requestData);
        // setOpen(true);
    }

    return (
        <Container className={classes.root}>
            <Grid className={classes.gridStyle} container spacing={9}>
                <Grid item xs={12} md={6}>
                    <RequestCard obj={requestData} />
                </Grid>
                <Grid item xs={12} md={6}>
                    <CheckCircleIcon className={classes.icon} />
                    <br />
                    <Typography align="center" variant="h4" paragraph>
                        거래 완료!
                    </Typography>
                    <br />
                    <Typography variant="h5" paragraph>
                        전문가
                    </Typography>
                    <Grid container spacing={6}>
                        <Grid item xs={3}>
                            {localStorage.getItem("profileImage")
                                ?
                                <Avatar src={localStorage.getItem("profileImage")} className={classes.large} />
                                :
                                <Avatar className={classes.large}>
                                    <PersonIcon style={{ fontSize: 100 }} />
                                </Avatar>
                            }
                        </Grid>
                        <Grid item xs={6} style={{ marginTop: '8px' }}>
                            <Typography variant="h6">
                                {data.user_name}
                            </Typography>
                            <Rating name="half-rating-read" value={1} precision={0.5} readOnly />
                            <Typography>3.5/5.0</Typography>
                        </Grid>
                    </Grid>
                    <br />
                    <Typography style={{ display: 'flex', alignItems: 'center' }}>
                        거래는 만족스러우셨나요? &nbsp;&nbsp;&nbsp;
                            <Button
                            onClick={onClickReviewOpen}
                            variant="contained"
                            color="primary"
                            size="small"
                            className={classes.button}
                            endIcon={<EditIcon />}
                        >
                            후기 작성하기
                        </Button>
                    </Typography>
                </Grid>
            </Grid>
            <Review open={open} setOpen={setOpen} seller_id={data.seller_id}/>
        </Container>
    )
}

export default Complete
