import axios from "./axiosInstance";
const baseurl="http://localhost:8080";
const classroomBase = `${baseurl}/classroom`;

export const classroomLogin = () =>
  axios.get(`${classroomBase}/login`);

export const startAttendance = (subjectCode) =>
  axios.post(`${classroomBase}/attendance/start`, null, {
    params: { subjectCode }
  });

export const registerStudentBasic = (email) =>
  axios.post(`${classroomBase}/student/register/basic`, null, {
    params: { email }
  });

export const registerStudentOtp = (email, password, otp) =>
  axios.post(`${classroomBase}/student/register/otp`, null, {
    params: { email, password, otp }
  });

export const getStudentsWithAttendance = (code) =>
  axios.get(`${classroomBase}/students/attendance`, {
    params: { code }
  });

export const markStudentPresent = (id) =>
  axios.post(`${classroomBase}/attendance/present`, null, {
    params: { id }
  });

export const sendEmailAttendance = (subjectCode) =>
  axios.post(`${classroomBase}/attendance/sendEmail`, {
    subjectCode: subjectCode, // or just `subjectCode` shorthand
  });




