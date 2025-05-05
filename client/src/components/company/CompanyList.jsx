// src/components/company/CompanyList.jsx
import React from 'react';
import {
  Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Paper, IconButton
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

export default function CompanyList({ companies, onEdit, onDelete }) {
  return (
    <TableContainer component={Paper} sx={{ maxWidth: 900 }}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell>Address</TableCell>
            <TableCell>Contact Person</TableCell>
            <TableCell>Phone</TableCell>
            <TableCell>Email</TableCell>
            <TableCell>GST No</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {companies.map((c) => (
            <TableRow key={c.id}>
              <TableCell>{c.name}</TableCell>
              <TableCell>{c.address}</TableCell>
              <TableCell>{c.contactPerson}</TableCell>
              <TableCell>{c.phone}</TableCell>
              <TableCell>{c.email}</TableCell>
              <TableCell>{c.gstNo}</TableCell>
              <TableCell align="right">
                <IconButton onClick={() => onEdit(c)}><EditIcon /></IconButton>
                <IconButton onClick={() => onDelete(c.id)}><DeleteIcon /></IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
