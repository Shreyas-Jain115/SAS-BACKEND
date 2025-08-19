import React, { useRef, useState } from "react";
import { resetPasswordBasic, resetPasswordOtp } from "../store/api/user";

const ResetPassword = () => {
  const emailRef = useRef();
  const passwordRef = useRef();
  const otpRef = useRef();

  const [step, setStep] = useState("email"); // steps: 'email' → 'otp'
  const [loading, setLoading] = useState(false);

  const handleResetPassword = async () => {
    const email = emailRef.current.value;

    if (step === "email") {
      if (!email) {
        alert("Please enter your email");
        return;
      }

      setLoading(true);
      try {
        await resetPasswordBasic(email);
        alert("OTP sent to your email for password reset");
        setStep("otp");
      } catch (error) {
        console.error("Error sending OTP:", error);
        alert("Failed to send OTP. Please try again.");
      } finally {
        setLoading(false);
      }
    }

    if (step === "otp") {
      const otp = otpRef.current.value;
      const password = passwordRef.current.value;

      if (!email || !otp || !password) {
        alert("Please fill in all fields.");
        return;
      }

      setLoading(true);
      try {
        const response = await resetPasswordOtp(email, otp, password);
        // login(response.data); // Optional: call your login function
        alert("Password reset successfully");
        // Optionally redirect the user or reset the form
      } catch (error) {
        console.error("Error resetting password:", error);
        alert("Failed to reset password. Please check the OTP and try again.");
      } finally {
        setLoading(false);
      }
    }
  };

  return (
    <div>
      <h2>Reset Password</h2>
      <div style={{ marginBottom: "10px" }}>
        <input type="email" ref={emailRef} placeholder="Enter your email" disabled={step === "otp"} />
      </div>
      {step === "otp" && (
        <>
          <div style={{ marginBottom: "10px" }}>
            <input type="text" ref={otpRef} placeholder="Enter OTP" />
          </div>
          <div style={{ marginBottom: "10px" }}>
            <input type="password" ref={passwordRef} placeholder="Enter new password" />
          </div>
        </>
      )}
      <button onClick={handleResetPassword} disabled={loading}>
        {loading ? "Please wait..." : "Reset Password"}
      </button>
    </div>
  );
};

export default ResetPassword;
