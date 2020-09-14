import React from 'react';
import Popover from '@material-ui/core/Popover';
import { List, ListItem, ListItemText } from '@material-ui/core';


const PopOver = ({ setNumberOpen, setChatOpen, open, anchorEl, handleClose }) => {


  const chatStart = () => {
    setChatOpen(true);
    handleClose();
  }

  const numberShow = () => {
    setNumberOpen(true);
    handleClose();
  }

  return (
    <div>
      <Popover
        open={open}
        anchorEl={anchorEl}
        onClose={handleClose}
        anchorOrigin={{
          vertical: 'center',
          horizontal: 'right',
        }}
        transformOrigin={{
          vertical: 'center',
          horizontal: 'left',
        }}
      >
        <List>
          <ListItem
            button
            onClick={chatStart}>
            <ListItemText primary="1:1 채팅" />
          </ListItem>
          <ListItem
            button
            onClick={numberShow}>
            <ListItemText primary="전화,문자메세지" />
          </ListItem>
        </List>
      </Popover>
    </div>
  )
}

export default PopOver
