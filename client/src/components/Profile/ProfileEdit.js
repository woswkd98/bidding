import React, { useState, useEffect } from 'react'
import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
import Avatar from '@material-ui/core/Avatar';
import DialogTitle from '@material-ui/core/DialogTitle';
import Dialog from '@material-ui/core/Dialog';
import { Grid, DialogContent, DialogActions, GridList, GridListTile, TextField, IconButton, GridListTileBar } from '@material-ui/core';
import { blue } from '@material-ui/core/colors';
import Backdrop from '@material-ui/core/Backdrop';
import CircularProgress from '@material-ui/core/CircularProgress';
import ProfileCarousel from './ProfileCarousel';
import DeleteIcon from '@material-ui/icons/Delete';
import Axios from 'axios';

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
    avatar: {
        backgroundColor: blue[100],
        color: blue[700],
    },
    buttonStyle: {
        width: '100%'
    },
    backdrop: {
        zIndex: theme.zIndex.drawer + 1,
        color: '#fff',
    },
    avatarBig: {
        backgroundColor: blue[100],
        color: blue[700],
        width: '150px',
        height: '150px',
    },
    titleBar: {
        background:
            'linear-gradient(to bottom, rgba(0,0,0,0.7) 0%, ' +
            'rgba(0,0,0,0.3) 70%, rgba(0,0,0,0) 100%)',
    },
    icon: {
        color: 'white',
    },
    inputStyle: {
        width: '50px',
        height: '20px',
    }
}));

