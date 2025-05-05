// src/components/scales/ScalesForm.jsx
import React, { useState, useEffect } from 'react';
import {
  Box, Typography, TextField, Stack,
  FormControl, InputLabel, Select,
  MenuItem, FormHelperText, Button, Paper
} from '@mui/material';

const verificationTypes = ['HARDNESS_AND_FORCE', 'HARDNESS', 'FORCE'];

const defaultFormData = {
  id: null,
  scaleName: '',
  loadCapacity: '',
  verificationType: '',
  uncertainty: '',
  machineId: ''
};

export default function ScalesForm({
  machines = [],
  initialData = null,
  onSubmit = () => Promise.resolve(),
  onCancel = () => {}
}) {
  const [formData, setFormData] = useState(defaultFormData);
  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (initialData) {
      setFormData({
        ...initialData,
        loadCapacity: String(initialData.loadCapacity),
        uncertainty: String(initialData.uncertainty),
        machineId: initialData.machineId?.toString() || ''
      });
    } else {
      setFormData(defaultFormData);
    }
    setErrors({});
  }, [initialData]);

  const handleChange = e => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async e => {
    e.preventDefault();
    const newErrors = {};
    if (!formData.scaleName.trim()) newErrors.scaleName = 'Required';
    if (formData.loadCapacity === '' || isNaN(+formData.loadCapacity))
      newErrors.loadCapacity = 'Must be a number';
    if (!formData.verificationType)
      newErrors.verificationType = 'Required';
    if (formData.uncertainty === '' || isNaN(+formData.uncertainty))
      newErrors.uncertainty = 'Must be a number';
    if (!formData.machineId) newErrors.machineId = 'Required';

    if (Object.keys(newErrors).length) {
      setErrors(newErrors);
      return;
    }

    const dto = {
      ...formData,
      loadCapacity: +formData.loadCapacity,
      uncertainty: +formData.uncertainty,
      machineId: +formData.machineId
    };

    await onSubmit(dto);
    setFormData(defaultFormData);
  };

  const isEditing = formData.id !== null;
  const list = Array.isArray(machines) ? machines : [];

  return (
    <Paper sx={{ p: 2 }}>
      <Typography variant="h6" gutterBottom>
        {isEditing ? 'Edit Scale' : 'Add Scale'}
      </Typography>

      <Box component="form" onSubmit={handleSubmit}>
        <Stack spacing={2}>
          <TextField
            name="scaleName"
            label="Scale Name"
            value={formData.scaleName}
            onChange={handleChange}
            fullWidth
            error={!!errors.scaleName}
            helperText={errors.scaleName || ' '}
          />

          <TextField
            name="loadCapacity"
            label="Load Capacity"
            type="number"
            value={formData.loadCapacity}
            onChange={handleChange}
            fullWidth
            error={!!errors.loadCapacity}
            helperText={errors.loadCapacity || ' '}
          />

          <FormControl fullWidth error={!!errors.verificationType}>
            <InputLabel>Verification Type</InputLabel>
            <Select
              name="verificationType"
              value={formData.verificationType}
              onChange={handleChange}
              label="Verification Type"
            >
              <MenuItem value="" disabled>Select type</MenuItem>
              {verificationTypes.map(type => (
                <MenuItem key={type} value={type}>{type}</MenuItem>
              ))}
            </Select>
            <FormHelperText>{errors.verificationType || ' '}</FormHelperText>
          </FormControl>

          <TextField
            name="uncertainty"
            label="Uncertainty"
            type="number"
            value={formData.uncertainty}
            onChange={handleChange}
            fullWidth
            error={!!errors.uncertainty}
            helperText={errors.uncertainty || ' '}
          />

          <FormControl fullWidth error={!!errors.machineId}>
            <InputLabel>Machine</InputLabel>
            <Select
              name="machineId"
              value={formData.machineId}
              onChange={handleChange}
              label="Machine"
            >
              <MenuItem value="" disabled>Select machine</MenuItem>
              {list.map(machine => (
                <MenuItem key={machine.id} value={machine.id.toString()}>
                  {machine.name}
                </MenuItem>
              ))}
            </Select>
            <FormHelperText>{errors.machineId || ' '}</FormHelperText>
          </FormControl>

          <Stack direction="row" spacing={1}>
            <Button type="submit" variant="contained">
              {isEditing ? 'Update' : 'Add'}
            </Button>
            {isEditing && (
              <Button variant="outlined" onClick={onCancel}>
                Cancel
              </Button>
            )}
          </Stack>
        </Stack>
      </Box>
    </Paper>
  );
}
