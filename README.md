# 📄 Calibration Certificate Management System

A full-stack web application for managing calibration certificates, service requests, and machine data with secure authentication and role-based access. Built using **Spring Boot** for the backend and **React.js + Material UI** for the frontend.

---

## 🛠 Tech Stack

### Backend
- **Spring Boot**
- **Spring Security (JWT + Bcrypt)**
- **JPA/Hibernate**
- **MySQL**
- **Lombok**
- **Apache POI**

### Frontend
- **React.js**
- **Material UI (MUI)**
- **Axios**

---

## 🚀 Features

### ✅ Core Modules
- **User Authentication & Authorization**
  - JWT-based login
  - Passwords encrypted with Bcrypt
  - Role-based access for Admin, Engineer, and Client

- **Certificate Management**
  - Create, edit, view, and delete calibration certificates
  - Fields: `certificateNumber`, `issueDate`, `expiryDate`, `status`, etc.
  - Link certificates to specific `ClientMachine` and `ServiceRequest`

- **Service Request Handling**
  - Create and manage service requests
  - Assign engineers and track request status

- **Entity Management**
  - Raw Data, Machines, Engineers, Clients, and Test Blocks
  - Fully functional CRUD operations with dynamic tables

- **Dashboard**
  - Admin dashboard with overview and control over all modules

- **PDF Certificate Generation** (Optional / if implemented)

---


## 🧾 Installation & Setup

### Backend (Spring Boot)
```bash
cd backend
./mvnw spring-boot:run

---

### 📦 Clone the Repository

```bash
git clone https://github.com/vishal07k/Callibration-System.git
cd Callibration-System
