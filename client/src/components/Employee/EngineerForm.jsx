import React, { useEffect, useState } from 'react';
import { TextField, Button, Paper, Grid } from '@mui/material';

const EngineerForm = ({ onSave, initialData }) => {
  const [formData, setFormData] = useState({ name: '', email: '', phone: '' });
  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (initialData) {
      setFormData(initialData);
    }
    setErrors({});
  }, [initialData]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newErrors = {};
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const phoneRegex = /^[6-9]\d{9}$/;
    if(!emailRegex.test(formData.email)){
      newErrors.email = 'Invalid Email';
    }
    if(!phoneRegex.test(formData.phone)){
      newErrors.phone = 'Invalid phone number';
    }
    if(Object.keys(newErrors).length){
      setErrors(newErrors);
      return;
    }

    await onSave(formData);
    setFormData({ name: '', email: '', phone: '' });
  };

  return (
    <Paper elevation={3} sx={{ p: 3, mb: 3 }}>
      <form onSubmit={handleSubmit}>
        <Grid container spacing={2}>
          <Grid item xs={12} sm={4}>
            <TextField
              label="Name"
              name="name"
              fullWidth
              value={formData.name}
              onChange={handleChange}
              required
              
            />
          </Grid>
          <Grid item xs={12} sm={4}>
            <TextField
              label="Email"
              name="email"
              type="email"
              fullWidth
              value={formData.email}
              onChange={handleChange}
              required
              error={!!errors.email}
            helperText={errors.email || ' '}
            />
          </Grid>
          <Grid item xs={12} sm={4}>
            <TextField
              label="Phone"
              name="phone"
              fullWidth
              value={formData.phone}
              onChange={handleChange}
              required
              error={!!errors.phone}
              helperText={errors.phone || ' '}
            />
          </Grid>
          <Grid item xs={12}>
            <Button type="submit" variant="contained">
              {formData.id ? 'Update' : 'Add'} Engineer
            </Button>
          </Grid>
        </Grid>
      </form>
    </Paper>
  );
};

export default EngineerForm;
