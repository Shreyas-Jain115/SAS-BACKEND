import { useRef, useContext, useState, useEffect } from "react";
import axios from "axios";
import { DataContext } from "../store/DataContext";
import { loginBasic, loginOtp } from "../store/api/user";
import styles from "./Login.module.css";
import { useNavigate } from "react-router-dom";
import GetOTP from "./GetOTP";
import { Link } from "react-router-dom";
function Login() {
  const navigate = useNavigate();
  const { login } = useContext(DataContext);
  const emailRef = useRef("");
  const passwordRef = useRef("");
  const [otp, setOTP] = useState("");

  const handleSubmitBasic = async (e) => {
    e.preventDefault();
    const email = emailRef.current.value;
    const password = passwordRef.current.value;
    loginBasic(email,password)
      .then((res) => {
        if (res.data) {
          setOTP("required");
        } else {
          alert("wrong password or username");
        }
      })
      .catch((error) => {
        error.response?.data?.message || error.message;
      });
  };

 
  useEffect(() => {
    if (otp && otp !== "required" && otp.length == 6) {
      const email = emailRef.current.value;
      const password = passwordRef.current.value;
      loginOtp(email, password, otp).then((response) => {
        console.log(response);
          login(response.data.token);
          if (response.data.role === "admin") {
            navigate("/admin/dashboard");
          } else if (response.data.role === "classroom") {
            navigate("/classroom/dashboard");
          } else if (response.data.role === "student") {
            navigate("/student/dashboard");
          } else {
            alert("Invalid role");
          }
        
      }).catch((error)=>{
        console.log(error.message);
      });
    }
  }, [otp]);
  return (
    <div className={styles.container}>
      <form className={styles.form} onSubmit={handleSubmitBasic}>
        <h2 className={styles.title}>Welcome Back</h2>
        <label htmlFor="emailId" className={styles.label}>
          Email:
        </label>
        <input
          type="email"
          id="emailId"
          className={styles.input}
          placeholder="Enter your email"
          ref={emailRef}
          required
        />
        <label htmlFor="passwordId" className={styles.label}>
          Password:
        </label>
        <input
          type="password"
          id="passwordId"
          className={styles.input}
          placeholder="Enter your password"
          ref={passwordRef}
          required
        />
        <button type="submit" className={styles.button}>
          Login
        </button>
      </form>
      {otp === "required" && <GetOTP setOTP={setOTP} />}
      <Link to="/resetPassword" className={styles.resetLink}>
        Forgot Password?
      </Link>
    </div>
  );
}

export default Login;
