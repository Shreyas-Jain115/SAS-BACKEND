import React, { useState ,useRef} from 'react';
import styles from './GetOTP.module.css';

const GetOTP = ({ setOTP }) => {
  const inputRef=useRef();

  const handleSubmit = () => {
    setOTP(inputRef.current.value);
  };

  const onClose=() => {
    setOTP("");
  }

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <h2 className={styles.title}>Enter OTP</h2>
        <input
          type="text"
          ref={inputRef}
          placeholder="Enter OTP"
          className={styles.input}
        />
        <div className={styles.buttonGroup}>
          <button className={styles.submitBtn} onClick={handleSubmit}>
            Submit
          </button>
          <button className={styles.cancelBtn} onClick={onClose}>
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
};

export default GetOTP;
