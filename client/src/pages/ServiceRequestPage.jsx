import React, { useState, useEffect } from 'react';
import ServiceRequestForm from '../components/ServiceRequests/ServiceRequestForm';
import ServiceRequestTable from '../components/ServiceRequests/ServiceRequestTable';
import { Box, Typography } from '@mui/material';
import Navbar from '../components/navbar/Navbar';
import Sidebar from '../components/sidebar/Sidebar';
import api from '../utils/api';

const ServiceRequestPage = () => {
  const [requests, setRequests] = useState([]);
  const [editingData, setEditingData] = useState(null);
  const [drawerOpen, setDrawerOpen] = useState(true);

  const toggleDrawer = () => setDrawerOpen((o) => !o);

  const fetchServiceData = async () => {
    try {
      const response = await api.get('/service-requests');
      setRequests(response.data);
    } catch (err) {
      console.log('Error fetching service requests:', err);
    }
  };

  const handleSubmit = async (data) => {
    try {
      if (data.id) {
        await api.put(`/service-requests/${data.id}`, data);
      } else {
        await api.post('/service-requests', data);
      }
      fetchServiceData();
      setEditingData(null);
    } catch (err) {
      console.error('Error saving service request:', err);
    }
  };

  const handleDeleteRequest = async (id) => {
    if (window.confirm('Are you sure you want to delete this service request?')) {
      try {
        await api.delete(`/service-requests/${id}`);
        fetchServiceData();
      } catch (err) {
        console.error('Error deleting:', err);
      }
    }
  };

  const handleEditRequest = (data) => {
    setEditingData(data);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  useEffect(() => {
    fetchServiceData();
  }, []);

  return (
    <>
      <Navbar toggleDrawer={toggleDrawer} />
      <Box sx={{ display: 'flex', mt: '64px', height: 'calc(100vh - 64px)' }}>
        <Sidebar open={drawerOpen} />
        <Box p={3} sx={{ flexGrow: 1 }}>
          <Typography variant="h4" gutterBottom>Service Request Form</Typography>
          <ServiceRequestForm onSubmit={handleSubmit} editingData={editingData} />
          <Typography variant="h5" mt={4}>Service Request List</Typography>
          <ServiceRequestTable
            data={requests}
            onEdit={handleEditRequest}
            onDelete={handleDeleteRequest}
          />
        </Box>
      </Box>
    </>
  );
};

export default ServiceRequestPage;
