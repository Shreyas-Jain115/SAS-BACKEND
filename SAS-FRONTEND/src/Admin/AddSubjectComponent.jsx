import { useRef } from "react";
import styles from "./AddSubjectComponent.module.css"; // Assuming you have a CSS module for styling
function AddSubjectComponent({ setSubject }) {
  const subjectNameRef = useRef("");
  const subjectCodeRef = useRef("");

  const AddSubject = (e) => {
    e.preventDefault();
    console.log(1);
    const subCode = subjectCodeRef.current.value.trim();
    const subName = subjectNameRef.current.value.trim();

    if (!subCode || !subName) return;

    setSubject(prev => ({
      ...prev,
      [subCode]: { subName }
    }));

    subjectCodeRef.current.value = "";
    subjectNameRef.current.value = "";
  };

  return (
  <form className={styles.form} onSubmit={AddSubject}>
    <label htmlFor="subjectName" className={styles.label}>Enter Subject Name</label>
    <input type="text" id="subjectName" placeholder="Physics" ref={subjectNameRef} className={styles.input} />
    <label htmlFor="subjectCode" className={styles.label}>Enter Subject Code</label>
    <input type="text" id="subjectCode" placeholder="BPHY01" ref={subjectCodeRef} className={styles.input} />
    <button type="submit" className={styles.button}>Add</button>
  </form>
);
}

export default AddSubjectComponent;
