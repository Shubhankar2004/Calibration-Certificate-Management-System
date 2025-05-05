import React, { useState, useEffect } from 'react';
import TestBlockForm from '../components/TestBlocks/TestBlockForm';
import TestBlockTable from '../components/TestBlocks/TestBlockTable';
import api from '../utils/api';
import { Box, Typography } from '@mui/material';
import Navbar from '../components/navbar/Navbar';
import Sidebar from '../components/sidebar/Sidebar';

export default function TestBlock() {
  const [drawerOpen, setDrawerOpen] = useState(true);
  const toggleDrawer = () => setDrawerOpen((o) => !o);

  const [testBlocks, setTestBlocks] = useState([]);
  const [editingData, setEditingData] = useState(null);

  const fetchData = async () => {
    try {
      const res = await api.get('/test-blocks');
      setTestBlocks(res.data);
      console.log('Fetched Test Blocks:', res.data);

    } catch (err) {
      console.error('Error fetching test blocks:', err);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this Test Block?')) {
      await api.delete(`/test-blocks/${id}`);
      fetchData();
    }
  };

  const handleEdit = (data) => {
    setEditingData(data);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      <Navbar toggleDrawer={toggleDrawer} />
      <Box sx={{ display: 'flex', mt: '64px', height: 'calc(100vh - 64px)' }}>
        <Sidebar open={drawerOpen} />
        <Box p={3}>
          <Typography variant="h5" gutterBottom>Manage Test Blocks</Typography>
          <TestBlockForm fetchData={fetchData} editingData={editingData} setEditingData={setEditingData} />
          <TestBlockTable testBlocks={testBlocks} onEdit={handleEdit} onDelete={handleDelete} />
        </Box>
      </Box>
    </>
  );
}
