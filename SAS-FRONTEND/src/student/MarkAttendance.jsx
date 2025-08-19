import { useContext, useState } from "react";
import { DataContext } from "../store/DataContext";
import { markStudentAttendance } from "../store/api/student";
import { QrReader } from "react-qr-reader";
import haversine from "haversine-distance"; // For distance calculation
import { toast } from "react-toastify";
import styles from "./MarkAttendance.module.css"; // Assuming you have a CSS module for styling

// Helper: Get user's current geolocation
const getCurrentLocation = () =>
  new Promise((resolve, reject) =>
    navigator.geolocation.getCurrentPosition(
      (pos) =>
        resolve({
          lat: pos.coords.latitude,
          lon: pos.coords.longitude,
        }),
      (err) => reject(err)
    )
  );

function MarkAttendance() {
  const { token } = useContext(DataContext);
  const [scanned, setScanned] = useState(false);
  const [scanResult, setScanResult] = useState("");

  const handleScan = async (data) => {
    if (!data || scanned) return;

    try {
      const parsed = JSON.parse(data);
      const { timestamp, subjectCode, data: time, lat, lon } = parsed;

      const qrTime = new Date(timestamp);
      const now = new Date();
      const timeDiff = (now - qrTime) / 1000; // in seconds

      if (timeDiff > 5) {
        toast.error("QR code expired");
        return;
      }

      const current = await getCurrentLocation();
      const qrLoc = { latitude: lat, longitude: lon };
      const userLoc = { latitude: current.lat, longitude: current.lon };

      const distance = haversine(userLoc, qrLoc); // in meters
      if (distance > 50) {
        toast.error("You are too far from the QR code location");
        return;
      }

      // Valid QR, mark attendance
      setScanned(true);
      setScanResult(`Subject: ${subjectCode}, Time: ${time}`);

      await markStudentAttendance(time,subjectCode);

      toast.success("Attendance marked successfully!");
    } catch (err) {
      console.error("QR Scan Error or Invalid QR Code:", err);
      toast.error("Invalid QR code or network error");
    }
  };

  const handleError = (err) => {
    console.error("QR Scan Error:", err);
    toast.error("Camera error");
  };

  return (
  <div className={styles.container}>
    <h2 className={styles.title}>Scan QR to Mark Attendance</h2>

    {!scanned && (
      <div className={styles.qrWrapper}>
        <div className={styles.qrInner}>
          <QrReader
            scanDelay={300}
            constraints={{ facingMode: "environment" }}
            onResult={(result, error) => {
              if (result?.text) handleScan(result.text);
              if (error) handleError(error);
            }}
            style={{ width: "100%" }}
          />
        </div>
        <div className={styles.qrInstructions}>
          Align the camera with the QR code to scan
        </div>
      </div>
    )}

    {scanned && (
      <div className={styles.successBox}>
        <span className={styles.check}>✅</span>
        <p className={styles.success}>Attendance marked successfully!</p>
        <p className={styles.details}>{scanResult}</p>
      </div>
    )}
  </div>
);
}

export default MarkAttendance;
