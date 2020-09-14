import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import BuildIcon from '@material-ui/icons/Build';
import QueryBuilderIcon from '@material-ui/icons/QueryBuilder';
import { TextField, InputAdornment, OutlinedInput, Paper, Typography, Tooltip, Card } from '@material-ui/core';
import Chip from '@material-ui/core/Chip';
import LabelIcon from '@material-ui/icons/Label';
import AddCircleOutlineIcon from '@material-ui/icons/AddCircleOutline';
import CalendarTodayIcon from '@material-ui/icons/CalendarToday';
import Checkbox from '@material-ui/core/Checkbox';
import Slide from '@material-ui/core/Slide';

const useStyles = makeStyles((theme) => ({
    rootList: {
        marginTop: theme.spacing(5),
        width: '100%',
        maxWidth: 500,
        margin: 'auto',
    },
    backgroundStyle: {
        backgroundColor: 'white',
    }
}));

const categories = [
    {
        id: 0,
        value: '앱 개발'
    },
    {
        id: 1,
        value: '웹 개발'
    },
    {
        id: 2,
        value: '소프트웨어 개발'
    },
    {
        id: 3,
        value: '유지보수'
    }
];

const deadlines = [
    {
        id: 0,
        value: '1시간'
    },
    {
        id: 1,
        value: '3시간'
    },
    {
        id: 2,
        value: '6시간'
    },
    {
        id: 3,
        value: '12시간'
    },
    {
        id: 4,
        value: '18시간'
    },
    {
        id: 5,
        value: '24시간'
    }
];

