import styles from "./StudentSubjectData.module.css"; // Assuming you have a CSS module for styling
import { markStudentPresent } from "../store/api/classroom";
import { useContext } from "react";
import { DataContext } from "../store/DataContext";

function StudentSubjectData({ attendanceList }) {
  console.log("Attendance List:", attendanceList);
  const { token } = useContext(DataContext);
  const handleMarkAttendance = async (attendanceId) => {
    try {
      const response = await markStudentPresent(attendanceId);
      console.log("Attendance marked successfully:", response.data);
    } catch (error) {
      console.error("Error marking attendance:", error);
    }
  };

  return (
    <div className={styles.wrapper}>
      <h2 className={styles.title}>Attendance Data</h2>

      <h6 className={styles.section}>Absent</h6>
      <div className={styles.attendanceList}>
        {attendanceList.map(
          (attendance, index) =>
            !attendance.present && (
              <div key={index} className={styles.attendanceCard}>
                <p>Date: {new Date(attendance.time).toLocaleDateString()}</p>
                <button
                  className={styles.markButton}
                  onDoubleClick={() => handleMarkAttendance(attendance.id)}
                  title="Double click to mark attendance"
                >
                  Mark Attendance
                </button>
              </div>
            )
        )}
      </div>

      <h6 className={styles.section}>Present</h6>
      <div className={styles.attendanceList}>
        {attendanceList.map(
          (attendance, index) =>
            attendance.present && (
              <div key={index} className={styles.attendanceCard}>
                <p>Date: {new Date(attendance.time).toLocaleDateString()}</p>
              </div>
            )
        )}
      </div>

      <div className={styles.stats}>
        <div>Total Attendance: {attendanceList.length}</div>
        <div>
          Present Count: {attendanceList.filter((att) => att.present).length}
        </div>
        <div>
          Absent Count: {attendanceList.filter((att) => !att.present).length}
        </div>
        <div>
          Attendance Percentage:{" "}
          {attendanceList.length > 0
            ? (
                (attendanceList.filter((att) => att.present).length /
                  attendanceList.length) *
                100
              ).toFixed(2)
            : "0.00"}
          %
        </div>
      </div>
    </div>
  );
}

export default StudentSubjectData;
