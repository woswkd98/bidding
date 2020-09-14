import React,{useState} from 'react';
import { Popover, Button, List, ListItem, ListItemText } from '@material-ui/core';
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';

const SortButton = () => {

    const [anchorEl, setAnchorEl] = useState(null);

    const [sortValue,setSortValue] = useState('정렬기준');

    const open = Boolean(anchorEl);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const onClickSort = (value) => {
        setSortValue(value);
        setAnchorEl(null);
    }

    return (
        <>
        <Button style={{ float: 'right' }} onClick={handleClick}>
            {sortValue}<ArrowDropDownIcon />
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
                <ListItem button onClick={()=>{onClickSort('마감임박순')}}>
                    <ListItemText primary="마감임박순" />
                </ListItem>
                <ListItem button onClick={()=>{onClickSort('요청일순')}}>
                    <ListItemText primary="요청일순" />
                </ListItem>
            </List>
        </Popover>
        </>
    )
}

export default SortButton;