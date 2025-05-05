import React, { useState, useEffect } from 'react';
import {
  Box, Button, Grid, MenuItem, Paper, TextField, Typography
} from '@mui/material';
import api from '../../utils/api';

const initialData = {
  name: '',
  make: '',
  identificationNumber: '',
  rangeValue: '',
  property: '',
  measurementUncertainty: '',
  nonUniformity: '',
  calibratedBy: '',
  validUpto: '',
  scaleId: ''
};

export default function TestBlockForm({ fetchData, editingData, setEditingData }) {
  const [formData, setFormData] = useState(initialData);
  const [scales, setScales] = useState([]);

  useEffect(() => {
    if (editingData) {
      setFormData(prev => ({
        ...prev,
        ...editingData
      }));
    }
  }, [editingData]);

  useEffect(() => {
    api.get('/scales')
      .then(res => {
        console.log("Scales fetched successfully:", res.data);
        setScales(res.data);
      })
      .catch(err => {
        console.error('Error fetching scales:', err);
      });
  }, []);

  // Log scales after they've been updated
  useEffect(() => {
    console.log("Updated scales:", scales);
  }, [scales]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Basic validation
    for (let key in formData) {
      if (formData[key] === '' || formData[key] === null) {
        alert(`Please fill the field: ${key}`);
        return;
      }
    }

    try {
      if (formData.id) {
        await api.put(`/test-blocks/${formData.id}`, formData);
      } else {
        await api.post('/test-blocks', formData);
      }
      fetchData();
      setFormData(initialData);
      setEditingData(null);
    } catch (err) {
      console.error('Error submitting test block:', err);
    }
  };

  return (
    <Paper elevation={3} sx={{ p: 3, mb: 3 }}>
      <Typography variant="h6" gutterBottom>{formData.id ? 'Edit Test Block' : 'Add Test Block'}</Typography>
      <form onSubmit={handleSubmit}>
        <Grid container spacing={2}>
          {[
            { name: 'name', label: 'Name' },
            { name: 'make', label: 'Make' },
            { name: 'identificationNumber', label: 'Identification Number' },
            { name: 'rangeValue', label: 'Range Value', type: 'number' },
            { name: 'property', label: 'Property' },
            { name: 'measurementUncertainty', label: 'Measurement Uncertainty', type: 'number' },
            { name: 'nonUniformity', label: 'Non Uniformity', type: 'number' },
            { name: 'calibratedBy', label: 'Calibrated By' },
            { name: 'validUpto', label: 'Valid Upto', type: 'date' }
          ].map(field => (
            <Grid item xs={12} sm={6} key={field.name}>
              <TextField
                sx={{ minWidth: 250 }}
                label={field.label}
                name={field.name}
                type={field.type || 'text'}
                InputLabelProps={field.type === 'date' ? { shrink: true } : {}}
                value={formData[field.name]}
                onChange={handleChange}
              />
            </Grid>
          ))}

          {/* Dropdown for scaleId */}
          <Grid item xs={12} sm={6}>
            <TextField
              sx={{ minWidth: 250 }}
              select
              label="Scale"
              name="scaleId"
              value={formData.scaleId}
              onChange={handleChange}
            >
              {scales.map(scale => (
                <MenuItem key={scale.id} value={scale.id}>
                  {scale.scaleName} {/* Display scale name */}
                </MenuItem>
              ))}
            </TextField>
          </Grid>

          <Grid item xs={12}>
            <Button type="submit" variant="contained" color="primary">
              {formData.id ? 'Update' : 'Save'}
            </Button>
          </Grid>
        </Grid>
      </form>
    </Paper>
  );
}
