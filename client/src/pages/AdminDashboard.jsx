import React, { useState } from 'react';
import {
  Box,
  Toolbar,
  Card,
  CardContent,
  Typography,
  List,
  ListItem,
  ListItemText,
  ListItemIcon
} from '@mui/material';
import BuildIcon from '@mui/icons-material/Build';
import StraightenIcon from '@mui/icons-material/Straighten';
import CategoryIcon from '@mui/icons-material/Category';
import PeopleIcon from '@mui/icons-material/People';
import BusinessIcon from '@mui/icons-material/Business';
import AssignmentIcon from '@mui/icons-material/Assignment';
import DevicesIcon from '@mui/icons-material/Devices';
import BarChartIcon from '@mui/icons-material/BarChart';
import VerifiedIcon from '@mui/icons-material/Verified';
import { Link } from 'react-router-dom'; // Import Link for navigation

import Navbar from '../components/navbar/Navbar';
import Sidebar from '../components/sidebar/Sidebar';

const drawerWidth = 240;

const AdminDashboard = () => {
  const [open, setOpen] = useState(true);

  const toggleDrawer = () => {
    setOpen(!open);
  };

  return (
    <Box sx={{ display: 'flex' }}>
      {/* Navbar */}
      <Navbar toggleDrawer={toggleDrawer} />

      {/* Sidebar */}
      <Sidebar open={open} />

      {/* Main Content */}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          marginLeft: open ? `${drawerWidth}px` : 0,
          transition: 'margin 0.3s',
        }}
      >
        <Toolbar />
        <Typography variant="h4" gutterBottom>
          Welcome to the Admin Dashboard
        </Typography>

        <Card elevation={2} sx={{ mt: 2 }}>
          <CardContent>
            <Typography variant="h6" gutterBottom>
              Steps to Handle the Software:
            </Typography>
            <List>
              <ListItem component={Link} to="/machines">
                <ListItemIcon><BuildIcon fontSize="large" /></ListItemIcon>
                <ListItemText
                  primary="Machines"
                  secondary="Register calibration machines used in the process."
                />
              </ListItem>
              <ListItem component={Link} to="/scales">
                <ListItemIcon><StraightenIcon fontSize="large" /></ListItemIcon>
                <ListItemText
                  primary="Scales"
                  secondary="Define measurement scales and associate them with machines."
                />
              </ListItem>
              <ListItem component={Link} to="/testBlocks">
                <ListItemIcon><CategoryIcon fontSize="large" /></ListItemIcon>
                <ListItemText
                  primary="Test Blocks"
                  secondary="Add test blocks used for machine calibration checks."
                />
              </ListItem>
              <ListItem component={Link} to="/employee">
                <ListItemIcon><PeopleIcon fontSize="large" /></ListItemIcon>
                <ListItemText
                  primary="Employee"
                  secondary="Manage employees responsible for calibration and services."
                />
              </ListItem>
              <ListItem component={Link} to="/companies">
                <ListItemIcon><BusinessIcon fontSize="large" /></ListItemIcon>
                <ListItemText
                  primary="Companies"
                  secondary="Maintain client company details for tracking services."
                />
              </ListItem>
              <ListItem component={Link} to="/service-request-form">
                <ListItemIcon><AssignmentIcon fontSize="large" /></ListItemIcon>
                <ListItemText
                  primary="Service Request"
                  secondary="Raise and manage service requests from clients."
                />
              </ListItem>
              <ListItem component={Link} to="/client-machines">
                <ListItemIcon><DevicesIcon fontSize="large" /></ListItemIcon>
                <ListItemText
                  primary="Client Machines"
                  secondary="Register and manage machines belonging to clients."
                />
              </ListItem>
              <ListItem component={Link} to="/rawData">
                <ListItemIcon><BarChartIcon fontSize="large" /></ListItemIcon>
                <ListItemText
                  primary="Raw Data"
                  secondary="Enter calibration and inspection data for machines."
                />
              </ListItem>
              <ListItem component={Link} to="/certificates">
                <ListItemIcon><VerifiedIcon fontSize="large" /></ListItemIcon>
                <ListItemText
                  primary="Certificates"
                  secondary="Generate calibration certificates from raw data."
                />
              </ListItem>
            </List>
          </CardContent>
        </Card>
      </Box>
    </Box>
  );
};

export default AdminDashboard;