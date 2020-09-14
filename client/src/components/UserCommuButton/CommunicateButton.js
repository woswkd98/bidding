import React, { useState } from 'react'
import { Button } from '@material-ui/core';
import PopOver from './PopOver';
import UserChat from '../chat';
import PhoneNumber from './PhoneNumber';

const CommunicateButton = ({request_id,seller_id,phone,avatarSrc}) => {

    const [anchorEl, setAnchorEl] = useState(null);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const open = Boolean(anchorEl);

    const [chatOpen,setChatOpen] = useState(false);

    const handleChatClose = () => {
        setChatOpen(false);
    }

    const [numberOpen, setNumberOpen] = useState(false);

    const handleNumberClose = () => {
        setNumberOpen(false);
    }

    return (
        <>
        <Button onClick={handleClick} style={{ width: '100%'}} variant="outlined">
            상담하기
        </Button>
        <PopOver setNumberOpen={setNumberOpen} setChatOpen={setChatOpen} open={open} anchorEl={anchorEl} handleClose={handleClose}/>
        {chatOpen && <UserChat open={chatOpen} onClose={handleChatClose} request={request_id} seller={seller_id} avatarSrc={avatarSrc} /> }
        <PhoneNumber open={numberOpen} onClose={handleNumberClose} phone={phone}/>
        </>
    )
}

export default CommunicateButton;
