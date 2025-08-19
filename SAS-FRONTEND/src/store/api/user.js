import axios from "./axiosInstance";
const baseurl = "http://localhost:8080";
const userBase = `${baseurl}/user`;

export const registerClassroomBasic = (classroom) =>
  axios.post(`${userBase}/admin/registerClassroom/basic`, classroom);

export const registerClassroomOtp = (classroom, otp) =>
  axios.post(`${userBase}/admin/registerClassroom/otp`, classroom, {
    params: { otp },
  });

export const loginBasic = (email, password) =>
  axios.post(`${userBase}/login/basic`, {
    email,
    password,
  });

export const loginOtp = (email, password, otp) => {
  console.log(email);
  return axios.post(
    `${userBase}/login/otp`,
    {
      email,
      password,
    },
    {
      params: { otp },
    }
  );
}

export const adminSignUpBasic = (user) =>
  axios.post(`${userBase}/admin/signUp/basic`, user);

export const adminSignUpOtp = (user, otp) =>
  axios.post(`${userBase}/admin/signUp/otp`, user, {
    params: { otp },
  });

export const getClassroomByCredentials = (email, password) =>
  axios.get(`${userBase}/admin/classroom`, {
    params: { email, password },
  });

export const removeSubject = (id, subjectCode) =>
  axios.delete(`${userBase}/admin/subject/remove`, {
    params: { id, subjectCode },
  });

export const addSubject = (id, subjectName, subjectCode) =>
  axios.put(`${userBase}/admin/subject/add`, null, {
    params: { id, subjectName, subjectCode },
  });
export const resetPasswordBasic = (email) =>
  axios.post(`${userBase}/reset/basic`, { email });

export const resetPasswordOtp = (email, otp,password) =>
  axios.post(`${userBase}/reset/otp`, { email, password }, {
    params: { otp },
  });
      