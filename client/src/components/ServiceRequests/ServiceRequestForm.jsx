import React, { useState, useEffect } from 'react';
import {
  TextField, Checkbox, FormControlLabel, Button, Grid, Paper, MenuItem, Typography
} from '@mui/material';
import api from '../../utils/api';

const initialFormData = {
  serviceRequestNumber: '',
  requestDate: '',
  contactPerson: '',
  contactNumber: '',
  email: '',
  frequencyRequired: '',
  certificatesSentBy: '',
  agreedDateForCalibration: '',
  validityPeriod: '',
  status: '',
  companyId: '',
  engineerId: '',
  calibrationType: '',
  requiredCertificatesUnderNabl: false,
  calibrationAsPerStandard: false,
  repairingRequired: false,
  calibrationScopeAccepted: false,
  conformityStatementRequired: false,
  commercialTermsAccepted: false,
  nextCalibrationDateRequired: false,
};

const ServiceRequestForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState(initialFormData);
  const [companies, setCompanies] = useState([]);
  const [engineers, setEngineers] = useState([]);

  useEffect(() => {
    api.get('/companies/all').then(res => setCompanies(res.data));
    api.get('/engineers').then(res => setEngineers(res.data));
  }, []);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    const newValue = type === 'checkbox' ? checked : value;

    if (name === 'companyId') {
      const selected = companies.find(c => c.id === parseInt(value));
      if (selected) {
        setFormData(prev => ({
          ...prev,
          companyId: selected.id,
          contactPerson: selected.contactPerson,
          contactNumber: selected.phone,
          email: selected.email,
        }));
        return;
      }
    }

    setFormData(prev => ({ ...prev, [name]: newValue }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const requiredFields = ['serviceRequestNumber', 'requestDate', 'companyId', 'engineerId', 'status', 'calibrationType'];
    for (let field of requiredFields) {
      if (!formData[field]) {
        alert(`Please fill in: ${field}`);
        return;
      }
    }

    onSubmit(formData);
    setFormData(initialFormData);
  };

  return (
    <Paper elevation={3} style={{ padding: '20px', marginBottom: '20px' }}>
      <Typography variant="h6" gutterBottom>Service Request Form</Typography>
      <form onSubmit={handleSubmit}>
        <Grid container spacing={2}>
        <Grid item xs={12} sm={6}>
            <TextField
              select sx={{ minWidth: 250 }} name="companyId" label="Company"
              value={formData.companyId} onChange={handleChange}
            >
              {companies.map(company => (
                <MenuItem key={company.id} value={company.id}>{company.name}</MenuItem>
              ))}
            </TextField>
          </Grid>
          {/* Text fields */}
          {[
            { name: 'serviceRequestNumber', label: 'Request Number' },
            { name: 'requestDate', label: 'Request Date', type: 'date' },
            { name: 'contactPerson', label: 'Contact Person' },
            { name: 'contactNumber', label: 'Contact Number' },
            { name: 'email', label: 'Email' },
            { name: 'certificatesSentBy', label: 'Certificates Sent By' },
            { name: 'agreedDateForCalibration', label: 'Agreed Date For Calibration', type: 'date' },
            { name: 'validityPeriod', label: 'Validity Period', type: 'number' }
          ].map(({ name, label, type }) => (
            <Grid item xs={12} sm={6} key={name}>
              <TextField
                sx={{ minWidth: 250 }}
                label={label}
                name={name}
                type={type || 'text'}
                value={formData[name]}
                onChange={handleChange}
                InputLabelProps={type === 'date' ? { shrink: true } : {}}
              />
            </Grid>
          ))}

          {/* Dropdowns */}
         

          <Grid item xs={12} sm={6}>
            <TextField
              select sx={{ minWidth: 250 }} name="engineerId" label="Engineer"
              value={formData.engineerId} onChange={handleChange}
            >
              {engineers.map(engineer => (
                <MenuItem key={engineer.id} value={engineer.id}>{engineer.name}</MenuItem>
              ))}
            </TextField>
          </Grid>

          <Grid item xs={12} sm={6}>
            <TextField
              select sx={{ minWidth: 250 }} name="status" label="Status"
              value={formData.status} onChange={handleChange}
            >
              {['PENDING', 'APPROVED', 'REJECTED'].map(status => (
                <MenuItem key={status} value={status}>{status}</MenuItem>
              ))}
            </TextField>
          </Grid>

          <Grid item xs={12} sm={6}>
            <TextField
              select sx={{ minWidth: 250 }} name="calibrationType" label="Calibration Type"
              value={formData.calibrationType} onChange={handleChange}
            >
              {['HARDNESS', 'FORCE', 'HARDNESS_AND_FORCE'].map(type => (
                <MenuItem key={type} value={type}>{type}</MenuItem>
              ))}
            </TextField>
          </Grid>

          <Grid item xs={12} sm={6}>
            <TextField
              select sx={{ minWidth: 250 }} name="frequencyRequired" label="Frequency (Months)"
              value={formData.frequencyRequired} onChange={handleChange}
            >
              {[3, 6, 12].map(value => (
                <MenuItem key={value} value={value}>{value} Months</MenuItem>
              ))}
            </TextField>
          </Grid>

          {/* Checkboxes */}
          {[
            { name: 'requiredCertificatesUnderNabl', label: 'NABL Required' },
            { name: 'calibrationAsPerStandard', label: 'Cal. As Per Standard' },
            { name: 'repairingRequired', label: 'Repairing Required' },
            { name: 'calibrationScopeAccepted', label: 'Scope Accepted' },
            { name: 'conformityStatementRequired', label: 'Conformity Statement' },
            { name: 'commercialTermsAccepted', label: 'Commercial Terms Accepted' },
            { name: 'nextCalibrationDateRequired', label: 'Next Calibration Date Required' }
          ].map(({ name, label }) => (
            <Grid item xs={12} sm={6} key={name}>
              <FormControlLabel
                control={<Checkbox checked={formData[name]} onChange={handleChange} name={name} />}
                label={label}
              />
            </Grid>
          ))}

          <Grid item xs={12}>
            <Button type="submit" variant="contained" color="primary">Submit</Button>
          </Grid>
        </Grid>
      </form>
    </Paper>
  );
};

export default ServiceRequestForm;