const ProfileEdit = ({ profile, onClose, open, user_id, profileClose }) => {

    const classes = useStyles();

    const [imageOpen, setImageOpen] = useState(false);

    const [image, setImage] = useState('');

    const [loading, setLoading] = useState(false);

    const onClickImageOpen = (src) => {
        setImage(src);
        setImageOpen(true);
    }

    const [profileImage, setProfileImage] = useState('');

    const [profilePreview, setProfilePreview] = useState(profile.profileImage);

    const [exampleImages, setExampleImages] = useState([]);

    const [examplePreview, setExamplePreview] = useState([]);

    const [exPreview, setExPreview] = useState(profile.exampleImages);

    const [text, setText] = useState(profile.text);

    const [phone, setPhone] = useState(profile.phone)

    const [phoneInput, setPhoneInput] = useState({
        phone1: '',
        phone2: '',
        phone3: '',
    });

    const onChangeText = (e) => {
        setText(e.target.value);
    }

    const onChangePhone = (e) => {
        setPhoneInput({
            ...phoneInput,
            [e.target.name]: e.target.value,
        });
    }

    const onClickAxios = async () => {
        let formData = new FormData(); //객체
        const config = { //config 객체
            header: { "content-type": "multipart/form-data" },
        };
        formData.append('user', user_id);
        formData.append('phone', phone);
        formData.append('text', text);
        if (profileImage) {
            formData.append("profileImage", profileImage);
        } else {
            formData.append("profilePreview", profilePreview);
        }

        if (exPreview) {
            exPreview.map((obj) => {
                return formData.append("exPreview", obj);
            })
        }
        if (exampleImages) {
            exampleImages.map((obj) => {
                return formData.append("exampleImages", obj);
            })
        }
        Axios.post('/sellers', formData, config)
            .then(res => {
                console.log(res);
                setLoading(false);
                onClose();
                profileClose();
            })
            .catch(err => {
                console.log(err);
            })
    }

    const handleSaveProfile = () => {
        setLoading(true);
        onClickAxios();
    }

    const onChnageProfileImage = (e) => {
        setProfileImage(e.target.files[0]);
        let files = e.target.files
        let reader = new FileReader();
        reader.onload = (e) => {
            setProfilePreview(e.target.result)
        }
        if (files.length !== 0) {
            console.log(files);
            reader.readAsDataURL(files[0])
        }
    }

    const imageRead = (obj) => {
        let reader = new FileReader();
        reader.onload = (e) => {
            setExamplePreview((examplePreview) => {
                return [
                    ...examplePreview,
                    {
                        src: e.target.result,
                        obj: obj,
                    }
                ]
            })
        }
        reader.readAsDataURL(obj);
    }

    const onChangeImage = (e) => {
        const files = e.target.files
        const filesLength = files.length;
        for (let i = 0; i < filesLength; i++) {
            setExampleImages((exampleImages) => {
                return [...exampleImages, files[i]]
            });
            imageRead(files[i]);
        }
        console.log(exampleImages);
    }

    const onClickDeleteImage = (obj) => {
        setExampleImages(exampleImages.filter((x) => {
            return obj !== x;
        }))
        setExamplePreview(examplePreview.filter((x) => {
            return obj !== x.obj;
        }))
    }

    const onClicDeleteExPreview = (ex) => {
        setExPreview(exPreview.filter((x) => {
            return ex !== x;
        }))
        console.log(exPreview);
    }

    const showExamplePreview = examplePreview.map((obj, index) => {
        return (
            <GridListTile key={index} >
                <img src={obj.src} alt="없음" onClick={() => { onClickImageOpen(obj.src) }} />
                <GridListTileBar
                    titlePosition="bottom"
                    actionIcon={
                        <IconButton className={classes.icon} onClick={() => { onClickDeleteImage(obj.obj) }}>
                            <DeleteIcon />
                        </IconButton>
                    }
                    actionPosition="right"
                    className={classes.titleBar}
                />
            </GridListTile>
        )
    })

    const showExPreview = exPreview.map((obj, index) => {
        return (
            <GridListTile key={index} >
                <img src={obj} alt="없음" onClick={() => { onClickImageOpen(obj) }} />
                <GridListTileBar
                    titlePosition="bottom"
                    actionIcon={
                        <IconButton className={classes.icon} onClick={() => { onClicDeleteExPreview(obj) }}>
                            <DeleteIcon />
                        </IconButton>
                    }
                    actionPosition="right"
                    className={classes.titleBar}
                />
            </GridListTile>
        )
    })


    useEffect(() => {
        if (phoneInput.phone1 || phoneInput.phone2 || phoneInput.phone3) {
            setPhone(`${phoneInput.phone1}-${phoneInput.phone2}-${phoneInput.phone3}`);
        }
    }, [phoneInput])


    return (
        <>
            {loading
                ?
                <Backdrop className={classes.backdrop} open={loading}>
                    <CircularProgress color="inherit" />
                </Backdrop>
                :
                <Dialog fullWidth={true} maxWidth="sm" onClose={onClose} aria-labelledby="form-dialog-title" open={open}>
                    <DialogTitle id="simple-dialog-title">
                        <Grid container>
                            <Grid item xs={1}>
                                <Avatar className={classes.avatar} src={profilePreview} />
                            </Grid>
                            <Grid item xs={11}>
                                <div style={{ display: "inline-block", marginTop: "5px", marginLeft: "5px" }}>내 프로필 설정</div>
                            </Grid>
                        </Grid>
                    </DialogTitle>
                    <DialogContent>
                        <h3 style={{ margin: '0px' }}>프로필 사진</h3>
                        <small>나를 나타내는 사진을 올려주세요.</small>
                        <br /><br />
                        <label htmlFor='imageupload' style={{ display: 'inline-block' }}>
                            <Avatar className={classes.avatarBig} src={profilePreview} />
                            <input type='file' id='imageupload' onChange={onChnageProfileImage} accept="image/*" style={{ display: 'none' }} />
                        </label>
                        <br /><br />
                        <h3 style={{ margin: '0px' }}>프로젝트 사진</h3>
                        <small>자신이 완성한 프로젝트 사진을 올려주세요. 구매자의 선택에 도움이 됩니다.</small><br />
                        <input type="file" onChange={onChangeImage} multiple accept="image/*" />
                        <div className={classes.root}>
                            <GridList className={classes.gridList} cols={2.5}>
                                {showExPreview}
                                {showExamplePreview}
                            </GridList>
                        </div>
                        <br />
                        <h3 style={{ marginBottom: '0px' }}>상세 설명</h3>
                        <small>자기 소개나 사진에 대한 설명을 적어주세요.</small><br /><br />
                        <TextField
                            multiline
                            defaultValue={text}
                            variant="outlined"
                            onChange={onChangeText}
                            style={{ width: "100%" }}
                        />
                        <h3 style={{ marginBottom: '0px' }}>휴대전화</h3>
                        <small>구매자와 연락을 할 수 있도록 휴대전화 번호를 적어주세요.</small><br /><br />
                        <input className={classes.inputStyle} name="phone1" onChange={onChangePhone} />
                        -
                        <input className={classes.inputStyle} name="phone2" onChange={onChangePhone} />
                        -
                        <input className={classes.inputStyle} name="phone3" onChange={onChangePhone} />
                        <br /><br />
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={handleSaveProfile} variant="contained" className={classes.buttonStyle}>저장</Button>
                        <Button onClick={onClose} variant="contained" className={classes.buttonStyle}>취소</Button>
                    </DialogActions>
                    <ProfileCarousel open={imageOpen} setOpen={setImageOpen} src={image} />
                </Dialog>
            }
        </>
    )
}

export default ProfileEdit
