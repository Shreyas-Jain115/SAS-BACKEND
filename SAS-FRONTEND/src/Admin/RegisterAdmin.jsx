import { useRef, useState,useEffect } from "react";
import GetOTP from "../Component/GetOTP";
import { adminSignUpBasic,adminSignUpOtp } from "../store/api/user";
import styles from "./RegisterAdmin.module.css"; // Assuming you have a CSS module for styling
function RegisterAdmin() {
  const emailRef = useRef("");
  const passwordRef = useRef("");
  const [otp,setOTP] = useState("");
  const handleSubmitBasic = async (e) => {
    e.preventDefault();
    const email = emailRef.current.value;
    const password = passwordRef.current.value;
    adminSignUpBasic({
      email
    }).then(
    (res)=> {
      setOTP("required");
    }
    ).catch((error)=> {
      console.log(error);
    })
  };

  useEffect(() => {
      if (otp && otp !== "required" && otp.length == 6) {
        const email = emailRef.current.value;
        const password = passwordRef.current.value;
        adminSignUpOtp({email,password}, otp).then((res) => {
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
    <form className={styles.form} onSubmit={handleSubmitBasic}>
      <div className={styles.formInner}>
        <label htmlFor="emailId" className={styles.label}>
          Email:
        </label>
        <input
          type="email"
          id="emailId"
          ref={emailRef}
          required
          className={styles.input}
        />
        <label htmlFor="passwordId" className={styles.label}>
          Password:
        </label>
        <input
          type="password"
          id="passwordId"
          ref={passwordRef}
          required
          className={styles.input}
        />
        <button type="submit" className={styles.button}>
          Register
        </button>
      </div>
    </form>
    {otp==="required"&&<GetOTP setOTP={setOTP} />}
    </div>
  );
}

export default RegisterAdmin;
