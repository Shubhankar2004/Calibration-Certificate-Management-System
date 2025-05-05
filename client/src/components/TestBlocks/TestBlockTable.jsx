import React from 'react';
import {
  Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, IconButton
} from '@mui/material';
import { Edit, Delete } from '@mui/icons-material';

export default function TestBlockTable({ testBlocks, onEdit, onDelete }) {
  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            {['Name', 'Make', 'ID No', 'Range', 'Property', 'Uncertainty', 'Non-Uniformity', 'Calibrated By', 'Scale Id', 'Actions'].map(header => (
              <TableCell key={header}>{header}</TableCell>
            ))}
          </TableRow>
        </TableHead>
        <TableBody>
          {testBlocks.map(block => (
            <TableRow key={block.id}>
              <TableCell>{block.name}</TableCell>
              <TableCell>{block.make}</TableCell>
              <TableCell>{block.identificationNumber}</TableCell>
              <TableCell>{block.rangeValue}</TableCell>
              <TableCell>{block.property}</TableCell>
              <TableCell>{block.measurementUncertainty}</TableCell>
              <TableCell>{block.nonUniformity}</TableCell>
              <TableCell>{block.calibratedBy || 'N/A'}</TableCell>
              <TableCell>{block.scaleId}</TableCell>
              <TableCell>
                <IconButton onClick={() => onEdit(block)}><Edit /></IconButton>
                <IconButton onClick={() => onDelete(block.id)}><Delete /></IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
