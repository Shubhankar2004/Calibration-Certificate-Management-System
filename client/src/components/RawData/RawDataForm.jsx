// // RawDataForm.jsx

// RawDataForm.jsx

import React, { useEffect, useState } from 'react';
import {
  Box, Button, Grid, MenuItem, Paper, TextField, Typography, Snackbar, Alert
} from '@mui/material';
import api from '../../utils/api';

const inspectionStatuses = ['YES', 'NO'];
const equipmentStatuses = ['OK', 'NOT_OK'];

const inspectionFields = [
  'machineLeveled', 'indenterGoodCondition', 'cleanAnvil', 'vibrationFree',
  'dialGaugeZero', 'standardBlocksCalibrated', 'magnifierClean', 'tempHumidityOk',
  'lightingAdequate', 'loadSelectorWorking', 'indentorChecked', 'surfaceChecked',
  'testBlockFlatness', 'testBlockLabelVisible', 'dialGaugeCheck',
  'testBlockCalDueDateChecked'
];

const equipmentFields = []; // Add any if needed

export default function RawDataForm({ onSave, initialData = {} }) {
  const [clientMachines, setClientMachines] = useState([]);
  const [selectedClientMachineId, setSelectedClientMachineId] = useState(initialData?.clientMachineId || '');
  const [scalesWithBlocks, setScalesWithBlocks] = useState([]);
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'error' });

  const [formData, setFormData] = useState({
    calibrationType: '',
    serviceDate: '',
    clientMachineId: '',
    leastCount: '',
    ranges: '',
    dialGaugeNo: '',
    indenterNo: '',
    temperatureDetail: '',
    machineLeveled: '',
    indenterGoodCondition: '',
    cleanAnvil: '',
    vibrationFree: '',
    dialGaugeZero: '',
    standardBlocksCalibrated: '',
    magnifierClean: '',
    tempHumidityOk: '',
    lightingAdequate: '',
    loadSelectorWorking: '',
    indentorChecked: '',
    surfaceChecked: '',
    testBlockFlatness: '',
    testBlockLabelVisible: '',
    dialGaugeCheck: '',
    testBlockCalDueDateChecked: '',
    ...initialData
  });

  const [formErrors, setFormErrors] = useState({});

  useEffect(() => {
    api.get('/client-machines')
      .then(res => setClientMachines(res.data))
      .catch(err => console.error('Error fetching client machines:', err));
  }, []);

  useEffect(() => {
    if (selectedClientMachineId) {
      api.get(`/client-machines/${selectedClientMachineId}`)
        .then(res => {
          const selectedScales = res.data.selectedScales || res.data.selectedScaleIds || [];
          const fetchBlocks = selectedScales.map((scale) => {
            const scaleId = scale.id || scale;
            return api.get(`/test-blocks/by-scale/${scaleId}`)
              .then(blocks => ({
                scaleId,
                scaleName: scale.scaleName || `Scale ${scaleId}`,
                testBlocks: blocks.data.map(tb => ({
                  ...tb,
                  readings: Array(5).fill(''),
                  average: ''
                }))
              }));
          });
          Promise.all(fetchBlocks).then(setScalesWithBlocks);
        });
    } else {
      setScalesWithBlocks([]);
    }
  }, [selectedClientMachineId]);

  const handleReadingChange = (scaleId, testBlockId, index, value) => {
    setScalesWithBlocks(prev =>
      prev.map(scale =>
        scale.scaleId === scaleId ? {
          ...scale,
          testBlocks: scale.testBlocks.map(block =>
            block.id === testBlockId ? {
              ...block,
              readings: block.readings.map((r, i) => (i === index ? value : r)),
              average: calculateAverage(block.readings.map((r, i) => i === index ? parseFloat(value || 0) : parseFloat(r || 0)))
            } : block
          )
        } : scale
      )
    );
  };

  const calculateAverage = (readings) => {
    const nums = readings.map(parseFloat).filter(n => !isNaN(n));
    if (nums.length === 5) {
      const avg = nums.reduce((a, b) => a + b, 0) / 5;
      return parseFloat(avg.toFixed(2));
    }
    return '';
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    setFormErrors(prev => ({ ...prev, [name]: '' }));
  };

  const validateForm = () => {
    const errors = {};
    Object.keys(formData).forEach(field => {
      if (formData[field] === '' || formData[field] === null || formData[field] === undefined) {
        errors[field] = 'This field is required';
      }
    });

    if (!selectedClientMachineId) {
      errors.clientMachineId = 'Client Machine is required';
    }

    setFormErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateForm()) {
      setSnackbar({ open: true, message: 'Please fill all required fields.', severity: 'error' });
      return;
    }

    if (clientMachines.length === 0) {
      setSnackbar({ open: true, message: 'Client machines not loaded yet.', severity: 'error' });
      return;
    }

    const testBlocksUsed = [];
    const observedReadings = [];

    for (const scale of scalesWithBlocks) {
      for (const block of scale.testBlocks) {
        if (block.readings.some(r => r === '')) {
          setSnackbar({ open: true, message: 'All readings must be filled.', severity: 'error' });
          return;
        }
        testBlocksUsed.push(block.id);
        observedReadings.push({
          scaleId: scale.scaleId,
          testBlockId: block.id,
          readings: block.readings.map(Number),
          average: block.average
        });
      }
    }

    const payload = {
      ...formData,
      clientMachineId: selectedClientMachineId,
      rawData: {
        testBlocksUsed,
        observedReadings
      }
    };

    try {
      if (initialData?.id) {
        await api.put(`/raw-data/${initialData.id}`, payload);
        setSnackbar({ open: true, message: 'Raw data updated!', severity: 'success' });
      } else {
        const res = await api.post('/raw-data', payload);
        if (res.data === null) {
          setSnackbar({ open: true, message: `${formData.selectedClientMachineId} already has raw data`, severity: 'warning' });
          return;
        }
        setSnackbar({ open: true, message: 'Raw data submitted!', severity: 'success' });
      }
      onSave();
    } catch (error) {
      console.error('Error submitting raw data:', error.response?.data || error.message);
      setSnackbar({ open: true, message: 'Submission failed.', severity: 'error' });
    }
  };

  return (
    <Paper elevation={3} sx={{ padding: 3 }}>
      <Typography variant="h6" gutterBottom>Enter Raw Data</Typography>
      <Grid container spacing={2}>
        <Grid item xs={12} sm={6}>
          <TextField
            select
            sx={{ minWidth: 250 }}
            label="Client Machine"
            value={selectedClientMachineId || ""}
            onChange={(e) => {
              const value = e.target.value;
              setSelectedClientMachineId(value);
              setFormData(prev => ({ ...prev, clientMachineId: value }));
              setFormErrors(prev => ({ ...prev, clientMachineId: '' }));
            }}
            error={!!formErrors.clientMachineId}
            helperText={formErrors.clientMachineId}
          >
            {clientMachines.map(cm => (
              <MenuItem key={cm.id} value={cm.id}>
                {cm.name || `Machine ${cm.id}`}
              </MenuItem>
            ))}
          </TextField>
        </Grid>

        {Object.entries({
          calibrationType: "Calibration Type",
          serviceDate: "Service Date",
          leastCount: "Least Count",
          ranges: "Ranges",
          dialGaugeNo: "Dial Gauge No",
          indenterNo: "Indenter No",
          temperatureDetail: "Temperature Detail",
          machineLeveled: "Machine Leveled",
          indenterGoodCondition: "Indenter Good Condition",
          cleanAnvil: "Clean Anvil",
          vibrationFree: "Vibration Free",
          dialGaugeZero: "Dial Gauge Zero",
          standardBlocksCalibrated: "Standard Blocks Calibrated",
          magnifierClean: "Magnifier Clean",
          tempHumidityOk: "Temp & Humidity OK",
          lightingAdequate: "Lighting Adequate",
          loadSelectorWorking: "Load Selector Working",
          indentorChecked: "Indentor Checked",
          surfaceChecked: "Surface Checked",
          testBlockFlatness: "Test Block Flatness",
          testBlockLabelVisible: "Test Block Label Visible",
          dialGaugeCheck: "Dial Gauge Check",
          testBlockCalDueDateChecked: "Test Block Due Date Checked",
        }).map(([key, label]) => (
          <Grid item xs={6} sm={3} key={key}>
            <TextField
              select={inspectionFields.includes(key) || equipmentFields.includes(key)}
              type={key === 'serviceDate' ? 'date' : 'text'}
              sx={{ minWidth: 250 }}
              label={label}
              name={key}
              value={formData[key]}
              onChange={handleChange}
              InputLabelProps={key === 'serviceDate' ? { shrink: true } : undefined}
              error={!!formErrors[key]}
              helperText={formErrors[key]}
            >
              {inspectionFields.includes(key) &&
                inspectionStatuses.map(opt => (
                  <MenuItem key={opt} value={opt}>{opt}</MenuItem>
                ))}
              {equipmentFields.includes(key) &&
                equipmentStatuses.map(opt => (
                  <MenuItem key={opt} value={opt}>{opt}</MenuItem>
                ))}
            </TextField>
          </Grid>
        ))}

        {scalesWithBlocks.map(scale => (
          <Grid item xs={12} key={scale.scaleId}>
            <Typography variant="subtitle1">Scale: {scale.scaleName}</Typography>
            {scale.testBlocks.map((block) => (
              <Box key={block.id} sx={{ mb: 2 }}>
                <Typography variant="body2">Test Block: {block.name} (ID: {block.id})</Typography>
                <Grid container spacing={1}>
                  {block.readings.map((reading, rIndex) => (
                    <Grid item xs={2} key={rIndex}>
                      <TextField
                        label={`R${rIndex + 1}`}
                        value={reading}
                        onChange={(e) => handleReadingChange(scale.scaleId, block.id, rIndex, e.target.value)}
                        sx={{ minWidth: 250 }}
                      />
                    </Grid>
                  ))}
                  <Grid item xs={2}>
                    <TextField label="Avg" value={block.average} disabled fullWidth />
                  </Grid>
                </Grid>
              </Box>
            ))}
          </Grid>
        ))}

        <Grid item xs={12}>
          <Button variant="contained" color="primary" onClick={handleSubmit} sx={{ minWidth: 250 }}>
            {initialData?.id ? 'Update' : 'Submit'}
          </Button>
        </Grid>
      </Grid>

      <Snackbar
        open={snackbar.open}
        autoHideDuration={3000}
        onClose={() => setSnackbar({ ...snackbar, open: false })}
        anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
      >
        <Alert severity={snackbar.severity} onClose={() => setSnackbar({ ...snackbar, open: false })}>
          {snackbar.message}
        </Alert>
      </Snackbar>
    </Paper>
  );
}



