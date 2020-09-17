import React, { useState, useEffect } from 'react'
import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
import Avatar from '@material-ui/core/Avatar';
import DialogTitle from '@material-ui/core/DialogTitle';
import Dialog from '@material-ui/core/Dialog';
import { Grid, DialogContent,  GridList, GridListTile, TextField, IconButton, GridListTileBar } from '@material-ui/core';
import PersonIcon from '@material-ui/icons/Person';
import { blue } from '@material-ui/core/colors';
import Backdrop from '@material-ui/core/Backdrop';
import CircularProgress from '@material-ui/core/CircularProgress';
import ProfileCarousel from '../../components/Profile/ProfileCarousel';
import DeleteIcon from '@material-ui/icons/Delete';
import Axios from 'axios';
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepLabel from '@material-ui/core/StepLabel';
import StepContent from '@material-ui/core/StepContent';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';

const useStyles = makeStyles((theme) => ({
    button: {
        marginTop: theme.spacing(1),
        marginRight: theme.spacing(1),
    },
    actionsContainer: {
        marginBottom: theme.spacing(2),
    },
    resetContainer: {
        padding: theme.spacing(3),
    },
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'space-around',
        overflow: 'hidden',
        backgroundColor: theme.palette.background.paper,
    },
    gridList: {
        flexWrap: 'nowrap',
        // Promote the list into his own layer on Chrome. This cost memory but helps keeping high FPS.
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

function getSteps() {
    return ['휴대전화 인증', '프로필 사진 등록', '프로젝트 사진 등록', '소개 및 설명'];
}


const ExpertRegister = ({ onClose, open, user_id }) => {

    const [activeStep, setActiveStep] = useState(0);
    const steps = getSteps();

    const classes = useStyles();

    const [imageOpen, setImageOpen] = useState(false);

    const [image, setImage] = useState('');

    const [loading, setLoading] = useState(false);

    const onClickImageOpen = (src) => {
        setImage(src);
        setImageOpen(true);
    }

    const [profileImage, setProfileImage] = useState('');

    const [profilePreview, setProfilePreview] = useState('');

    const [exampleImages, setExampleImages] = useState([]);

    const [examplePreview, setExamplePreview] = useState([]);

    const [exPreview, setExPreview] = useState([]);

    const [text, setText] = useState('');

    const [phone, setPhone] = useState('');

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
        formData.append('userId', user_id);
        
        formData.append('phone', phone);
        formData.append('portfolio', text);
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
                console.log(res.data);
                console.log("sellers");
                
                localStorage.setItem("is_seller", res.data.seller_id);
                setLoading(false);
             
                onClose();
                window.location.reload();
            })
            .catch(err => {
                console.log('rest 에러!',err);
            })
    }

    const handleExpertRegister = () => {
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
        // setExampleImages(files);
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


    const handleNext = () => {
        setActiveStep((prevActiveStep) => prevActiveStep + 1);
    };

    const handleBack = () => {
        setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };

    const handleReset = () => {
        setActiveStep(0);
    };


    function getStepContent(step) {
        switch (step) {
            case 0:
                return (
                    <>
                        <small>구매자와 연락을 할 수 있도록 휴대전화 번호를 적어주세요.</small><br /><br />
                        <input className={classes.inputStyle} name="phone1" onChange={onChangePhone} />
                        -
                        <input className={classes.inputStyle} name="phone2" onChange={onChangePhone} />
                        -
                        <input className={classes.inputStyle} name="phone3" onChange={onChangePhone} />
                        <br /><br />
                    </>
                )
            case 1:
                return (
                    <>
                        <small>나를 나타내는 사진을 올려주세요.</small>
                        <br /><br />
                        <label htmlFor='imageupload' style={{ display: 'inline-block' }}>
                            {profilePreview === ''
                                ?
                                <Avatar className={classes.avatarBig}>
                                    <PersonIcon style={{ fontSize: 80 }} />
                                </Avatar>
                                :
                                <Avatar className={classes.avatarBig} src={profilePreview} />
                            }
                            <input type='file' id='imageupload' onChange={onChnageProfileImage} accept="image/*" style={{ display: 'none' }} />
                        </label>
                        <br /><br />
                    </>
                )
            case 2:
                return (
                    <>
                        <small>자신이 완성한 프로젝트 사진을 올려주세요. 구매자의 선택에 도움이 됩니다.</small><br />
                        <input type="file" onChange={onChangeImage} multiple accept="image/*" />
                        <div className={classes.root}>
                            <GridList className={classes.gridList} cols={2.5}>
                                {showExPreview}
                                {showExamplePreview}
                            </GridList>
                        </div>
                        <br />
                    </>
                )
            case 3:
                return (
                    <>
                        <small>자기 소개나 사진에 대한 설명을 적어주세요.</small><br />
                        <TextField
                            multiline
                            defaultValue={text}
                            variant="outlined"
                            onChange={onChangeText}
                            style={{ width: "100%" }}
                        />
                    </>
                )
            default:
                return 'Unknown step';
        }
    }


    return (
        <>
            {loading
                ?
                <Backdrop className={classes.backdrop} open={loading}>
                    <CircularProgress color="inherit" />
                </Backdrop>
                :
                <Dialog fullWidth={true} maxWidth="sm" onClose={onClose} aria-labelledby="form-dialog-title" open={open}>
                    <DialogTitle style={{ padding: '0px' }}>
                        <Grid container>
                            <Grid item xs={6}>
                                <div style={{ marginLeft: '14px', marginTop: '10px' }}>전문가 등록하기</div>
                            </Grid>
                            <Grid item xs={6}>
                                <IconButton onClick={onClose} style={{ float: 'right' }}>
                                    <CloseIcon />
                                </IconButton>
                            </Grid>
                        </Grid>
                    </DialogTitle>
                    <DialogContent>
                        <Stepper activeStep={activeStep} orientation="vertical">
                            {steps.map((label, index) => (
                                <Step key={label}>
                                    <StepLabel>{label}</StepLabel>
                                    <StepContent>
                                        <Typography>{getStepContent(index)}</Typography>
                                        <div className={classes.actionsContainer}>
                                            <div>
                                                <Button
                                                    disabled={activeStep === 0}
                                                    onClick={handleBack}
                                                    className={classes.button}
                                                >
                                                    뒤로
                                                </Button>
                                                {activeStep === steps.length - 1
                                                    ?
                                                    <Button
                                                        variant="contained"
                                                        color="primary"
                                                        onClick={handleNext}
                                                        className={classes.button}
                                                    >
                                                        완료
                                                </Button>
                                                    :
                                                <Button
                                                        variant="contained"
                                                        color="primary"
                                                        onClick={handleNext}
                                                        className={classes.button}
                                                    >
                                                        다음
                                                </Button>
                                                }

                                            </div>
                                        </div>
                                    </StepContent>
                                </Step>
                            ))}
                        </Stepper>
                    </DialogContent>
                    {activeStep === steps.length && (
                        <Paper square elevation={0} className={classes.resetContainer}>
                            <Button variant="outlined" onClick={handleReset} className={classes.button}>
                                처음으로
                            </Button>
                            <Button variant="contained" color="primary" onClick={handleExpertRegister} className={classes.button}>
                                등록하기
                            </Button>
                        </Paper>
                    )}
                    <ProfileCarousel open={imageOpen} setOpen={setImageOpen} src={image} />
                </Dialog>
            }
        </>
    )
}


export default ExpertRegister;