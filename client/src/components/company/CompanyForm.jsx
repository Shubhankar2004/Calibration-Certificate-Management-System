// src/components/company/CompanyForm.jsx
import React, { useState, useEffect } from 'react';
import { TextField, Button, Box } from '@mui/material';

const initialState = {
  name: '',
  address: '',
  contactPerson: '',
  phone: '',
  email: '',
  gstNo: ''
};

export default function CompanyForm({ initialData, onSubmit }) {
  const [form, setForm] = useState(initialState);

  useEffect(() => {
    if (initialData) {
      setForm(initialData);
    } else {
      setForm(initialState);
    }
  }, [initialData]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(form);
    setForm(initialState);
  };

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{ display: 'grid', gap: 2, mb: 4, maxWidth: 600 }}
    >
      <TextField label="Name" name="name" value={form.name} onChange={handleChange} required />
      <TextField label="Address" name="address" value={form.address} onChange={handleChange} required />
      <TextField label="Contact Person" name="contactPerson" value={form.contactPerson} onChange={handleChange} required />
      <TextField label="Phone" name="phone" value={form.phone} onChange={handleChange} required />
      <TextField label="Email" name="email" value={form.email} onChange={handleChange} required />
      <TextField label="GST No" name="gstNo" value={form.gstNo} onChange={handleChange} required />
      <Button type="submit" variant="contained">
        {form.id ? 'Update Company' : 'Add Company'}
      </Button>
    </Box>
  );
}