// import React, { useEffect, useState } from 'react';
// import {
//   Box, Button, Grid, MenuItem, Paper, TextField, Typography, Snackbar, Alert
// } from '@mui/material';
// import api from '../../utils/api';

// const inspectionStatuses = ['YES', 'NO'];
// const equipmentStatuses = ['OK', 'NOT_OK'];

// export default function RawDataForm({ onSave, initialData = {} }) {
//   const [clientMachines, setClientMachines] = useState([]);
//   const [selectedClientMachineId, setSelectedClientMachineId] = useState(initialData?.clientMachineId || '');
//   const [scalesWithBlocks, setScalesWithBlocks] = useState([]);
//   const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'error' });

//   const [formData, setFormData] = useState({
//     calibrationType: '',
//     serviceDate: '',
//     clientMachineId: '',
//     leastCount: '',
//     ranges: '',
//     dialGaugeNo: '',
//     indenterNo: '',
//     temperatureDetail: '',
//     machineLeveled: '',
//     indenterGoodCondition: '',
//     cleanAnvil: '',
//     vibrationFree: '',
//     dialGaugeZero: '',
//     standardBlocksCalibrated: '',
//     magnifierClean: '',
//     tempHumidityOk: '',
//     lightingAdequate: '',
//     loadSelectorWorking: '',
//     indentorChecked: '',
//     surfaceChecked: '',
//     testBlockFlatness: '',
//     testBlockLabelVisible: '',
//     dialGaugeCheck: '',
//     testBlockCalDueDateChecked: '',
//     ...initialData
//   });

