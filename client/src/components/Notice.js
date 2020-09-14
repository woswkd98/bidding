import React from 'react'
import { Grid, Typography } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
    gridStyle: {
        margin: '4% auto',
        width: "80%",
    },
}));

const Notice = () => {

    const classes = useStyles();
    return (
        <Grid container className={classes.gridStyle} spacing={9}>
            <Grid item xs={12} md={4}>
                <Typography align="center" variant="h5" gutterBottom>안내</Typography>
                <ul>
                    <li>
                        본 웹사이트는 고객과 전문가를 연결시켜드리는 중개 플랫폼 입니다.
                        </li>
                    <br />
                    <li>
                        사이트 운영자는 거래에 관여하지 않습니다.
                        </li>
                    <br />
                    <li>

                    </li>
                </ul>
            </Grid>
            <Grid item xs={12} md={4}>
                <Typography align="center" variant="h5" gutterBottom>교환/환불</Typography>
            </Grid>
            <Grid item xs={12} md={4}>
                <Typography align="center" variant="h5" gutterBottom>평점과 리뷰</Typography>
                <ul>
                    <li>
                        평점과 리뷰는 거래 완료 고객에 한해서만 작성이 가능합니다.
                        </li>
                    <br />
                    <li>
                        사이트 운영자는 리뷰에 관여하지 않습니다.
                        </li>
                    <br />
                    <li>

                    </li>
                </ul>
            </Grid>
        </Grid>
    )
}

export default Notice
