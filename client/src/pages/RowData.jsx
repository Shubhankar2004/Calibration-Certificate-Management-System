import React, { useState } from 'react';
import { Box } from '@mui/material';
import Navbar from '../components/navbar/Navbar';
import Sidebar from '../components/sidebar/Sidebar';
import RawDataForm from '../components/RawData/RawDataForm';
import RawDataTable from '../components/RawData/RawDataTable';

export default function RawData() {
  const [drawerOpen, setDrawerOpen] = useState(true);
  const [refresh, setRefresh] = useState(false);
  const toggleDrawer = () => setDrawerOpen((o) => !o);

  const handleSave = () => {
    setRefresh(!refresh); // Toggle to reload table
  };

  return (
    <>
      <Navbar toggleDrawer={toggleDrawer} />
      <Box sx={{ display: 'flex', mt: '64px', height: 'calc(100vh - 64px)' }}>
        <Sidebar open={drawerOpen} />
        <Box p={3} flexGrow={1}>
          <RawDataForm onSave={handleSave} />
          <RawDataTable refreshFlag={refresh} />
        </Box>
      </Box>
    </>
  );
}
