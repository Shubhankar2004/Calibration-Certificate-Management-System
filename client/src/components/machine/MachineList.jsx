import React from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
  Typography,
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

export default function MachineList({ machines = [], onEdit, onDelete }) {
  return (
    <TableContainer component={Paper} sx={{ maxWidth: 1000 }}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Machine Name</TableCell>
            <TableCell></TableCell>
            <TableCell align="right" sx={{ pr: 5 }}>Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {machines.map((machine) => (
            <TableRow key={machine.id}>
              <TableCell>{machine.name}</TableCell>
              <TableCell>
                {machine.scales && machine.scales.length > 0 &&
                  machine.scales.map((scale, idx) => (
                    <Typography key={idx} variant="body2">
                      • {scale.scaleName} - {scale.loadCapacity}kg ({scale.verificationType})
                    </Typography>
                  ))}
              </TableCell>
              <TableCell align="right" sx={{ pr: 5 }}>
                <IconButton onClick={() => onEdit(machine)}>
                  <EditIcon />
                </IconButton>
                
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
