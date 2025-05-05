import React, { useEffect, useState } from 'react';
import api from '../utils/api';
import EngineerForm from '../components/Employee/EngineerForm';
import EngineerTable from '../components/Employee/EngineerTable';
import Navbar from '../components/navbar/Navbar';
import Sidebar from '../components/sidebar/Sidebar';
import { Box, Typography } from '@mui/material';

const Employee = () => {


    const [drawerOpen, setDrawerOpen] = useState(true);
    const toggleDrawer = () => setDrawerOpen((o) => !o);

  const [engineers, setEngineers] = useState([]);
  const [selectedEngineer, setSelectedEngineer] = useState(null);

  useEffect(() => {
    fetchEngineers();
  }, []);

  const fetchEngineers = async () => {
    try {
      const response = await api.get('/engineers');
      setEngineers(response.data);
      console.log(response.data);
      
    } catch (error) {
      console.error('Error fetching engineers:', error);
    }
  };

  const handleSave = async (engineer) => {
    try {
      
      if (engineer.id) {
        console.log(engineer);
      
        const response = await api.put(`/engineers/${engineer.id}`, engineer);
        setEngineers(prev =>
          prev.map(e => (e.id === engineer.id ? response.data : e))
        );
      } else {
        const response = await api.post('/engineers', engineer);
        setEngineers(prev => [...prev, response.data]);
      }
      setSelectedEngineer(null);
    } catch (error) {
      console.error('Error saving engineer:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await api.delete(`/engineers/${id}`);
      setEngineers(prev => prev.filter(e => e.id !== id));
    } catch (error) {
      console.error('Error deleting engineer:', error);
    }
  };

  return (
    <>
    <Navbar toggleDrawer={toggleDrawer} />
    <Box sx={{ display: 'flex', mt: '64px', height: 'calc(100vh - 64px)' }}>
      <Sidebar open={drawerOpen} />
      <Box p={3}>
      <Typography variant="h4" gutterBottom>
        Manage Engineers
      </Typography>
      <EngineerForm onSave={handleSave} initialData={selectedEngineer} />
      <EngineerTable
        engineers={engineers}
        onEdit={setSelectedEngineer}
        onDelete={handleDelete}
      />
      </Box>
    </Box>
    </>
  );
};

export default Employee;
