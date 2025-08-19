import { useState, useRef, useContext } from "react";
import { DataContext } from "../store/DataContext";
import { getClassroomByCredentials,removeSubject,addSubject } from "../store/api/user";
import styles from "./ViewSubject.module.css";
const ViewSubject = () => {
  const emailRef = useRef();
  const passwordRef = useRef();
  const subjectRef = useRef();
  const subjectCodeRef = useRef();
  const [classroom, setClassroom] = useState({});
  const [classroomId, setClassroomId] = useState("");
  const handleGetClassroom = async () => {
    const email = emailRef.current.value;
    const password = passwordRef.current.value;

    try {
        getClassroomByCredentials(email,password)
        .then((response) => {
          console.log("Classroom data:", response.data);
          const subjectNames = response.data.subjects.split(":");
          const subjectCodes = response.data.subjectCodes.split(":");
          const subjectObj = {};
          subjectNames.forEach((name, index) => {
            subjectObj[subjectCodes[index]] = { subName: name };
          });
          setClassroom(subjectObj);
          setClassroomId(response.data.id);
        })
        .catch((error) => {
          console.error("Error fetching classroom data:", error);
        });
    } catch (error) {
      console.error("Error during classroom retrieval:", error);
    }
  };

  const handleRemoveSubject = async (subjectCode) => {
    console.log("classroom id"+classroomId)
    try {
    const response =await removeSubject(classroomId,subjectCode)

      if (response.data) {
        alert("Subject removed successfully");
        setClassroom((prevClassroom) => {
          const updatedClassroom = { ...prevClassroom };
          delete updatedClassroom[subjectCode];
          return updatedClassroom;
        });
      } else {
        alert("Failed to remove subject");
      }
    } catch (error) {
      console.error("Error removing subject:", error);
    }
  };

  const handleAddNewSubject = async () => {
    const subjectName = subjectRef.current.value;
    const subjectCode = subjectCodeRef.current.value;
    try {
          addSubject(classroomId,subjectName,subjectCode)
        .then((response) => {
          if (response.data) {
            alert("Subject added successfully");
            subjectRef.current.value = "";
            subjectCodeRef.current.value = "";

            setClassroom((prevClassroom) => ({
              ...prevClassroom,
              [subjectCode]: { subName: subjectName },
            }));
          } else {
            alert("Failed to add subject");
          }
        })
        .catch((error) => {
          console.error("Error adding subject:", error);
        });
    } catch (error) {
      console.error("Error during subject addition:", error);
    }
  };

  return (
    <div className={styles.wrapper}>
      <h1 className={styles.title}>View Subject</h1>
      <div className={styles.formRow}>
        <input
          type="text"
          placeholder="Email"
          ref={emailRef}
          className={styles.input}
        />
        <input
          type="password"
          placeholder="Password"
          ref={passwordRef}
          className={styles.input}
        />
        <button onClick={handleGetClassroom} className={styles.button}>
          Get Classroom
        </button>
      </div>

      {classroomId && (
        <div className={styles.subjectListContainer}>
          {Object.keys(classroom).length > 0 ? (
            Object.entries(classroom).map(([code, details]) => (
              <div key={code} className={styles.subjectCard}>
                <h2>{details.subName}</h2>
                <p>Code: {code}</p>
                <button
                  onDoubleClick={() => handleRemoveSubject(code)}
                  title="Double click to remove subject"
                  className={styles.removeButton}
                >
                  Remove Subject
                </button>
              </div>
            ))
          ) : (
            <p>No subjects found.</p>
          )}

          <div className={styles.addSection}>
            <h3>Add New Subject</h3>
            <div className={styles.formRow}>
              <input
                type="text"
                placeholder="Subject Name"
                ref={subjectRef}
                className={styles.input}
              />
              <input
                type="text"
                placeholder="Subject Code"
                ref={subjectCodeRef}
                className={styles.input}
              />
              <button
                onDoubleClick={handleAddNewSubject}
                title="Double click to add subject"
                className={styles.button}
              >
                Add Subject
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ViewSubject;
