import React, { useState, useEffect } from 'react';
import { TextField, Button, Grid, MenuItem } from '@mui/material';
import api from '../../utils/api';

const CertificateForm = ({ onSave, editingCertificate }) => {
  const initialFormState = {
    certificateNumber: '',
    issueDate: '',
    expiryDate: '',
    certificateStatus: 'ISSUED',
    clientMachineId: '',
    serviceRequestId: '',
  };

  const [formData, setFormData] = useState(initialFormState);
  const [clientMachines, setClientMachines] = useState([]);
  const [serviceRequests, setServiceRequests] = useState([]);

  useEffect(() => {
    api.get('/client-machines').then((res) => setClientMachines(res.data));
    api.get('/service-requests').then((res) => setServiceRequests(res.data));
  }, []);

  useEffect(() => {
    if (editingCertificate) setFormData(editingCertificate);
  }, [editingCertificate]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (formData.id) {
        await api.put(`/certificates/${formData.id}`, formData);
      } else {
        await api.post('/certificates', formData);
      }
      onSave();
      setFormData(initialFormState);
    } catch (error) {
      console.error('Error saving certificate:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <Grid container spacing={2} sx={{ p: 2 }}>
        <Grid item xs={12} sm={6}>
          <TextField sx={{ minWidth: 250 }} label="Certificate Number" name="certificateNumber" value={formData.certificateNumber} onChange={handleChange} required />
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField sx={{ minWidth: 250 }} type="date" label="Issue Date" name="issueDate" value={formData.issueDate} InputLabelProps={{ shrink: true }} onChange={handleChange} required />
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField sx={{ minWidth: 250 }} type="date" label="Expiry Date" name="expiryDate" value={formData.expiryDate} InputLabelProps={{ shrink: true }} onChange={handleChange} required />
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField select sx={{ minWidth: 250 }} label="Certificate Status" name="certificateStatus" value={formData.certificateStatus} onChange={handleChange}>
            <MenuItem value="ISSUED">ISSUED</MenuItem>
            <MenuItem value="REVOKED">REVOKED</MenuItem>
          </TextField>
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField select sx={{ minWidth: 250 }} label="Client Machine" name="clientMachineId" value={formData.clientMachineId} onChange={handleChange} required>
            {clientMachines.map((cm) => (
              <MenuItem key={cm.id} value={cm.id}>{cm.name || `Machine ${cm.id}`}</MenuItem>
            ))}
          </TextField>
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField select sx={{ minWidth: 250 }} label="Service Request" name="serviceRequestId" value={formData.serviceRequestId} onChange={handleChange} required>
            {serviceRequests.map((sr) => (
              <MenuItem key={sr.id} value={sr.id}>{sr.serviceRequestNumber}</MenuItem>
            ))}
          </TextField>
        </Grid>

        <Grid item xs={12}>
          <Button variant="contained" color="primary" type="submit" sx={{ mr: 2 }}>
            {formData.id ? 'Update Certificate' : 'Add Certificate'}
          </Button>

          {formData.id && (
            <Button
              variant="outlined"
              color="secondary"
              onClick={() => window.open(`/auth/api/certificates/${formData.id}/generate`, '_blank')}
            >
              Generate Excel
            </Button>
          )}
        </Grid>
      </Grid>
    </form>
  );
};

export default CertificateForm;