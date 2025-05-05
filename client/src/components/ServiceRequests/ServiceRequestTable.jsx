import React from 'react';
import {
  TableContainer,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Paper,
  IconButton
} from '@mui/material';
import { Edit, Delete } from '@mui/icons-material';

const ServiceRequestTable = ({ data, onEdit, onDelete }) => {
  return (
    <Paper elevation={3} style={{ padding: '20px', marginBottom: '20px' }}>
      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Request No.</TableCell>
              <TableCell>Request Date</TableCell>
              <TableCell>Company ID</TableCell>
              <TableCell>Contact Person</TableCell>
              <TableCell>Calibration Type</TableCell>
              <TableCell>Frequency</TableCell>
              <TableCell>Status</TableCell>
              <TableCell>Engineer ID</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {data.map((row, idx) => (
              <TableRow key={row.id || idx}>
                <TableCell>{row.serviceRequestNumber}</TableCell>
                <TableCell>{row.requestDate}</TableCell>
                <TableCell>{row.companyId}</TableCell>
                <TableCell>{row.contactPerson}</TableCell>
                <TableCell>{row.calibrationType}</TableCell>
                <TableCell>{row.frequencyRequired} Months</TableCell>
                <TableCell>{row.status}</TableCell>
                <TableCell>{row.engineerId}</TableCell>
                <TableCell>
                  <IconButton onClick={() => onEdit(row)}><Edit color="primary" /></IconButton>
                  <IconButton onClick={() => onDelete(row.id)}><Delete color="error" /></IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Paper>
  );
};

export default ServiceRequestTable;
