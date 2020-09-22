import React from 'react'
import { useHistory } from 'react-router-dom';
import { Button, Container, Grid, Typography, Avatar, List, ListItem, ListItemText } from '@material-ui/core';
import Rating from '@material-ui/lab/Rating';
import UserCommuButton from '../../../components/UserCommuButton';
import RequestCard from '../../../components/RequestCard';
import { makeStyles } from '@material-ui/core/styles';
import NavigateNextIcon from '@material-ui/icons/NavigateNext';
import PersonIcon from '@material-ui/icons/Person';
import Notice from '../../../components/Notice';
import Axios from 'axios';

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
}));

const Progress = ({ data, requestData }) => {

    const classes = useStyles();

    const history = useHistory();
    
    const tradeComplete = () => {
        const AreYouSure = window.confirm('거래가 완료되었습니까?');
        if (AreYouSure) {
            Axios.post('/biddings/complete',{
                biddingId: data.bidding_id,
                
            })
            .then(res=>{
                alert(res.data);
                history.replace('/user/mypage');
            })
            .catch(err=>{
                console.log(err);
            })
        }
    }

    const tradeCancel = () => {
        const AreYouSure = window.confirm('취소하시겠습니까?');
        if (AreYouSure) {
            Axios.post('/biddings/tradeCancel',{
                requestId: requestData.request_id,
            })
            .then(res=>{
                alert(res.data);
                history.replace('/user/mypage');
            })
            .catch(err=>{
                console.log(err);
            })
        }
    }
    console.log(requestData);
    console.log(data);
    return (
        <Container className={classes.root}>
            <Grid className={classes.gridStyle} container spacing={9}>
                <Grid item xs={12} md={6}>
                    <RequestCard obj={requestData} />
                </Grid>
                <Grid item xs={12} md={6}>
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
                                {requestData.user_name}
                            </Typography>
                            <Rating name="half-rating-read" value={1} precision={0.5} readOnly />
                            <Typography>3.5/5.0</Typography>
                        </Grid>
                    </Grid>
                    <List>
                        <ListItem>
                            <ListItemText primary={data.price} primaryTypographyProps={{ variant: "h5" }} />
                        </ListItem>
                        <ListItem>
                            <NavigateNextIcon fontSize="small" />&nbsp;&nbsp;
                            <ListItemText primary='가격은 상호 협의를 통해 변동이 있을 수 있습니다.' />
                        </ListItem>
                    </List>
                    <UserCommuButton avatarSrc={localStorage.getItem("profileImage")} request_id={requestData.request_id} seller_id={requestData.seller_id}/>
                    <Button onClick={tradeComplete} style={{ width: '100%' }} variant="outlined">
                        거래 완료
                    </Button>
                    <Button onClick={tradeCancel} style={{ width: '100%' }} variant="outlined">
                        거래 취소
                    </Button>
                </Grid>
            </Grid>
            <Notice/>
        </Container>
    )
}


export default Progress
