import React,{useState} from 'react'
import RequestCard from '../../../components/RequestCard'
import { Container, Grid, Typography, makeStyles,Button } from '@material-ui/core'
import { useSelector } from 'react-redux';
import Chat from '../../../components/chat';
import { useHistory } from 'react-router-dom';
import Counter from '../../../components/Counter';
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
    icon: {
        display: 'block',
        fontSize: 50,
        marginTop : '50px',
        margin: 'auto',
    }
}));

const Watting = ({ data }) => {

    const classes = useStyles();

    const history = useHistory();

    const user_id = useSelector(state => state.userAction.user_id)

    const bidCancel = () => {
        const AreYouSure = window.confirm("취소하시겠습니까?");
        if(AreYouSure){
            Axios.post('/biddings/biddingCancel',{
                biddingId : data.request_id,
            })
            .then(res => {
                alert(res.data);
                history.push('/seller/mypage');
            })
            .catch(err => {
                console.log(err);
            })
        }
    }


    const [chatOpen, setChatOpen] = useState(false);

    const handleChatOpen = () => {
        setChatOpen(true);
    }

    const handleChatClose = () => {
        setChatOpen(false);
    }


    return (
        <Container className={classes.root}>
            <Grid className={classes.gridStyle} container spacing={7}>
                <Grid item xs={12} md={6}>
                    <RequestCard obj={data} />
                </Grid>
                <Grid item xs={12} md={6}>
                    <Typography align="center" variant="h5" gutterBottom>
                        낙찰 대기중입니다.
                    </Typography>
                    <Typography align="center" variant="h6" gutterBottom>
                        <Counter data={data}/>
                    </Typography>
                    <br/>
                    <Typography align="center" variant="h6" paragraph>
                        구매자의 응찰을 기다리는 중입니다.<br/>
                        응찰 전에는 1:1 채팅만 할 수 있습니다.
                    </Typography>
                    <Button onClick={handleChatOpen} style={{ width: '100%' }} variant="outlined">
                        1:1 채팅
                    </Button>
                    <Button onClick={bidCancel} style={{ width: '100%' }} variant="outlined">
                        입찰 취소
                    </Button>
                </Grid>
            </Grid>
            <Chat open={chatOpen} onClose={handleChatClose} request={data.request_id} seller={user_id} />
        </Container>
    )
}

export default Watting;