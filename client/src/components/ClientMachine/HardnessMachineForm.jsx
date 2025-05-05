import React, { useState, useEffect } from 'react';
import {
  TextField, Button, Grid, MenuItem,
  Select, InputLabel, FormControl
} from '@mui/material';
import api from '../../utils/api';

const HardnessMachineForm = ({ onSave, editingData }) => {
  const initialFormState = {
    make: '',
    model: '',
    serialNumber: '',
    identificationNumber: '',
    amount: '',
    machineId: '',
    serviceRequestId: '',
    selectedScaleIds: []
  };

  const [formData, setFormData] = useState(initialFormState);
  const [machines, setMachines] = useState([]);
  const [serviceRequests, setServiceRequests] = useState([]);
  const [scales, setScales] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [machinesRes, serviceReqRes, scalesRes] = await Promise.all([
          api.get('/machines'),
          api.get('/service-requests'),
          api.get('/scales')
        ]);
        setMachines(machinesRes.data);
        setServiceRequests(serviceReqRes.data);
        setScales(scalesRes.data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  // Handle edit mode: populate formData when editingData is provided
  useEffect(() => {
    if (editingData) {
      setFormData({
        id: editingData.id,
        make: editingData.make,
        model: editingData.model,
        serialNumber: editingData.serialNumber,
        identificationNumber: editingData.identificationNumber,
        amount: editingData.amount,
        machineId: editingData.machine?.id || editingData.machineId,
        serviceRequestId: editingData.serviceRequest?.id || editingData.serviceRequestId,
        selectedScaleIds: editingData.selectedScales?.map(scale => scale.id) || []
      });
    }
  }, [editingData]);

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === 'selectedScaleIds') {
      setFormData(prev => ({
        ...prev,
        [name]: value // array of scale ids
      }));
    } else {
      setFormData(prev => ({
        ...prev,
        [name]: value
      }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        ...formData,
        selectedScaleIds: formData.selectedScaleIds.map(id => parseInt(id))
      };

      if (formData.id) {
        await api.put(`/client-machines/${formData.id}`, payload);
      } else {
        await api.post('/client-machines', payload);
      }

      onSave();
      setFormData(initialFormState);
    } catch (error) {
      console.error('Error submitting form:', error);
    }
  };

  const filteredScales = formData.machineId
    ? scales.filter(scale => scale.machineId === parseInt(formData.machineId))
    : [];

  return (
    <form onSubmit={handleSubmit}>
      <Grid container spacing={2} sx={{ p: 2 }}>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Make"
            name="make"
            sx={{ minWidth: 250 }}
            value={formData.make}
            onChange={handleChange}
            required
          />
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField
            label="Model"
            name="model"
            sx={{ minWidth: 250 }}
            value={formData.model}
            onChange={handleChange}
            required
          />
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField
            label="Serial Number"
            name="serialNumber"
            sx={{ minWidth: 250 }}
            value={formData.serialNumber}
            onChange={handleChange}
            required
          />
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField
            label="Identification Number"
            name="identificationNumber"
            sx={{ minWidth: 250 }}
            value={formData.identificationNumber}
            onChange={handleChange}
            required
          />
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField
            label="Amount"
            name="amount"
            type="number"
            sx={{ minWidth: 250 }}
            value={formData.amount}
            onChange={handleChange}
            required
          />
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField
            select
            label="Machine"
            name="machineId"
            sx={{ minWidth: 250 }}
            value={formData.machineId}
            onChange={handleChange}
            required
          >
            {machines.map((m) => (
              <MenuItem key={m.id} value={m.id}>{m.name}</MenuItem>
            ))}
          </TextField>
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField
            select
            label="Service Request"
            name="serviceRequestId"
            sx={{ minWidth: 250 }}
            value={formData.serviceRequestId}
            onChange={handleChange}
            required
          >
            {serviceRequests.map((sr) => (
              <MenuItem key={sr.id} value={sr.id}>{sr.serviceRequestNumber}</MenuItem>
            ))}
          </TextField>
        </Grid>

        <Grid item xs={12} sm={6}>
          <FormControl sx={{ minWidth: 250 }}>
            <InputLabel>Scales (Filtered by Machine)</InputLabel>
            <Select
              multiple
              name="selectedScaleIds"
              value={formData.selectedScaleIds}
              onChange={handleChange}
              label="Scales (Filtered by Machine)"
              renderValue={(selected) =>
                filteredScales
                  .filter(scale => selected.includes(scale.id.toString()) || selected.includes(scale.id))
                  .map(scale => scale.scaleName)
                  .join(', ')
              }
            >
              {filteredScales.length > 0 ? (
                filteredScales.map((scale) => (
                  <MenuItem key={scale.id} value={scale.id}>
                    {scale.scaleName}
                  </MenuItem>
                ))
              ) : (
                <MenuItem disabled>No scales found for selected machine</MenuItem>
              )}
            </Select>
          </FormControl>
        </Grid>

        <Grid item xs={12}>
          <Button type="submit" variant="contained" color="primary" sx={{ minWidth: 250 }}>
            {formData.id ? 'Update' : 'Submit'}
          </Button>
        </Grid>
      </Grid>
    </form>
  );
};

export default HardnessMachineForm;
