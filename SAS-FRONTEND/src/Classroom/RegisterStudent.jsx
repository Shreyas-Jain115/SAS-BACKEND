import { useRef,useEffect, useState} from "react";
import { DataContext } from "../store/DataContext";
import { registerStudentBasic,registerStudentOtp } from "../store/api/classroom";
import GetOTP from "../Component/GetOTP";
import styles from "./RegisterStudent.module.css";
function RegisterStudent() {
    const emailRef = useRef();
    const passwordRef = useRef();
    const [otp,setOTP] = useState("");
    const handleSubmit = async (e) => {
        e.preventDefault();
        const email = emailRef.current.value;
        console.log(email);
        const password = passwordRef.current.value;
        registerStudentBasic(email).then((response) => {
          if(response.data)
            setOTP("required")
          console.log('Registration successful:', response.data);
        }).catch((error) => {
            console.error('Registration failed:', error);
        });
    }
    useEffect(() => {
          if (otp && otp !== "required" && otp.length == 6) {
            const email = emailRef.current.value;
            const password = passwordRef.current.value;
            console.log(email);
            registerStudentOtp(email,password,otp).then((res) => {
              if (res?.data?.email === email) {
                alert("success");
              }
            }).catch((error)=>{
              console.log(error.message);
            });
          }
        }, [otp]);
    return (
      <div>
  <form className={styles.form} onSubmit={handleSubmit}>
    <div className={styles.formInner}>
      <label htmlFor="emailId" className={styles.label}>Email:</label>
      <input type="email" id="emailId" ref={emailRef} required className={styles.input} />
      <label htmlFor='passwordId' className={styles.label}>Password:</label>
      <input type="password" id="passwordId" ref={passwordRef} required className={styles.input} />
      <button type="submit" className={styles.button}>Register</button>
    </div>
  </form>
  {otp==="required"&&<GetOTP setOTP={setOTP}/>}
  </div>
);

}
export default RegisterStudent;