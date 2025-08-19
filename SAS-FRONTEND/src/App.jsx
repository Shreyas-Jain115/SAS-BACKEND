import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { Router,Route,Links, Routes } from 'react-router-dom'
import Login from './Component/Login'
import RegisterClassroom from './Admin/RegisterClassroom'
import ClassroomDashboard from './Classroom/ClassroomDashboard'
import RegisterStudent from './Classroom/RegisterStudent'
import SubjectData from './Classroom/SubjectData'
import StudentDashboard from './student/StudentDashboard'
import MarkAttendance from './student/MarkAttendance'
import SubjectDataStudent  from './student/SubjectData'
import AdminDashboard from './Admin/AdminDashboard'
import QrCodeDisplay from './Classroom/QrCodeDisplay'
import RegisterAdmin from './Admin/RegisterAdmin'
import ViewSubject from './Admin/ViewSubject'
import ResetPassword from './Component/ResetPassword'
function App() {

  return (
    <>
        <Routes>
            <Route path="/admin/dashboard" element={<AdminDashboard />} />
            <Route path="/admin/registerAdmin" element={<RegisterAdmin />} />
            <Route path='/admin/registerClassroom' element={<RegisterClassroom />} />
            <Route path="/admin/viewSubject" element={<ViewSubject />} />
            <Route path="/login" element={<Login />} />
            <Route path="/" element={<h1>Welcome to Smart Attendance System</h1>} />
            <Route path="/classroom/dashboard" element={<ClassroomDashboard />} />
            <Route path="/classroom/registerStudent" element={<RegisterStudent />} />
            <Route path="/classroom/subjectData/:subjectCode" element={<SubjectData />} />
            <Route path="/student/dashboard" element={<StudentDashboard />} />
            <Route path="/student/markAttendance" element={<MarkAttendance />} />
            <Route path="/student/subjectData" element={<SubjectDataStudent />} />
            <Route path="/classroom/qrCodeDisplay/:subjectCode" element={<QrCodeDisplay />} />
            <Route path="/resetPassword" element={<ResetPassword />} />
        </Routes>
    </>
  )
}

export default App
