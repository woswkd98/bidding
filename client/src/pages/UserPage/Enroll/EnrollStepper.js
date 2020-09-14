import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepLabel from '@material-ui/core/StepLabel';
import Button from '@material-ui/core/Button';
import EnrollList from './EnrollList';
import EnrollDialog from './EnrollDialog';
import EnrollAlert from './EnrollAlert';
import { useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';
import ConfirmDialog from './ConfirmDialog';
import Axios from 'axios';

const useStyles = makeStyles((theme) => ({
    root: {
        width: '100%',
        textAlign: "center",
        backgroundColor: 'transparent',
    },
    backButton: {
        marginRight: theme.spacing(1),
    },
    instructions: {
        marginTop: theme.spacing(1),
        marginBottom: theme.spacing(1),
    },
}));

function getSteps() {
    return ['카테고리 설정', '마감 시간 설정', '희망 작업 완료일 설정', '태그 입력', '상세 설명 입력', '작성완료'];
}

const EnrollStepper = () => {
    const history = useHistory();
    const classes = useStyles();
    const [activeStep, setActiveStep] = useState(0);
    const steps = getSteps();

    const [loading, setLoading] = useState(false);

    const handleNext = () => {
        setActiveStep((prevActiveStep) => prevActiveStep + 1);
    };

    const handleBack = () => {
        setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };

    const handleReset = () => {
        history.push('/user/mypage');
    };

    const [enrollData, setEnrollData] = useState({
        category: '',
        deadline: '',
        hopeDate: '',
        detail: '',
        tags: [],
    })

    const { category, deadline, detail, hopeDate, tags } = enrollData;

    const handleEnrollData = (name, value) => {
        setEnrollData({
            ...enrollData,
            [name]: value
        })
    }

    const [open, setOpen] = useState(false);
    const [alertOpen, setAlertOpen] = useState(false);
    const [confirmOpen, setConfirmOpen] = useState(false);

    const onClickCheck = () => {
        switch (activeStep) {
            case 0:
                if (category === '') {
                    setAlertOpen(true);
                } else {
                    handleNext();
                }
                break;
            case 1:
                if (deadline === '') {
                    setAlertOpen(true);
                } else {
                    handleNext();
                }
                break;
            case 2:
                if (hopeDate === '') {
                    setAlertOpen(true);
                } else {
                    handleNext();
                }
                break;
            case 3:
                if (tags.length === 0) {
                    setConfirmOpen(true);
                } else {
                    handleNext();
                }
                break;
            case 4:
                if (detail === '') {
                    setAlertOpen(true);
                } else {
                    handleNext();
                }
                break;
            case 5:
                setOpen(true);
                break;
            default:
                break;

        }
    }

    /*
    private Long userId;
    private String category;
    private String detail;
    private Long deadline;
    private String hopeDate;
    private String state;
    private List<String> tags = new ArrayList<String>();    
    */

    const user_id = useSelector(state => state.userAction.user_id);

    const sendRequest = () => {
    
        Axios.put('/requests', {
            userId : user_id,
            ...enrollData,
            state : "요청 진행중"
        })
        .then(res => {
            alert(res.data);
            setLoading(false);
            setOpen(false);
            handleNext();
        })
    }

    const onClickSendRequest = () => {
        setLoading(true);
        sendRequest();
    }


    return (
        <div className={classes.root}>
            <Stepper className={classes.root} activeStep={activeStep} alternativeLabel>
                {steps.map((label) => (
                    <Step key={label}>
                        <StepLabel>{label}</StepLabel>
                    </Step>
                ))}
            </Stepper>
            <div>
                {activeStep === steps.length ? (
                    <div>
                        <span className={classes.instructions}>
                            <h1>작성완료</h1>
                            <p>
                                전문가들의 입찰을 기다려주세요!
                            </p>
                        </span>
                        <Button variant="contained" onClick={handleReset}>돌아가기</Button>
                    </div>
                ) : (
                        <div>
                            <span className={classes.instructions}>
                                <EnrollList stepIndex={activeStep} handleEnrollData={handleEnrollData} />
                            </span>
                            <div style={{ marginTop: '50px' }}>
                                <Button
                                    disabled={activeStep === 0}
                                    onClick={handleBack}
                                    className={classes.backButton}
                                    variant="contained"
                                >
                                    뒤로
                                </Button>
                                {activeStep === steps.length - 1
                                    ?
                                    <Button variant="contained" color="primary" onClick={onClickCheck}>
                                        완료
                                </Button>
                                    :
                                    <Button variant="contained" color="primary" onClick={onClickCheck}>
                                        다음
                                </Button>}
                            </div>
                        </div>
                    )}
            </div>
            <EnrollDialog open={open} onClickSendRequest={onClickSendRequest} setOpen={setOpen} loading={loading} />
            <EnrollAlert open={alertOpen} setOpen={setAlertOpen} />
            <ConfirmDialog open={confirmOpen} setOpen={setConfirmOpen} handleNext={handleNext} />
        </div>
    );
}


export default EnrollStepper;
