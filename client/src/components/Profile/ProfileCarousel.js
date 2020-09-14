import React from 'react'
import Dialog from '@material-ui/core/Dialog';


const ProfileCarousel = ({ open, setOpen, src }) => {
    return (
        <Dialog
            maxWidth="xl"
            open={open}
            onClose={() => setOpen(false)}
            onStart={() => setOpen(false)}
        >
            <img style={{ display: 'block', margin: 'auto' }} src={src} alt="portfolio" />
        </Dialog>
    )
}

export default ProfileCarousel

