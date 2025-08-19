import { useRef, useContext, useState, useEffect } from "react";
import { DataContext } from "../store/DataContext";
import {
  registerClassroomBasic,
  registerClassroomOtp,
} from "../store/api/user";
import AddSubjectComponent from "./AddSubjectComponent";
import styles from "./RegisterClassroom.module.css"; // Assuming you have a CSS module for styling
import GetOTP from "../Component/GetOTP";
function RegisterClassroom() {
  const { token } = useContext(DataContext);
  const teacherEmailRef = useRef("");
  const teacherPasswordRef = useRef("");
  const teacherPhoneRef = useRef("");
  const [subject, setSubject] = useState({});
  const [otp, setOTP] = useState("");
  const Register = () => {
    const email = teacherEmailRef.current.value.trim();
    console.log("Check Email:"+email);
    if (!email) {
      alert("Please fill all fields");
      return;
    }
    const data = {
      user: {
        email,
        role: "teacher",
      },
    };
    console.log("Registering with data1:", data);
    if (!token) {
      alert("You are not logged in");
      return;
    }
    registerClassroomBasic(data)
      .then((response) => {
        response.data
          ? setOTP("required")
          : alert("Registration failed: " + response.data.message);
      })
      .catch((error) => {
        console.error("There was an error!", error);
      });
  };
  useEffect(() => {
    console.log(subject);
  }, [subject]);

  const removeSubject = (key) => {
    setSubject((prev) => {
      const { [key]: _, ...rest } = prev;
      return rest;
    });
  };

  useEffect(() => {
    if (otp && otp !== "required" && otp.length == 6) {
      const email = teacherEmailRef.current.value.trim();
      console.log("Check Email:"+email);
      const password = teacherPasswordRef.current.value.trim();
      const phone = teacherPhoneRef.current.value.trim();
      var subjectCodes = "";
      var subjectNames = "";
      for (const key in subject) {
        if (Object.hasOwnProperty.call(subject, key)) {
          subjectCodes += key + ":";
          subjectNames += subject[key].subName + ":";
        } else {
          alert("No subjects added for key: " + key);
          return;
        }
      }
      if (!email || !password || !phone) {
        alert("Please fill all fields");
        return;
      }
      const data = {
        user: {
          email,
          password,
          role: "teacher",
        },
        phoneNo: phone,
        subjects: subjectNames.slice(0, -1), // Remove trailing colon
        subjectCodes: subjectCodes.slice(0, -1), // Remove trailing colon
      };
      console.log("Registering with data:", data);
      if (!token) {
        alert("You are not logged in");
        return;
      }
      registerClassroomOtp(data, otp)
        .then((res) => {
          if (res?.data?.user?.email === email) {
            alert("success");
          }
        })
        .catch((error) => {
          console.log(error.message);
        });
    }
  }, [otp]);

  return (
    <div className={styles.wrapper}>
      <div className={styles.formGroup}>
        <label htmlFor="emailId" className={styles.label}>
          Email:
        </label>
        <input
          type="email"
          id="emailId"
          placeholder="Email"
          ref={teacherEmailRef}
          required
          className={styles.input}
        />
        <label htmlFor="passwordId" className={styles.label}>
          Password:
        </label>
        <input
          type="password"
          id="passwordId"
          placeholder="Password"
          ref={teacherPasswordRef}
          required
          className={styles.input}
        />
        <label htmlFor="phoneId" className={styles.label}>
          Phone:
        </label>
        <input
          type="tel"
          id="phoneId"
          placeholder="Phone"
          ref={teacherPhoneRef}
          required
          className={styles.input}
        />
        <AddSubjectComponent setSubject={setSubject} />
        <button className={styles.button} onClick={Register}>
          Register
        </button>
      </div>
      <ul className={styles.subjectList}>
        {Object.keys(subject).map((key) => (
          <li
            className={styles.subjectItem}
            onClick={() => removeSubject(key)}
            key={key}
          >
            {key}: {subject[key].subName}
          </li>
        ))}
      </ul>
      {otp==="required"&&<GetOTP setOTP={setOTP}/>}
    </div>
  );
}

export default RegisterClassroom;
