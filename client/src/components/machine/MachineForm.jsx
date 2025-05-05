// src/components/machine/MachineForm.jsx
import React, { useState, useEffect } from 'react';
import { TextField, Button, Box } from '@mui/material';

const initialState = {
  name: '',
};

export default function MachineForm({ initialData, onSubmit }) {
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
    onSubmit({
      ...form,
      id: initialData?.id || null,
      name: form.name.trim(),
    });
    if (!initialData) {
      setForm(initialState);
    }
  };

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{ display: 'grid', gap: 2, mb: 4, maxWidth: 600 }}
    >
      <TextField
        label="Machine Name"
        name="name"
        value={form.name}
        onChange={handleChange}
        required
      />
      <Button type="submit" variant="contained">
        {initialData ? 'Update Machine' : 'Add Machine'}
      </Button>
    </Box>
  );
}
