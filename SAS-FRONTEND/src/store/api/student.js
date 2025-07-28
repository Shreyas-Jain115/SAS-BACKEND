import axios from "./axiosInstance";
const baseurl="http://localhost:8080";
const studentBase = `${baseurl}/student`;

export const getStudentProfile = () =>
  axios.get(`${studentBase}/profile`);

export const markStudentAttendance = (dateTime, subjectCode) =>
  axios.post(`${studentBase}/attendance/mark`, null, {
    params: { dateTime, subjectCode }
  });

export const getSubjectAttendance = (subjectCode) =>
  axios.get(`${studentBase}/attendance/subject`, {
    params: { subjectCode }
  });
