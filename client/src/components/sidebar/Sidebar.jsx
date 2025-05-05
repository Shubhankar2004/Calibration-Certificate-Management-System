import React from 'react';
import { Link } from 'react-router-dom';
import {
  Drawer,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Toolbar,
  Divider,
  useMediaQuery,
  useTheme,
} from '@mui/material';

import AssignmentIcon from '@mui/icons-material/Assignment';
import BuildIcon from '@mui/icons-material/Build';
import PrecisionManufacturingIcon from '@mui/icons-material/PrecisionManufacturing';
import TimelineIcon from '@mui/icons-material/Timeline';
import BusinessIcon from '@mui/icons-material/Business';
import ScaleIcon from '@mui/icons-material/Scale';
import PeopleIcon from '@mui/icons-material/People';
import Inventory2Icon from '@mui/icons-material/Inventory2';
import ComputerIcon from '@mui/icons-material/Computer';
import HomeIcon from '@mui/icons-material/Home';  // Import HomeIcon

export const drawerWidth = 240;

const menuItems = [
  { text: 'Home', icon: <HomeIcon />, route: '/adminDashboard' },  // Home item
  { text: 'Machines', icon: <PrecisionManufacturingIcon />, route: '/machines' },
  { text: 'Scales', icon: <ScaleIcon />, route: '/scales' },
  { text: 'Test Blocks', icon: <Inventory2Icon />, route: '/testBlocks' },
  { text: 'Employee', icon: <PeopleIcon />, route: '/employee' },
  { text: 'Companies', icon: <BusinessIcon />, route: '/companies' },
  { text: 'Service Request', icon: <BuildIcon />, route: '/service-request-form' },
  { text: 'Client Machines', icon: <ComputerIcon />, route: '/client-machines' },
  { text: 'Raw Data', icon: <AssignmentIcon />, route: '/rawData' },
  { text: 'Certificates', icon: <TimelineIcon />, route: '/certificates' },
];

const Sidebar = ({ open, toggleDrawer }) => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

  return (
    <Drawer
      variant={isMobile ? 'temporary' : 'persistent'}
      open={open}
      onClose={toggleDrawer}
      sx={{
        width: drawerWidth,
        flexShrink: 0,
        '& .MuiDrawer-paper': {
          width: drawerWidth,
          boxSizing: 'border-box',
        },
      }}
    >
      <Toolbar />
      <Divider />
      <List>
        {menuItems.map(({ text, icon, route }) => (
          <ListItem
            button
            key={text}
            component={Link}
            to={route}
            onClick={isMobile ? toggleDrawer : undefined}
          >
            <ListItemIcon>{icon}</ListItemIcon>
            <ListItemText primary={text} />
          </ListItem>
        ))}
      </List>
    </Drawer>
  );
};

export default Sidebar;