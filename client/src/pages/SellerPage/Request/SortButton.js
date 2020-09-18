import React,{useState} from 'react';
import { Popover, Button, List, ListItem, ListItemText } from '@material-ui/core';
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';

const SortButton = ({sortValue,setSortValue}) => {

    const [anchorEl, setAnchorEl] = useState(null);

    const [buttonText, setButtonText] = useState('');

    const open = Boolean(anchorEl);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const onClickSort = (value,text) => {
        setSortValue(value);
        setButtonText(text);
        setAnchorEl(null);
    }


    return (
        <>
        <Button style={{ float: 'right' }} onClick={handleClick}>
            {buttonText ? buttonText : '정렬 기준'}<ArrowDropDownIcon />
        </Button>
        <Popover
            open={open}
            anchorEl={anchorEl}
            onClose={handleClose}
            anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'right',
            }}
            transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
            }}
        >
            <List>
                <ListItem button onClick={()=>{onClickSort('deadline',"마감임박순")}}>
                    <ListItemText primary="마감임박순" />
                </ListItem>
                <ListItem button onClick={()=>{onClickSort('uploadAt',"요청일순")}}>
                    <ListItemText primary="요청일순" />
                </ListItem>
            </List>
        </Popover>
        </>
    )
}

export default SortButton;