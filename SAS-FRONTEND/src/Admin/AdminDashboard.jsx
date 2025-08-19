import { Link } from "react-router-dom";
import { useEffect, useContext, useState } from "react";
import styles from "./AdminDashboard.module.css"; // Assuming you have a CSS module for styling
function AdminDashboard() {
  return (
    <div className={styles.dashboardContainer}>
      <h1 className={styles.title}>Admin Dashboard</h1>
      <nav className={styles.nav}>
        <ul className={styles.list}>
          <li className={styles.listItem}>
            <Link to="/admin/registerClassroom" className={styles.link}>
              Register Classroom
            </Link>
          </li>
          <li className={styles.listItem}>
            <Link to="/admin/viewSubject" className={styles.link}>
              View Subjects
            </Link>
          </li>
        </ul>
      </nav>
    </div>
  );
}

export default AdminDashboard;
