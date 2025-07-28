
import { useEffect,useState,useContext } from "react";
import { getStudentsWithAttendance } from "../store/api/classroom";
import { DataContext } from "../store/DataContext";
import { useParams } from "react-router-dom";
import styles from "./SubjectData.module.css";
import StudentSubjectData from "./StudentSubjectData";
function SubjectData() {
    const [studentData, setStudentData] = useState([]);
    const [viewAttendance, setViewAttendance] = useState("");
    const { token } = useContext(DataContext);
    const {subjectCode} = useParams();
    useEffect(() => {
        getStudentsWithAttendance(subjectCode)
        .then((response) => {
            console.log("Student data:", response.data);
            setStudentData(response.data);
        })
        .catch((error) => {
            console.error("Error fetching student data:", error);
        });
    }, [subjectCode, token]);
    const getAttendance=(attendabceList) => {
        var size=attendabceList.length;
        var presentCount=0;
        attendabceList.forEach((attendance) => {
            if(attendance.present) {
                presentCount++;
            }
        });
        return (presentCount/size)*100;
    }
    
    return (
  <div className={styles.subjectPage}>
    <div className={styles.subjectCode}>Subject Code: {subjectCode}</div>
    <div className={styles.studentList}>
      {studentData.map(student => (
        <div key={student.user.id} className={styles.studentCard}>
          <p className={styles.email}>{student.user.email}</p>
          <p className={styles.attendance}>
            Attendance: {getAttendance(student.attendanceList).toFixed(1)}%
          </p>
          <button className={styles.viewButton} onClick={() => setViewAttendance(key => key === student.user.id ? "" : student.user.id)}>
            <i className='fas fa-angle-down'></i> View Attendance
          </button>
          {viewAttendance === student.user.id && (
            <StudentSubjectData attendanceList={student.attendanceList} />
          )}
        </div>
      ))}
    </div>
  </div>
);

}
export default SubjectData;