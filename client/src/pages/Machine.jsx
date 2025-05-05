import React, { useState, useEffect } from 'react';
import api from '../utils/api';  // Import the API utility
import Navbar from '../components/navbar/Navbar';
import Sidebar from '../components/sidebar/Sidebar';
import MachineForm from '../components/machine/MachineForm';
import MachineList from '../components/machine/MachineList';
import ScalesList from '../components/scales/ScalesList';
import { useNavigate } from 'react-router-dom';

const Machine = () => {
  const [machines, setMachines] = useState([]);
  const [scales, setScales] = useState([]);
  const [selectedMachine, setSelectedMachine] = useState(null);
  const [selectedScale, setSelectedScale] = useState(null);
  const [drawerOpen, setDrawerOpen] = useState(true);

  const navigate = useNavigate();

  // Fetch machines and scales data
  useEffect(() => {
    const fetchMachines = async () => {
      try {
        const response = await api.get('/machines');
        setMachines(response.data);
      } catch (error) {
        console.error('Error fetching machines:', error);
        alert('There was an error fetching the machines data.');
      }
    };

    const fetchScales = async () => {
      try {
        const response = await api.get('/scales');
        setScales(response.data);
      } catch (error) {
        console.error('Error fetching scales:', error);
        alert('There was an error fetching the scales data.');
      }
    };

    fetchMachines();
    fetchScales();
  }, []);

  // Handle add or update machine
  const handleAddOrUpdateMachine = (machine) => {
    if (machine.id) {
      api.put(`/machines/${machine.id}`, machine).then((response) => {
        setMachines(prev => prev.map(m => m.id === machine.id ? response.data : m));
      }).catch(error => console.error('Error updating machine:', error));
    } else {
      api.post('/machines', machine).then((response) => {
        setMachines(prev => [...prev, response.data]);
      }).catch(error => console.error('Error adding machine:', error));
    }
  };

  // Handle delete machine
  const handleDeleteMachine = (id) => {
    api.delete(`/machines/${id}`).then(() => {
      setMachines(prev => prev.filter(m => m.id !== id));
    }).catch(error => console.error('Error deleting machine:', error));
  };

  // Handle add or update scale
  const handleAddOrUpdateScale = (scale) => {
    if (scale.id) {
      // api.put(`/scales/${scale.id}`, scale).then((response) => {
      //   setScales(prev => prev.map(s => s.id === scale.id ? response.data : s));
      // }).catch(error => console.error('Error updating scale:', error));
      navigate('/scales');
    } else {
      api.post('/scales', scale).then((response) => {
        setScales(prev => [...prev, response.data]);
      }).catch(error => console.error('Error adding scale:', error));
    }
  };

  // Handle delete scale
  const handleDeleteScale = (id) => {
    navigate('/scales');
  };

  const toggleDrawer = () => {
    setDrawerOpen(prev => !prev);
  };

  return (
    <>
      <Navbar toggleDrawer={toggleDrawer} />
      <div style={{ display: 'flex', height: 'calc(100vh - 64px)', marginTop: '64px' }}>
        <Sidebar open={drawerOpen} />
        <div style={{ padding: '20px', flex: 1, overflowY: 'auto' }}>
          <h1>Manage Machines and Scales</h1>
          <MachineForm onSubmit={handleAddOrUpdateMachine} initialData={selectedMachine} />
          <MachineList machines={machines} onEdit={setSelectedMachine} onDelete={handleDeleteMachine} />
          <ScalesList scales={scales} machines={machines} onEdit={handleAddOrUpdateScale} onDelete={handleDeleteScale} />
        </div>
      </div>
    </>
  );
};

export default Machine;
