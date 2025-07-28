import { useEffect, useContext, useState } from "react";
import { DataContext } from "../store/DataContext";
import { Link } from "react-router-dom";
import { getStudentProfile } from "../store/api/student";
import styles from "./StudentDashboard.module.css"; // Assuming you have a CSS module for styling
import { useNavigate } from "react-router-dom";
function StudentDashboard() {
  const { token } = useContext(DataContext);
  const [subjects, setSubjects] = useState([]);
  const navigate = useNavigate();
  useEffect(() => {
        getStudentProfile()
      .then((response) => {
        console.log("Student details:", response.data);
        console.log("Enrolled subjects:", response.data.subjects);
        setSubjects(response.data.subjects || []);
      })
      .catch((error) => {
        console.error("Error fetching student details:", error);
      });
  }, [token]);

  const getAttendance = (subjectCode) => {
    navigate(`/student/subjectData`, { state: { subjectCode } });
  };

  return (
    <div className={styles.dashboardContainer}>
      <h1 className={styles.dashboardTitle}>Student Dashboard</h1>
      <h2 className={styles.sectionTitle}>Enrolled Subjects:</h2>
      <ul className={styles.subjectList}>
        {subjects.map((subject) => (
          <li
            key={subject.code}
            className={styles.subjectItem}
            onClick={() => getAttendance(subject.code)}
            tabIndex={0}
            onKeyDown={(e) => {
              if (e.key === "Enter") getAttendance(subject.code);
            }}
          >
            {subject.name}
          </li>
        ))}
      </ul>
      <Link to="/student/markAttendance" className={styles.linkStyled}>
        <button className={styles.attendanceButton}>Mark Attendance</button>
      </Link>
    </div>
  );
}

export default StudentDashboard;
