import React, { useEffect, useState } from 'react';
import {
  Avatar, Box, Button, Container, CssBaseline, Grid, TextField, Typography, Paper
} from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import axios from 'axios';
import { motion, AnimatePresence } from 'framer-motion';

export default function LoginPage() {
  const [formData, setFormData] = useState({ username: '', password: '' });
  const [error, setError] = useState('');
  const [showWelcome, setShowWelcome] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => setShowWelcome(false), 5000);
    return () => clearTimeout(timer);
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    try {
      const res = await axios.post('http://localhost:8080/auth/login', {
        username: formData.username,
        password: formData.password,
      });

      const token = res.data;
      sessionStorage.setItem('token', token);
      window.location.href = '/adminDashboard';
    } catch (err) {
      setError('Invalid email or password');
    }
  };

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />

      <AnimatePresence>
        {showWelcome ? (
          <motion.div
            initial={{ y: '-100vh', opacity: 0 }}
            animate={{ y: 0, opacity: 1 }}
            exit={{ y: '100vh', opacity: 0 }}
            transition={{ duration: 1.2, delay: 0.2 }}
            style={{ textAlign: 'center', position: 'absolute', top: '30%', left: 0, right: 0 }}
          >
            <Typography variant="h3" sx={{ fontWeight: 'bold', color: 'primary.main' }}>
              Welcome to
            </Typography>
            <Typography variant="h4" color="text.secondary">
              Calibration Management System
            </Typography>
            <Typography variant="h5" color="text.secondary">
              by KCube Software Pvt. Ltd.
            </Typography>
          </motion.div>
        ) : (
          <motion.div
            initial={{ opacity: 0, scale: 0.9 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.6 }}
          >
            <Paper elevation={3} sx={{ mt: 10, p: 4, borderRadius: 3 }}>
              <Box display="flex" flexDirection="column" alignItems="center">
                <Avatar sx={{ m: 1, bgcolor: 'primary.main' }}>
                  <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">Login</Typography>
                <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
                  <TextField
                    margin="normal"
                    fullWidth
                    label="User Name"
                    name="username"
                    autoComplete="text"
                    value={formData.username}
                    onChange={handleChange}
                  />
                  <TextField
                    margin="normal"
                    fullWidth
                    label="Password"
                    name="password"
                    type="password"
                    autoComplete="current-password"
                    value={formData.password}
                    onChange={handleChange}
                  />
                  {error && (
                    <Typography color="error" variant="body2">{error}</Typography>
                  )}
                  <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    color="primary"
                    sx={{ mt: 3, mb: 2 }}
                  >
                    Sign In
                  </Button>
                  <Grid container justifyContent="flex-end">
                    <Grid item>
                      <Typography variant="body2" color="textSecondary">
                        Don't have an account? Contact admin.
                      </Typography>
                    </Grid>
                  </Grid>
                </Box>
              </Box>
            </Paper>
          </motion.div>
        )}
      </AnimatePresence>
    </Container>
  );
}