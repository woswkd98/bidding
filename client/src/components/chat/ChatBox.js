import React, { useEffect, useState, useRef } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import { Container, InputAdornment, OutlinedInput } from '@material-ui/core';
import ChatList from './ChatList';
import { useSelector } from 'react-redux';
import SendIcon from '@material-ui/icons/Send';
import IconButton from '@material-ui/core/IconButton';
import SockJsClient from 'react-stomp';
import Axios from 'axios';

const useStyles = makeStyles((theme) => ({
    root: {
        width: '100%',
        background: 'lightGray',
        marginBottom: '15px',
    },
    inline: {
        display: 'inline',
    },
    myChat: {
        float: 'right',
    },
    container: {
        width: '100%',
        height: '600px',
        overflowY: 'auto',
        overflowX: 'hidden',
        margin: '0px auto',
        padding: 0,
        '&::-webkit-scrollbar': {
            display: 'none',
        },
    },
    inputStyle: {
        width: '100%',
        padding: '15px 0px 15px 15px',
    }
}));



function ChatBox({ userInfo, avatarSrc }) {

    const classes = useStyles();

    const [message, setMessage] = useState('');

    const [newMessages, setNewMessage] = useState([]);

    const userName = useSelector(state => state.userAction.userName);
    const [data, setData] = useState([]);

    const clientRef = useRef();
    const objDiv = useRef();

    const sendMessage = () => {
        try {
            let send_message = {
                roomId: userInfo.room,
                userName: localStorage.getItem("userName"),
                message: message
            }
            clientRef.current.sendMessage("/pub/chat", JSON.stringify(send_message))

        } catch (err) {
            console.log(err);
        }
        setMessage('');
    }

    useEffect(() => {
        Axios.get("/chats/" + userInfo.room)
        .then(res => {
            setData(res.data);
        })
        .catch(err => {
            console.log(err);
        })
    }, [])

    useEffect(() => {
        objDiv.current.scrollTop = objDiv.current.scrollHeight;
    }, [newMessages])


    const lastMessageList =data.map((data, i) => {
        return <ChatList key={i} data={data} userName={userName} avatarSrc={avatarSrc} />
    })

    const newMessageList = newMessages.map((data, i) => {
        return <ChatList key={i} data={data} userName={userName} avatarSrc={avatarSrc} />
    })

    const onChangeMessage = (e) => {
        setMessage(e.target.value);
    }

    const onMessageReceive = (msg) => {
        setNewMessage(newMessages => (
            [...newMessages, msg]
        ));
    }

    const onSubmitForm = (e) => {
        if (e.keyCode === 13)
            if (!e.shiftKey) {
                if (message) {
                    e.preventDefault();
                    sendMessage();
                    setMessage('');
                } else {
                    e.preventDefault();
                    return;
                }
            }
    }

    const onClickForm = () => {
        if (message) {
            sendMessage();
            setMessage('');
        }
    }
    
    return (
        <div className={classes.root}>
            <Container ref={objDiv} className={classes.container}>
                <List className={classes.root}>
                  {lastMessageList}
                  {newMessageList}
                </List>
            </Container>
            <form onKeyDown={onSubmitForm}>
                <OutlinedInput
                    className={classes.inputStyle}
                    rowsMax={4}
                    multiline
                    value={message}
                    endAdornment={
                        <InputAdornment position="end">
                            <IconButton onClick={onClickForm}>
                                <SendIcon />
                            </IconButton>
                        </InputAdornment>
                    }
                    onChange={onChangeMessage} />
            </form>
            <SockJsClient
                url={"http://localhost:1234/chat"}
                topics={["/sub/room/" + userInfo.room]}
                onMessage={(msg) => { onMessageReceive(msg) }}
                ref={(client) => { clientRef.current = client }}
            />
        </div>
    );
}

export default ChatBox;