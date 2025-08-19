import { useEffect, useContext, useState } from "react";
import { DataContext } from "../store/DataContext";
import { classroomLogin, startAttendance } from "../store/api/classroom";
import { useNavigate, Link } from "react-router-dom";
import styles from "./ClassroomDashboard.module.css";
function ClassroomDashboard() {
  const navigate = useNavigate();
  const [subject, setSubject] = useState({});
  const { token, setTime, time } = useContext(DataContext);

  console.log("ClassroomDashboard token:", token);

  if (!token) {
    alert("You are not logged in");

    return <div>Please log in to access the dashboard.</div>;
  }

  useEffect(() => {
    classroomLogin()
      .then((response) => {
        console.log("Classroom data:", response.data);
        const subjectNames = response.data.subjects.split(":");
        const subjectCodes = response.data.subjectCodes.split(":");
        const subjectObj = {};
        subjectNames.forEach((name, index) => {
          subjectObj[subjectCodes[index]] = { subName: name };
        });
        setSubject(subjectObj);
      })
      .catch((error) => {
        console.error("Error fetching classroom data:", error);
      });
  }, []);
  const subjectDataPage = (subjectCode) => {
    navigate(`/classroom/subjectData/${subjectCode}`);
  };

  const handleMarkAttendance = (subjectCode) => {
    startAttendance(subjectCode)
      .then((response) => {
        console.log("Attendance marked successfully:", response.data);
        setTime(response.data);
        navigate("/classroom/qrCodeDisplay/" + subjectCode);
      })
      .catch((error) => {
        console.error("Error marking attendance:", error);
      });
  };

  return (
    <>
      <Link to="/cassroom/registerStudent" className={styles.registerLink}>
        <button className={styles.registerButton}>Register Student</button>
      </Link>

      <div className={styles.dashboardContainer}>
        <h2 className={styles.title}>Classroom Dashboard</h2>
        <h6 className={styles.subTitle}>Welcome to the Classroom Dashboard</h6>
        {Object.keys(subject).map((key) => (
          <div key={key} className={styles.subjectCard}>
            <h3 className={styles.subjectCode}>Subject Code: {key}</h3>
            <p className={styles.subjectName}>
              Subject Name: {subject[key].subName}
            </p>
            <div className={styles.buttonRow}>
              <button
                className={styles.actionButton}
                onClick={() => handleMarkAttendance(key)}
              >
                Mark Attendance
              </button>
              <button
                className={styles.actionButton}
                onClick={() => subjectDataPage(key)}
              >
                View Subject Data
              </button>
            </div>
          </div>
        ))}
        {time && (
          <div className={styles.lastAttendance}>
            <h4>Last Attendance Marked At: {time}</h4>
          </div>
        )}
      </div>
    </>
  );
}

export default ClassroomDashboard;
