import React, { useState } from 'react';
import { Box, Typography } from '@mui/material';
import Navbar from '../components/navbar/Navbar';
import Sidebar from '../components/sidebar/Sidebar';
import CertificateForm from '../components/Certificates/CertificateForm';
import CertificateTable from '../components/Certificates/CertificateTable';

const Certificates = () => {
  const [refresh, setRefresh] = useState(false);
  const [editingCertificate, setEditingCertificate] = useState(null);
  const [drawerOpen, setDrawerOpen] = useState(true);

  const toggleDrawer = () => setDrawerOpen((prev) => !prev);

  const handleSave = () => {
    setRefresh(!refresh);
    setEditingCertificate(null);
  };

  const handleEdit = (cert) => {
    setEditingCertificate(cert);
  };

  return (
    <>
      <Navbar toggleDrawer={toggleDrawer} />
      <Box sx={{ display: 'flex', mt: '64px', height: 'calc(100vh - 64px)' }}>
        <Sidebar open={drawerOpen} />
        <Box p={3} sx={{ width: '100%' }}>
          <Typography variant="h4" gutterBottom>
            Calibration Certificates
          </Typography>
          <CertificateForm onSave={handleSave} editingCertificate={editingCertificate} />
          <CertificateTable refresh={refresh} onEdit={handleEdit} />
        </Box>
      </Box>
    </>
  );
};

export default Certificates;
