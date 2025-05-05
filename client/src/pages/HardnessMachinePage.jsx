import React, { useState } from 'react';
import HardnessMachineForm from '../components/ClientMachine/HardnessMachineForm';
import HardnessMachineTable from '../components/ClientMachine/HardnessMachineTable';
import { Typography, Box } from '@mui/material';
import Navbar from '../components/navbar/Navbar';
import Sidebar from '../components/sidebar/Sidebar';
import api from '../utils/api'; // make sure this path matches your project

const HardnessMachinePage = () => {
  const [refresh, setRefresh] = useState(false);
  const [drawerOpen, setDrawerOpen] = useState(true);
  const [editingData, setEditingData] = useState(null);

  const toggleDrawer = () => setDrawerOpen((o) => !o);

  const handleSave = () => {
    setEditingData(null);
    setRefresh(!refresh);
  };

  const handleEdit = (machine) => {
    setEditingData(machine);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this machine?')) {
      try {
        await api.delete(`/client-machines/${id}`);
        setRefresh(!refresh);
      } catch (err) {
        console.error('Error deleting machine:', err);
      }
    }
  };

  return (
    <>
      <Navbar toggleDrawer={toggleDrawer} />
      <Box sx={{ display: 'flex', mt: '64px', height: 'calc(100vh - 64px)' }}>
        <Sidebar open={drawerOpen} />
        <Box p={3} sx={{ flexGrow: 1 }}>
          <Typography variant="h4" gutterBottom>Hardness Machine Management</Typography>
          <HardnessMachineForm onSave={handleSave} editingData={editingData} />
          <HardnessMachineTable
            refresh={refresh}
            onEdit={handleEdit}
            onDelete={handleDelete}
          />
        </Box>
      </Box>
    </>
  );
};

export default HardnessMachinePage;
