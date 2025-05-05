import React from 'react';
import {
  Paper, Table, TableHead, TableBody, TableRow, TableCell,
  TableContainer, Typography, Button
} from '@mui/material';

export default function ScalesList({ scales, machines, onEdit, onDelete }) {
  return (
    <Paper sx={{ p: 2 }}>
      <Typography variant="h6" gutterBottom>Scales List</Typography>
      <TableContainer>
        <Table size="small">
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Capacity</TableCell>
              <TableCell>Type</TableCell>
              <TableCell>Uncertainty</TableCell>
              <TableCell>Machine</TableCell>
              <TableCell align="right">Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {scales.length > 0 ? (
              scales.map((scale) => {
                const machine = machines.find(m => m.id === scale.machineId);
                return (
                  <TableRow key={scale.id}>
                    <TableCell>{scale.id}</TableCell>
                    <TableCell>{scale.scaleName}</TableCell>
                    <TableCell>{scale.loadCapacity}</TableCell>
                    <TableCell>{scale.verificationType}</TableCell>
                    <TableCell>{scale.uncertainty}</TableCell>
                    <TableCell>{machine?.name}</TableCell>
                    <TableCell align="right">
                      <Button size="small" onClick={() => onEdit(scale)}>Edit</Button>
                      <Button size="small" color="error" onClick={() => onDelete(scale.id)}>Delete</Button>
                    </TableCell>
                  </TableRow>
                );
              })
            ) : (
              <TableRow>
                <TableCell colSpan={7} align="center">
                  No scales available.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
    </Paper>
  );
}
