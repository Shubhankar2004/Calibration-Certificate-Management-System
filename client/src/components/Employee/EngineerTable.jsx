import React from 'react';
import {
  Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, IconButton, Paper
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

const EngineerTable = ({ engineers, onEdit, onDelete }) => {
  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell>Email</TableCell>
            <TableCell>Phone</TableCell>
            <TableCell>Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {engineers.map((eng) => (
            <TableRow key={eng.id}>
              <TableCell>{eng.name}</TableCell>
              <TableCell>{eng.email}</TableCell>
              <TableCell>{eng.phone}</TableCell>
              <TableCell>
                <IconButton onClick={() => onEdit(eng)}>
                  <EditIcon color="primary" />
                </IconButton>
                <IconButton onClick={() => onDelete(eng.id)}>
                  <DeleteIcon color="error" />
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default EngineerTable;
