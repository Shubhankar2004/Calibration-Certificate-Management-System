// src/pages/ScalesPage.js
import React, { useState, useEffect } from 'react';
import { Box, Typography, Toolbar, useMediaQuery, useTheme } from '@mui/material';
import Navbar from '../components/navbar/Navbar';
import Sidebar, { drawerWidth } from '../components/sidebar/Sidebar';
import ScalesForm from '../components/scales/ScalesForm';
import ScalesList from '../components/scales/ScalesList';
import api from '../utils/api';

export default function ScalesPage() {
  const [scales, setScales] = useState([]);
  const [machines, setMachines] = useState([]);
  const [editing, setEditing] = useState(null);
  const [open, setOpen] = useState(true);

  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

  // Function to toggle drawer visibility
  const toggleDrawer = () => setOpen(!open);

  // Fetch machines and scales data
  useEffect(() => {
    api.get('/machines')
      .then(res => {
        const list = Array.isArray(res.data) ? res.data : (res.data.content || []);
        setMachines(list);
      })
      .catch(err => console.error('Error fetching machines:', err));

    refreshScales();
  }, []);

  // Function to refresh scales data
  const refreshScales = () =>
    api.get('/scales?page=0&size=100')
      .then(res => setScales(res.data))
      .catch(err => console.error('Error fetching scales:', err));

  // Function to handle creating a new scale
  const handleCreate = dto =>
    api.post('/scales', dto)
      .then(() => refreshScales())
      .catch(err => console.error('Error creating scale:', err));

  // Function to handle updating an existing scale
  const handleUpdate = dto =>
    api.put(`/scales/${dto.id}`, dto)
      .then(() => {
        setEditing(null);
        refreshScales();
      })
      .catch(err => console.error('Error updating scale:', err));

  // Function to handle deleting a scale
  const handleDelete = id => {
    if (!window.confirm('Delete this scale?')) return;
    api.delete(`/scales/${id}`)
      .then(() => refreshScales())
      .catch(err => console.error('Error deleting scale:', err));
  };

  return (<>
    <Box sx={{ display: 'flex' }}>
      {/* Navbar with drawer toggle */}
      <Navbar toggleDrawer={toggleDrawer} />

      {/* Sidebar with open/close state */}
      <Sidebar open={open} toggleDrawer={toggleDrawer} />

      {/* Main content area */}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          width: '100%',  // Set width to 100% for full width
          marginLeft: 0,  // Remove all left margin
        }}
      >
        <Toolbar />
        <Typography variant="h4" gutterBottom>
          Scale Management
        </Typography>

        <Box mb={4}>
          {machines.length > 0 ? (
            <ScalesForm
              machines={machines}
              initialData={editing}
              onSubmit={editing ? handleUpdate : handleCreate}
              onCancel={() => setEditing(null)}
            />
          ) : (
            <Typography>Loading machines...</Typography>
          )}
        </Box>

        <ScalesList
          scales={scales}
          machines={machines}
          onEdit={setEditing}
          onDelete={handleDelete}
        />
      </Box>
    </Box>
    </>
  );
}
