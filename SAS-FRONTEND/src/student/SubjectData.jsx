
import { useState, useContext, useEffect } from "react";
import { getSubjectAttendance } from "../store/api/student";
import { DataContext } from "../store/DataContext";
import { useLocation } from "react-router-dom";
import styles from "./SubjectData.module.css";
function SubjectData() {
  const [attendanceData, setAttendanceData] = useState([]);
  const { token } = useContext(DataContext);
  const location = useLocation();
  const subjectCode = location.state?.subjectCode;

  useEffect(() => {
    if (subjectCode) {
          getSubjectAttendance(subjectCode)
        .then((response) => {
          console.log("Attendance data:", response.data);
          setAttendanceData(response.data);
        })
        .catch((error) => {
          console.error("Error fetching attendance data:", error);
        });
    }
  }, [subjectCode, token]);

  return (
    <div className={styles.subjectContainer}>
      <h2 className={styles.subjectTitle}>
        Subject Code: <span className={styles.subjectCode}>{subjectCode}</span>
      </h2>

      {attendanceData.length === 0 ? (
        <div className={styles.noData}>No attendance records found.</div>
      ) : (
        <div className={styles.attendanceList}>
          {attendanceData.map((attendance, index) => (
            <div
              key={index}
              className={
                attendance.present
                  ? styles.attendanceCardPresent
                  : styles.attendanceCardAbsent
              }
            >
              <div className={styles.attendanceTime}>
                {new Date(attendance.time).toLocaleString()}
              </div>
              <div className={styles.attendanceStatus}>
                {attendance.present ? "Present" : "Absent"}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
export default SubjectData;