//   const [formErrors, setFormErrors] = useState({});

//   useEffect(() => {
//     api.get('/client-machines')
//       .then(res => setClientMachines(res.data))
//       .catch(err => console.error('Error fetching client machines:', err));
//   }, []);

//   useEffect(() => {
//     if (selectedClientMachineId) {
//       api.get(`/client-machines/${selectedClientMachineId}`)
//         .then(res => {
//           const selectedScales = res.data.selectedScales || res.data.selectedScaleIds || [];
//           const fetchBlocks = selectedScales.map((scale) => {
//             const scaleId = scale.id || scale;
//             return api.get(`/test-blocks/by-scale/${scaleId}`)
//               .then(blocks => ({
//                 scaleId,
//                 scaleName: scale.scaleName || `Scale ${scaleId}`,
//                 testBlocks: blocks.data.map(tb => ({
//                   ...tb,
//                   readings: Array(5).fill(''),
//                   average: ''
//                 }))
//               }));
//           });
//           Promise.all(fetchBlocks).then(setScalesWithBlocks);
//         });
//     } else {
//       setScalesWithBlocks([]);
//     }
//   }, [selectedClientMachineId]);

//   const handleReadingChange = (scaleId, testBlockId, index, value) => {
//     setScalesWithBlocks(prev =>
//       prev.map(scale =>
//         scale.scaleId === scaleId ? {
//           ...scale,
//           testBlocks: scale.testBlocks.map(block =>
//             block.id === testBlockId ? {
//               ...block,
//               readings: block.readings.map((r, i) => (i === index ? value : r)),
//               average: calculateAverage(block.readings.map((r, i) => i === index ? parseFloat(value || 0) : parseFloat(r || 0)))
//             } : block
//           )
//         } : scale
//       )
//     );
//   };

