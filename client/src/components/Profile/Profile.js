import React, { useState } from 'react'
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Avatar from '@material-ui/core/Avatar';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import ListItemText from '@material-ui/core/ListItemText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Dialog from '@material-ui/core/Dialog';
import { blue } from '@material-ui/core/colors';
import { Grid, DialogContent, DialogActions, GridList, GridListTile, TextField, Typography } from '@material-ui/core';
import ProfileCarousel from './ProfileCarousel';
import Rating from '@material-ui/lab/Rating';
import ProfileEdit from './ProfileEdit';
import PersonIcon from '@material-ui/icons/Person';
import { useSelector } from 'react-redux';

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'space-around',
        overflow: 'hidden',
        backgroundColor: theme.palette.background.paper,
    },
    gridList: {
        flexWrap: 'nowrap',
        transform: 'translateZ(0)',
    },
    title: {
        color: theme.palette.primary.light,
    },
    titleBar: {
        background:
            'linear-gradient(to top, rgba(0,0,0,0.7) 0%, rgba(0,0,0,0.3) 70%, rgba(0,0,0,0) 100%)',
    },
    avatar: {
        backgroundColor: blue[100],
        color: blue[700],
    },
    buttonStyle: {
        width: '100%'
    }
}));

const Profile = ({ profile, onClose, open, user_id }) => {

    const classes = useStyles();
    const userName = useSelector(state => state.userAction.userName);
    const [imageOpen, setImageOpen] = useState(false);
    const [editOpen, setEditOpen] = useState(false);
    const [image, setImage] = useState('');

    const handleClose = () => {
        onClose();
    };

    const onEditClose = () => {
        setEditOpen(false);
    }

    const onClickImageOpen = (src) => {
        setImage(src);
        setImageOpen(true);
    }


    const reviewList = profile.reviews.map((obj, index) => {

        return (
            <ListItem key={index}>
                <ListItemAvatar>
                    <Avatar className={classes.avatar}>
                        <PersonIcon />
                    </Avatar>
                </ListItemAvatar>
                <ListItemText>
                    {obj.name}님의 평가
                    <Rating name="half-rating-read" value={obj.rating} precision={0.5} readOnly /><br />
                    {obj.text}
                </ListItemText>
            </ListItem>
        )
    })

    

    return (
        <Dialog fullWidth={true} maxWidth="sm" onClose={handleClose} aria-labelledby="form-dialog-title" open={open}>
            <DialogTitle id="simple-dialog-title">
                <Grid container>
                    <Grid item xs={1}>
                    {
                            profile.profileImage !== null ? <Avatar className={classes.avatar} src={profile.profileImage} /> :null
                        }
                        
                    </Grid>
                    <Grid item xs={11}>
                        <Typography variant="h6" color="textPrimary" style={{ display: "inline-block", marginTop: "5px", marginLeft: "5px" }}>
                            {profile.userName}님의 프로필
                        </Typography>
                    </Grid>
                </Grid>
            </DialogTitle>
            <DialogContent>
                <div className={classes.root}>

                    {
                    
                    profile.exampleImages.length !== 0
                        ?
                        profile.exampleImages.length === 1
                            ?
                            <img src={profile.exampleImages[0].url} alt="비어있음" onClick={() => { onClickImageOpen(profile.exampleImages[0].url) }} />
                            :
                            <GridList className={classes.gridList} cols={2.5}>
                                {profile.exampleImages.map((obj, index) => {
                                    console.log(obj);
                                    return (
                                        <GridListTile key={index} onClick={() => { onClickImageOpen(obj) }}>
                                            <img src={obj.url} alt="비어있음" />
                                        </GridListTile>
                                    )
                                })}
                            </GridList>
                        :
                        <textPrimary style={{ textAlign: 'center', marginTop: '60px' }}>No Image</textPrimary>
                    }
                </div>
               
                
                <br />
                
                
                <Typography variant="h6" color="textPrimary" gutterBottom>소개 및 설명</Typography>
                {profile.portfolio !== ''
                    ?
                    <TextField
                        multiline
                        variant="outlined"
                        InputProps={{ readOnly: true }}
                        value={profile.portfolio}
                        style={{ width: "100%" }}
                    />
                    :
                    <TextField
                        multiline
                        variant="outlined"
                        InputProps={{ readOnly: true }}
                        value="등록된 설명이 없습니다."
                        style={{ width: "100%" }}
                    />
                
                }

                <br /><br />
                <Typography variant="h6" color="textPrimary" gutterBottom style={{ display: 'inline-block' }}>이용자 리뷰</Typography>
                {reviewList.length !== 0
                    ?
                    <>
                        <Rating name="half-rating-read" value={1} precision={0.5} readOnly />
                        <List>
                            {reviewList}
                        </List>
                    </>
                    :
                    <Typography variant="h6" color="textPrimary" gutterBottom style={{ textAlign: 'center' }}>등록된 리뷰가 없습니다.</Typography>
                }
                <ProfileCarousel open={imageOpen} setOpen={setImageOpen} src={image} />
                <ProfileEdit profile={profile} onClose={onEditClose} open={editOpen} user_id={user_id} profileClose={handleClose} />
            </DialogContent>
            {userName === profile.userName
                ?
                <DialogActions>
                    <Button onClick={() => { setEditOpen(true) }} variant="contained" className={classes.buttonStyle}>프로필 설정</Button>
                </DialogActions>
                :
                <>
                </>
            }
        </Dialog>
    )
}

export default Profile
