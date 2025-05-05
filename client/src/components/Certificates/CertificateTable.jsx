import React, { useEffect, useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton } from '@mui/material';
import { Edit, FileDownload } from '@mui/icons-material';
import api from '../../utils/api';

const CertificateTable = ({ onEdit, refresh }) => {
  const [certificates, setCertificates] = useState([]);

  useEffect(() => {
    api.get('/certificates').then((res) => setCertificates(res.data));
  }, [refresh]);

  const handleDownload = async (id) => {
    try {
      await api.post(`/certificates/${id}/generate`);
      const response = await api.get(`/certificates/${id}/download`, { responseType: 'blob' });
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `certificate-${id}.xlsx`);
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    } catch (error) {
      console.error('Error downloading certificate:', error);
    }
  };

  return (
    <TableContainer component={Paper} sx={{ mt: 3 }}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Certificate Number</TableCell>
            <TableCell>Issue Date</TableCell>
            <TableCell>Expiry Date</TableCell>
            <TableCell>Status</TableCell>
            <TableCell>Client Machine ID</TableCell>
            <TableCell>Service Request ID</TableCell>
            <TableCell>Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {certificates.map((cert) => (
            <TableRow key={cert.id}>
              <TableCell>{cert.certificateNumber}</TableCell>
              <TableCell>{cert.issueDate}</TableCell>
              <TableCell>{cert.expiryDate}</TableCell>
              <TableCell>{cert.certificateStatus}</TableCell>
              <TableCell>{cert.clientMachineId}</TableCell>
              <TableCell>{cert.serviceRequestId}</TableCell>
              <TableCell>
                <IconButton onClick={() => onEdit(cert)}><Edit /></IconButton>
                <IconButton onClick={() => handleDownload(cert.id)}><FileDownload /></IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default CertificateTable;