//   const calculateAverage = (readings) => {
//     const nums = readings.map(parseFloat).filter(n => !isNaN(n));
//     if (nums.length === 5) {
//       const avg = nums.reduce((a, b) => a + b, 0) / 5;
//       return parseFloat(avg.toFixed(2));
//     }
//     return '';
//   };

//   const handleChange = (e) => {
//     const { name, value } = e.target;
//     setFormData(prev => ({ ...prev, [name]: value }));
//     setFormErrors(prev => ({ ...prev, [name]: '' }));
//   };

//   const validateForm = () => {
//     const errors = {};
//     const requiredFields = Object.keys(formData);

//     requiredFields.forEach(field => {
//       if (!formData[field]) {
//         errors[field] = 'This field is required';
//       }
//     });

//     if (!selectedClientMachineId) {
//       errors.clientMachineId = 'Client Machine is required';
//     }

//     setFormErrors(errors);
//     return Object.keys(errors).length === 0;
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();

//     if (!validateForm()) {
//       setSnackbar({ open: true, message: 'Please fill all required fields.', severity: 'error' });
//       return;
//     }

//     if (clientMachines.length === 0) {
//       setSnackbar({ open: true, message: 'Client machines not loaded yet.', severity: 'error' });
//       return;
//     }

//     const testBlocksUsed = [];
//     const observedReadings = [];

//     for (const scale of scalesWithBlocks) {
//       for (const block of scale.testBlocks) {
//         if (block.readings.some(r => r === '')) {
//           setSnackbar({ open: true, message: 'All readings must be filled.', severity: 'error' });
//           return;
//         }
//         testBlocksUsed.push(block.id);
//         observedReadings.push({
//           scaleId: scale.scaleId,
//           testBlockId: block.id,
//           readings: block.readings.map(Number),
//           average: block.average
//         });
//       }
//     }

//     const payload = {
//       ...formData,
//       clientMachineId: selectedClientMachineId,
//       rawData: {
//         testBlocksUsed,
//         observedReadings
//       }
//     };

//     try {
//       if (initialData?.id) {
//         await api.put(`/raw-data/${initialData.id}`, payload);
//         setSnackbar({ open: true, message: 'Raw data updated!', severity: 'success' });
//       } else {
//         const res = await api.post('/raw-data', payload);
//         if (res.data === null) {
//           setSnackbar({ open: true, message: `${formData.selectedClientMachineId} already has raw data`, severity: 'warning' });
//           return;
//         }
//         console.log(res, payload);
//         setSnackbar({ open: true, message: 'Raw data submitted!', severity: 'success' });
//       }
//       onSave();
//     } catch (error) {
//       console.error('Error submitting raw data:', error.response?.data || error.message);
//       setSnackbar({ open: true, message: 'Submission failed.', severity: 'error' });
//     }
//   };

//   return (
//     <Paper elevation={3} sx={{ padding: 3 }}>
//       <Typography variant="h6" gutterBottom>Enter Raw Data</Typography>
//       <Grid container spacing={2}>
//         <Grid item xs={12} sm={6}>
//           <TextField
//             select
//             sx={{ minWidth: 250 }}
//             label="Client Machine"
//             value={clientMachines.find(cm => cm.id === selectedClientMachineId) ? selectedClientMachineId : ""}
//             onChange={(e) => {
//               const value = e.target.value;
//               setSelectedClientMachineId(value);
//               setFormData(prev => ({ ...prev, clientMachineId: value }));
//               setFormErrors(prev => ({ ...prev, clientMachineId: '' }));
//             }}
//             error={!!formErrors.clientMachineId}
//             helperText={formErrors.clientMachineId}
//           >
//             {clientMachines.map(cm => (
//               <MenuItem key={cm.id} value={cm.id}>
//                 {cm.name || `Machine ${cm.id}`}
//               </MenuItem>
//             ))}
//           </TextField>
//         </Grid>

