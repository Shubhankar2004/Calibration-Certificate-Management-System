// src/routes/AppRoutes.jsx
import React from 'react';
import { Routes, Route } from 'react-router-dom';

import AdminDashboard from '../pages/AdminDashboard';
import MachinePage from '../pages/Machine';
import Companies from '../pages/Companies';
import ServiceRequestForm from '../pages/ServiceRequestPage';
import ScalesPage from '../pages/ScalesPage';
import RowData from '../pages/RowData';
import Certificates from '../pages/Certificates';
import Employee from '../pages/Employee';
import TestBlock from '../pages/TestBlock';
import LoginPage from '../pages/LoginPage';
import HardnessMachinePage from '../pages/HardnessMachinePage';

import ProtectedRoute from '../components/ProtectedRoute'; // import it

const AppRoutes = () => {
  return (
    <Routes>
      {/* Public route */}
      <Route path="/" element={<LoginPage />} />

      {/*  Protected routes below */}
      <Route
        path="/adminDashboard"
        element={
          <ProtectedRoute>
            <AdminDashboard />
          </ProtectedRoute>
        }
      />
      <Route
        path="/machines"
        element={
          <ProtectedRoute>
            <MachinePage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/companies"
        element={
          <ProtectedRoute>
            <Companies />
          </ProtectedRoute>
        }
      />
      <Route
        path="/service-request-form"
        element={
          <ProtectedRoute>
            <ServiceRequestForm />
          </ProtectedRoute>
        }
      />
      <Route
        path="/scales"
        element={
          <ProtectedRoute>
            <ScalesPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/rawData"
        element={
          <ProtectedRoute>
            <RowData />
          </ProtectedRoute>
        }
      />
      <Route
        path="/certificates"
        element={
          <ProtectedRoute>
            <Certificates />
          </ProtectedRoute>
        }
      />
      <Route
        path="/employee"
        element={
          <ProtectedRoute>
            <Employee />
          </ProtectedRoute>
        }
      />
      <Route
        path="/testBlocks"
        element={
          <ProtectedRoute>
            <TestBlock />
          </ProtectedRoute>
        }
      />
      <Route
        path="/client-machines"
        element={
          <ProtectedRoute>
            <HardnessMachinePage />
          </ProtectedRoute>
        }
      />
    </Routes>
  );
};

export default AppRoutes;
