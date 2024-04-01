import React, { useState } from 'react';
import { AppBar, Toolbar, Typography, IconButton, Avatar, Badge, styled} from '@mui/material';
import DashboardIcon from '@mui/icons-material/Dashboard';
import InfoIcon from '@mui/icons-material/Info';
import Swal from 'sweetalert2';

const StyledBadge = styled(Badge)(({ theme }) => ({
    '& .MuiBadge-badge': {
      backgroundColor: '#44b700',
      color: '#44b700',
      boxShadow: `0 0 0 2px ${theme.palette.background.paper}`,
      '&::after': {
        position: 'absolute',
        top: 0,
        left: 0,
        width: '100%',
        height: '100%',
        borderRadius: '50%',
        animation: 'ripple 1.2s infinite ease-in-out',
        border: '1px solid currentColor',
        content: '""',
      },
    },
    '@keyframes ripple': {
      '0%': {
        transform: 'scale(.8)',
        opacity: 1,
      },
      '100%': {
        transform: 'scale(2.4)',
        opacity: 0,
      },
    },
}));

const NavBarComp = () => {
    const iconButtonStyle = {
        color: '#000000',
    };

    const handleInfoClick = () => {
      Swal.fire({
        title: "You can follow my LinkedIn Aan Adrian Khothibulumam",
        width: 500,
        padding: "3em",
        color: "#716add",
        background: "#fff url(https://media.giphy.com/media/xT0BKtmw7D1TXWPT0Y/giphy.gif?cid=790b7611891e2ksl4imy8yv07o6qt029y5jb43pm92bh6xhf&ep=v1_gifs_search&rid=giphy.gif&ct=g)",
        backdrop: `
          rgba(0,0,123,0.4)
          url("https://sweetalert2.github.io/images/nyan-cat.gif")
          left top
          no-repeat
        `
      });
    };

    return (
        <AppBar position="static" sx={{ backgroundColor: '#FFFFFF', color: '#000000', boxShadow: 'none' }}>
            <Toolbar>
                <IconButton color="inherit" disableRipple sx={iconButtonStyle}>
                    <DashboardIcon color="blue"/>
                    <Typography variant="h6" sx={{ fontSize: '1rem', marginLeft: '3px', color:'orange', fontWeight:700 }}>
                        Dashboard
                        <Typography color='#0665A2' component='span' fontWeight='700' fontSize='1.2rem' fontStyle='italic'>LOCOMOTIVE</Typography>
                    </Typography>
                </IconButton>
                <div style={{ flexGrow: 1 }} />
                <IconButton color="inherit" disableRipple sx={iconButtonStyle} onClick={handleInfoClick}>
                    <InfoIcon />
                    <Typography variant="caption" sx={{ fontSize: '1rem', marginLeft: '3px' }}>Info</Typography>
                </IconButton>
                <IconButton>
                    <StyledBadge
                        overlap="circular"
                        anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
                        variant="dot"
                    >
                        <Avatar alt="Remy Sharp" src="" />
                    </StyledBadge>
                    <Typography variant="caption" sx={{ fontSize: '1rem', marginLeft: '3px' }}>aan629</Typography>
                </IconButton>
            </Toolbar>
        </AppBar>
    );
};

export default NavBarComp;