//         {Object.entries({
//           calibrationType: "Calibration Type",
//           serviceDate: "Service Date",
//           leastCount: "Least Count",
//           ranges: "Ranges",
//           dialGaugeNo: "Dial Gauge No",
//           indenterNo: "Indenter No",
//           temperatureDetail: "Temperature Detail",
//           machineLeveled: "Machine Leveled",
//           indenterGoodCondition: "Indenter Good Condition",
//           cleanAnvil: "Clean Anvil",
//           vibrationFree: "Vibration Free",
//           dialGaugeZero: "Dial Gauge Zero",
//           standardBlocksCalibrated: "Standard Blocks Calibrated",
//           magnifierClean: "Magnifier Clean",
//           tempHumidityOk: "Temp & Humidity OK",
//           lightingAdequate: "Lighting Adequate",
//           loadSelectorWorking: "Load Selector Working",
//           indentorChecked: "Indentor Checked",
//           surfaceChecked: "Surface Checked",
//           testBlockFlatness: "Test Block Flatness",
//           testBlockLabelVisible: "Test Block Label Visible",
//           dialGaugeCheck: "Dial Gauge Check",
//           testBlockCalDueDateChecked: "Test Block Due Date Checked",
//         }).map(([key, label]) => (
//           <Grid item xs={6} sm={3} key={key}>
//             <TextField
//               select={key !== 'serviceDate' && (inspectionStatuses.includes(formData[key]) || equipmentStatuses.includes(formData[key]))}
//               type={key === 'serviceDate' ? 'date' : 'text'}
//               sx={{ minWidth: 250 }}
//               label={label}
//               name={key}
//               value={formData[key]}
//               onChange={handleChange}
//               InputLabelProps={key === 'serviceDate' ? { shrink: true } : undefined}
//               error={!!formErrors[key]}
//               helperText={formErrors[key]}
//             >
//               {(inspectionStatuses.includes(formData[key]) ? inspectionStatuses :
//                 equipmentStatuses.includes(formData[key]) ? equipmentStatuses : [])
//                 .map(opt => (
//                   <MenuItem key={opt} value={opt}>{opt}</MenuItem>
//                 ))}
//             </TextField>
//           </Grid>
//         ))}

//         {scalesWithBlocks.map(scale => (
//           <Grid item xs={12} key={scale.scaleId}>
//             <Typography variant="subtitle1">Scale: {scale.scaleName}</Typography>
//             {scale.testBlocks.map((block) => (
//               <Box key={block.id} sx={{ mb: 2 }}>
//                 <Typography variant="body2">Test Block: {block.name} (ID: {block.id})</Typography>
//                 <Grid container spacing={1}>
//                   {block.readings.map((reading, rIndex) => (
//                     <Grid item xs={2} key={rIndex}>
//                       <TextField
//                         label={`R${rIndex + 1}`}
//                         value={reading}
//                         onChange={(e) => handleReadingChange(scale.scaleId, block.id, rIndex, e.target.value)}
//                         sx={{ minWidth: 250 }}
//                       />
//                     </Grid>
//                   ))}
//                   <Grid item xs={2}>
//                     <TextField label="Avg" value={block.average} disabled fullWidth />
//                   </Grid>
//                 </Grid>
//               </Box>
//             ))}
//           </Grid>
//         ))}

//         <Grid item xs={12}>
//           <Button variant="contained" color="primary" onClick={handleSubmit} sx={{ minWidth: 250 }}>
//             {initialData?.id ? 'Update' : 'Submit'}
//           </Button>
//         </Grid>
//       </Grid>

//       <Snackbar
//         open={snackbar.open}
//         autoHideDuration={3000}
//         onClose={() => setSnackbar({ ...snackbar, open: false })}
//         anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
//       >
//         <Alert severity={snackbar.severity} onClose={() => setSnackbar({ ...snackbar, open: false })}>
//           {snackbar.message}
//         </Alert>
//       </Snackbar>
//     </Paper>
//   );
// }
