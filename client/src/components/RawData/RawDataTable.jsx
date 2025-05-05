import React, { useEffect, useState } from 'react';
import {
  Box, Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Paper, IconButton, Dialog,
  DialogTitle, DialogContent
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import { Edit, Delete } from '@mui/icons-material';
import api from '../../utils/api';
import RawDataForm from './RawDataForm'; // Reuse your form

export default function RawDataTable({ refreshFlag }) {
  const [rows, setRows] = useState([]);
  const [editData, setEditData] = useState(null);
  const [open, setOpen] = useState(false);

  const fetchData = () => {
    api.get('/raw-data')
      .then((res) => setRows(res.data))
      .catch((err) => console.error('Error fetching RawData:', err));
  };

  useEffect(() => {
    fetchData();
  }, [refreshFlag]);

  const handleDelete = async (id) => {
    try {
      await api.delete(`/raw-data/${id}`);
      fetchData();
    } catch (err) {
      console.error('Error deleting data:', err);
    }
  };

  const handleEdit = (row) => {
    setEditData(row);
    setOpen(true);
  };

  const handleFormSave = () => {
    setOpen(false);
    setEditData(null);
    fetchData(); // Refresh after edit
  };

  return (
   
    <Box mt={4}>
       <Paper elevation={3} sx={{ p: 3, mb: 3 }}>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Calibration Type</TableCell>
              <TableCell>Service Date</TableCell>
              <TableCell>Client Machine</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.map((row) => (
              <TableRow key={row.id}>
                <TableCell>{row.calibrationType}</TableCell>
                <TableCell>{row.serviceDate}</TableCell>
                <TableCell>{row.clientMachine?.name || row.clientMachineId}</TableCell>
                <TableCell>
                  <IconButton color="primary" onClick={() => handleEdit(row)}><Edit /></IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {/* Edit Modal */}
      <Dialog open={open} onClose={() => setOpen(false)} maxWidth="md" fullWidth>
  <DialogTitle sx={{ m: 0, p: 2 }}>
    Edit Raw Data
    <IconButton
      aria-label="close"
      onClick={() => setOpen(false)}
      sx={{
        position: 'absolute',
        right: 8,
        top: 8,
        color: (theme) => theme.palette.grey[500],
      }}
    >
      <CloseIcon />
    </IconButton>
  </DialogTitle>
  <DialogContent dividers>
    <RawDataForm initialData={editData} onSave={handleFormSave} />
  </DialogContent>
</Dialog>

      </Paper>
    </Box>
    
  );
}
