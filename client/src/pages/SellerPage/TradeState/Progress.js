import React, { useState } from 'react'
import { useHistory } from 'react-router-dom';
import { Button, Container, Grid, List, ListItem, ListItemText } from '@material-ui/core';
import RequestCard from '../../../components/RequestCard';
import { makeStyles } from '@material-ui/core/styles';
import NavigateNextIcon from '@material-ui/icons/NavigateNext';
import Notice from '../../../components/Notice';
import { useSelector } from 'react-redux';
import Chat from '../../../components/chat';
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

const Progress = ({ data }) => {

    const user_id = useSelector(state => state.userAction.user_id)
    console.log("pro");
    console.log(data.request_id);
    const classes = useStyles();

    const history = useHistory();
    console.log(data);
    const [chatOpen, setChatOpen] = useState(false);

    const handleChatOpen = () => {
        setChatOpen(true);
    }

    const handleChatClose = () => {
        setChatOpen(false);
    }

    const tradeCancel = () => {
        const AreYouSure = window.confirm('취소하시겠습니까?');
        if(AreYouSure){
            Axios.post('/biddings/tradeCancel',{
                requestId: data.request_id
            })
            .then(res => {
                alert(res.data)
                history.replace('/seller/mypage');
            })
            .catch(err => {
                console.log(err);
            })
        }
    }



    return (
        <Container className={classes.root}>
            <Grid className={classes.gridStyle} container spacing={9}>
                <Grid item xs={12} md={6}>
                    <RequestCard obj={data} />
                </Grid>
                <Grid item xs={12} md={6}>
                    <List>
                        <ListItem>
                            <ListItemText primary="거래 진행중" primaryTypographyProps={{ variant: "h4" }} />
                        </ListItem>
                        <ListItem>
                            <ListItemText primary={`₩${data.price}`} primaryTypographyProps={{ variant: "h5" }} />
                        </ListItem>
                        <ListItem>
                            <NavigateNextIcon fontSize="small" />&nbsp;&nbsp;
                            <ListItemText primary='가격은 상호 협의를 통해 변동이 있을 수 있습니다.' />
                        </ListItem>
                    </List>
                    <Button onClick={handleChatOpen} style={{ width: '100%' }} variant="outlined">
                        1:1 채팅
                    </Button>
                    <Button onClick={tradeCancel} style={{ width: '100%' }} variant="outlined">
                        거래 취소
                    </Button>
                </Grid>
            </Grid>
            <Notice />
            <Chat open={chatOpen} onClose={handleChatClose} request={data.request_id} seller={user_id} />
        </Container>
    )
}


export default Progress
