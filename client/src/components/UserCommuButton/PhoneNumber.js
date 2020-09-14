import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import ListItemText from '@material-ui/core/ListItemText';
import Dialog from '@material-ui/core/Dialog';
import SmartphoneIcon from '@material-ui/icons/Smartphone';

const PhoneNumber = ({open,onClose,phone}) => {
    return (
        <Dialog open={open} onClose={onClose}>
            <List>
                <ListItem>
                    <ListItemAvatar>
                        <Avatar>
                            <SmartphoneIcon/>
                        </Avatar>
                    </ListItemAvatar>
                    <ListItemText primary={`전화번호 : ${phone}`}/>
                </ListItem>
            </List>
        </Dialog>
    )
}

export default PhoneNumber;
