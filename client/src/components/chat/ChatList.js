import React from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import Avatar from '@material-ui/core/Avatar';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles(() => ({
    myTextStyle: {
        display: 'inline-block',
        border: "1px solid yellow",
        borderRadius: '5px',
        padding: "2px 10px",
        backgroundColor: 'yellow',
        color: 'black',
        fontSize: '15px',
        maxWidth: '300px',
        whiteSpace: 'pre-line',
        wordBreak: 'break-all',
    },
    otherTextStyle: {
        display: 'inline-block',
        border: "1px solid white",
        borderRadius: '5px',
        padding: "2px 10px",
        backgroundColor: 'white',
        color: 'black',
        fontSize: '15px',
        maxWidth: '300px',
        whiteSpace: 'pre-line',
        wordBreak: 'break-all',
    },
    systemStyle: {
        textAlign: "center",
        margin: "15px 20px",
    }
}))




function ChatList({ data, userName, avatarSrc }) {

    console.log(data);
    const classes = useStyles();
    
    return (
        <>
            {(data.userName === 'system') ?
    
                <>
                    <div className={classes.systemStyle}>
                        {data.message}
                    </div>
                </> :
          
                (data.userName=== userName) ?
                    <>
                        <ListItem alignItems="center" style={{ textAlign: 'right'}}>
                            <ListItemText
                                primary={
                                    <React.Fragment>
                                        <small>{ new Date(data.uploadAt).toLocaleTimeString()}</small>
                                &nbsp;&nbsp;
                                <span className={classes.myTextStyle}>
                                            {data.message}
                                        </span>
                                    </React.Fragment>
                                }
                            />
                        </ListItem>
                    </> :
                    <>
                        <ListItem alignItems="center">
                            <ListItemAvatar>
                                <Avatar alt="Remy Sharp" src={avatarSrc} />
                            </ListItemAvatar>
                            <ListItemText
                                primary={<small>{data.userName}</small>}
                                secondary={
                                    <React.Fragment>
                                        <span className={classes.otherTextStyle}>
                                            {data.message}
                                        </span>
                            &nbsp;&nbsp;
                            <small>{new Date(data.uploadAt).toLocaleTimeString()}</small>
                                    </React.Fragment>
                                }
                            />
                        </ListItem>
                    </>
            }
        </>
    )
}

export default ChatList;