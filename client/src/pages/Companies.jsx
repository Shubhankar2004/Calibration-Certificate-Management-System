// src/pages/Companies.jsx
import React, { useState, useEffect } from 'react';
import api from '../utils/api';
import Navbar from '../components/navbar/Navbar';
import Sidebar from '../components/sidebar/Sidebar';
import CompanyForm from '../components/company/CompanyForm';
import CompanyList from '../components/company/CompanyList';
import { Box, Typography } from '@mui/material';

export default function Companies() {
  const [companies, setCompanies] = useState([]);
  const [selectedCompany, setSelectedCompany] = useState(null);
  const [drawerOpen, setDrawerOpen] = useState(true);

  const fetchCompanies = async () => {
    try {
      const res = await api.get('/companies/all?page=0&size=100');
      setCompanies(res.data);
    } catch (err) {
      console.error('Error fetching companies', err);
    }
  };

  useEffect(() => {
    fetchCompanies();
  }, []);

  const handleAddOrUpdate = async (company) => {
    try {
      if (company.id) {
        await api.put(`/companies/${company.id}`, company);
      } else {
        await api.post('/companies', company);
      }
      fetchCompanies();
      setSelectedCompany(null);
    } catch (err) {
      console.error('Error saving company', err);
    }
  };

  const handleEdit = (company) => setSelectedCompany(company);
  const handleDelete = async (id) => {
    try {
      await api.delete(`/companies/${id}`);
      fetchCompanies();
    } catch (err) {
      console.error('Error deleting company', err);
    }
  };

  const toggleDrawer = () => setDrawerOpen((o) => !o);

  return (
    <>
      <Navbar toggleDrawer={toggleDrawer} />
      <Box sx={{ display: 'flex', mt: '64px', height: 'calc(100vh - 64px)' }}>
        <Sidebar open={drawerOpen} />
        <Box sx={{ flex: 1, p: 3, overflowY: 'auto' }}>
          <Typography variant="h4" gutterBottom>Manage Companies</Typography>
          <CompanyForm initialData={selectedCompany} onSubmit={handleAddOrUpdate} />
          <CompanyList companies={companies} onEdit={handleEdit} onDelete={handleDelete} />
        </Box>
      </Box>
    </>
  );
}
