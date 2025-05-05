import React, { useEffect, useState } from 'react';
import {
  TableContainer, Table, TableHead, TableRow,
  TableCell, TableBody, Paper, IconButton
} from '@mui/material';
import { Edit, Delete } from '@mui/icons-material';
import api from '../../utils/api';

const HardnessMachineTable = ({ onEdit, onDelete, refresh }) => {
  const [machines, setMachines] = useState([]);

  const fetchMachines = async () => {
    try {
      const response = await api.get('/client-machines');
      setMachines(response.data);
    } catch (err) {
      console.error('Error fetching machines:', err);
    }
  };

  useEffect(() => {
    fetchMachines();
  }, [refresh]);

  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Make</TableCell>
            <TableCell>Model</TableCell>
            <TableCell>Serial Number</TableCell>
            <TableCell>Identification Number</TableCell>
            <TableCell>Amount</TableCell>
            <TableCell>Machine</TableCell>
            <TableCell>Service Request</TableCell>
            <TableCell>Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {machines.map((machine) => (
            <TableRow key={machine.id}>
              <TableCell>{machine.make}</TableCell>
              <TableCell>{machine.model}</TableCell>
              <TableCell>{machine.serialNumber}</TableCell>
              <TableCell>{machine.identificationNumber}</TableCell>
              <TableCell>{machine.amount}</TableCell>
              <TableCell>{machine.machine?.name || machine.machineId}</TableCell>
              <TableCell>{machine.serviceRequest?.serviceRequestNumber || machine.serviceRequestId}</TableCell>
              <TableCell>
                <IconButton color="primary" onClick={() => onEdit(machine)}><Edit /></IconButton>
                <IconButton color="error" onClick={() => onDelete(machine.id)}><Delete /></IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default HardnessMachineTable;