function EnrollList({ stepIndex, handleEnrollData }) {

    const classes = useStyles();

    const [categoryInput, setCategoryInput] = useState({
        category: '',
        categoryIndex: null,
    });

    const { category, categoryIndex } = categoryInput;

    const [deadlineInput, setdeadlineInput] = useState({
        deadline: '',
        deadlineIndex: null,
    });

    const { deadline, deadlineIndex } = deadlineInput;

    const [hopeDateInput, setHopeDateInput] = useState({
        hopeDate: '',
        hopeDateIndex: null,
    });

    const { hopeDate, hopeDateIndex } = hopeDateInput;

    const onChangeTime = (index, value) => {
        const now = new Date()
        let str = value.slice(0, -2);
        let end = now.setHours(now.getHours() + Number(str));
        setdeadlineInput({
            deadline: end,
            deadlineIndex: index,
        });
        handleEnrollData('deadline', end)
    }

    const onChangeCategory = (index, value) => {
        setCategoryInput({
            category: value,
            categoryIndex: index
        });
        handleEnrollData('category', value)
    };

    const onChangeHopeDate = (index, value) => {
        setHopeDateInput({
            hopeDate: value,
            hopeDateIndex: index
        });
        handleEnrollData('hopeDate', value)
    };


    const [tag, setTag] = useState('');

    const [tagList, setTagList] = useState([]);

    const onChangeTag = (e) => {
        setTag(e.target.value);
    }

    const onSubmitForm = (e) => {
        e.preventDefault();
        if (tag === '') {
            return;
        } else {
            setTagList([
                ...tagList,
                tag
            ])
            handleEnrollData('tags', [
                ...tagList,
                tag
            ])
            setTag('');
        }
    }

    const onClickRemove = (index) => {
        const list = tagList.filter((obj, x) => {
            return x !== index;
        })
        setTagList(list)
    }

    const showTagList = tagList.map((obj, index) => {
        return <span key={index}><Chip label={obj} variant="outlined" size="small" onDelete={() => { onClickRemove(index) }} />&nbsp;</span>
    })

    const [detail, setDetail] = useState('');

    const onChangeDetail = (e) => {
        setDetail(e.target.value);
        handleEnrollData('detail', e.target.value);
    }


    const printList = (arr, index, fnc) => {
        return (
            <Paper elevation={3}>
                <List className={classes.backgroundStyle} component="nav" aria-label="main mailbox folders">
                    {arr.map((obj) => {
                        return (
                            <div key={obj.id}>
                                <Slide timeout={100 * obj.id} direction="left" in={true} mountOnEnter unmountOnExit>
                                    <ListItem button selected={index === obj.id} onClick={() => fnc(obj.id, obj.value)}>
                                        <ListItemIcon>
                                            <Checkbox
                                                checked={index === obj.id}
                                            />
                                        </ListItemIcon>
                                        <ListItemText primary={obj.value} />
                                    </ListItem>
                                </Slide>
                            </div>
                        )
                    })}
                </List>
            </Paper>
        )
    }

    switch (stepIndex) {
        case 0:
            return (
                <div className={classes.rootList}>
                    <Typography variant="h4"><BuildIcon /> 무엇을 만들고 싶으신가요?</Typography>
                    <br />
                    {category}
                    <br /><br />
                    {printList(categories, categoryIndex, onChangeCategory)}
                </div>
            )
        case 1:
            return (
                <div className={classes.rootList}>
                    <Typography variant="h4" gutterBottom><QueryBuilderIcon /> 경매 시간을 설정해주세요.</Typography>
                    <Typography paragraph>
                        설정하신 시간 동안 전문가들이 입찰할 것입니다!
                    </Typography>
                    {deadline === '' ? <></> : <p>경매 마감 일시: {new Date(deadline).toLocaleString()}</p>}
                    {printList(deadlines, deadlineIndex, onChangeTime)}
                </div>
            )
        case 2:
            return (
                <div className={classes.rootList}>
                    <Typography variant="h4" gutterBottom><CalendarTodayIcon /> 희망 작업 완료일을 선택해주세요.</Typography>
                    <Typography paragraph>{hopeDate}</Typography>
                    <Typography paragraph>
                        전문가와 협의도 가능합니다!
                    </Typography>
                    <Paper elevation={3}>
                        <List className={classes.backgroundStyle} component="nav" aria-label="main mailbox folders">
                            <Slide timeout={200} direction="left" in={true} mountOnEnter unmountOnExit>
                                <ListItem button selected={hopeDateIndex === 0} onClick={() => onChangeHopeDate(0, '전문가와 협의')}>
                                    <ListItemIcon>
                                        <Checkbox
                                            checked={hopeDateIndex === 0}
                                        />
                                    </ListItemIcon>
                                    <ListItemText primary='전문가와 협의' />
                                </ListItem>
                            </Slide>
                            <Slide timeout={300} direction="left" in={true} mountOnEnter unmountOnExit>
                                <ListItem button selected={hopeDateIndex === 1} onClick={() => onChangeHopeDate(1)}>
                                    <ListItemIcon>
                                        <Checkbox
                                            checked={hopeDateIndex === 1}
                                        />
                                    </ListItemIcon>
                                    <ListItemText primary='날짜 선택' />
                                    {hopeDateIndex === 1
                                        ? <TextField
                                            id="date"
                                            label="date"
                                            type="date"
                                            defaultValue={new Date().toDateString()}
                                            className={classes.textField}
                                            InputLabelProps={{
                                                shrink: true,
                                            }}
                                            onChange={(e) => {
                                                if (new Date(e.target.value) < new Date()) {
                                                    alert('이미 지난 날짜입니다. 다시 선택해 주세요')
                                                } else {
                                                    onChangeHopeDate(1, e.target.value)
                                                }
                                            }}
                                        />
                                        : <></>
                                    }
                                </ListItem>
                            </Slide>
                        </List>
                    </Paper>
                </div>
            )
        case 3:
            return (
                <div className={classes.rootList}>
                    <Typography variant="h4" gutterBottom><LabelIcon /> 아이디어가 있으신가요?</Typography>
                    <Typography paragraph>
                        아이디어의 키워드를 태그형식으로 입력해주세요!<br />
                        잘 모르겠다면 입력하지 않아도 괜찮습니다.
                    </Typography>
                    예시 : <Chip label="쇼핑몰" variant="outlined" size="small" />&nbsp;
                    <Chip label="어플리케이션" variant="outlined" size="small" />&nbsp;
                    <Chip label="안드로이드" variant="outlined" size="small" />&nbsp;
                    <Chip label="IOS" variant="outlined" size="small" />
                    <br />
                    <br />
                    <Slide direction="left" in={true} mountOnEnter unmountOnExit>
                        <form onSubmit={onSubmitForm}>
                            <OutlinedInput
                                fullWidth
                                style={{ backgroundColor: 'white' }}
                                size="small"
                                placeholder="한 단어씩 입력해주세요!"
                                value={tag}
                                autoFocus
                                endAdornment={<InputAdornment position="end">ENTER</InputAdornment>}
                                onChange={onChangeTag} />
                            <br /><br />
                        </form>
                    </Slide>
                    {showTagList}<br /><br />
                </div>
            )
        case 4:
            return (
                <div className={classes.rootList}>
                    <Typography variant="h4" gutterBottom><AddCircleOutlineIcon /> 조금 더 자세한 설명을 적어주세요!</Typography>
                    <Typography paragraph>
                        전문가들의 이해를 도와줄 설명이 필요해요.
                        <br />
                        잘 모르겠다면 전문가와 상담이라고 적어주세요.
                    </Typography>
                    <Slide timeout={200} direction="left" in={true} mountOnEnter unmountOnExit>
                        <TextField id="outlined-multiline-static"
                            label="구상하신 아이디어의 상세한 설명이 필요해요!"
                            style={{ backgroundColor: 'white' }}
                            multiline
                            rows={5}
                            variant="outlined"
                            fullWidth
                            value={detail}
                            onChange={onChangeDetail}
                        />
                    </Slide>
                </div>
            )
        case 5:
            return (
                <div className={classes.rootList}>
                    <Typography variant="h4" gutterBottom>작성하신 견적서를 확인해주세요!</Typography>
                    <br />
                    <Card elevation={3}>
                        <List>
                            {[
                                {
                                    icon: () => <BuildIcon />,
                                    title: '카테고리',
                                    text: category !== '' ? category : '설정하지 않았습니다.'
                                },
                                {
                                    icon: () => <QueryBuilderIcon />,
                                    title: '요청 마감일',
                                    text: deadline !== '' ? new Date(deadline).toLocaleString() : '설정하지 않았습니다.'
                                },
                                {
                                    icon: () => <AddCircleOutlineIcon />,
                                    title: '상세설명',
                                    text: detail !== '' ? detail : '설정하지 않았습니다.'
                                },
                                {
                                    icon: () => <LabelIcon />,
                                    title: '태그',
                                    text: tagList.map((obj) => {
                                        return <span key={obj}><Chip variant="outlined" size="small" label={obj} />&nbsp;</span>
                                    })
                                },
                                {
                                    icon: () => <CalendarTodayIcon />,
                                    title: '희망 제작 마감일',
                                    text: hopeDate
                                },
                            ].map((obj, index) => {
                                return (
                                    <ListItem key={index}>
                                        <ListItemIcon>
                                            <Tooltip arrow title={obj.title}>
                                                {obj.icon()}
                                            </Tooltip>
                                        </ListItemIcon>
                                        <ListItemText
                                            primary={obj.text}
                                        />
                                    </ListItem>
                                )
                            })}
                        </List>
                    </Card>
                </div>

            )
        default:
            return 'Unknown stepIndex';
    }
}

export default EnrollList;