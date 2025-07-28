import { useContext, useEffect, useRef, useState } from "react";
import { DataContext } from "../store/DataContext";
import { useParams } from "react-router-dom";
import { v4 as uuidv4 } from "uuid";
import QRCode from "qrcode";
import { toast } from "react-toastify";
import styles from "./QrCodeDisplay.module.css";
// Simulated location helper
const getCurrentPosition = () =>
  new Promise((resolve, reject) =>
    navigator.geolocation.getCurrentPosition(
      (pos) => resolve({ lat: pos.coords.latitude, lon: pos.coords.longitude }),
      (err) => reject(err)
    )
  );

function QrCodeDisplay() {
  const { time } = useContext(DataContext);
  const { subjectCode } = useParams();

  const [qrUrl, setQrUrl] = useState("");
  const [loading, setLoading] = useState(false);
  const intervalRef = useRef(null);

  const generateQR = async () => {
    try {
      const location = await getCurrentPosition();

      const payload = {
        timestamp: new Date().toISOString(),
        data: time,
        subjectCode,
        lat: location.lat,
        lon: location.lon,
        uuid: uuidv4(),
      };

      const qrData = JSON.stringify(payload);
      const url = await QRCode.toDataURL(qrData, { width: 256, margin: 2 });

      setQrUrl(url);
    } catch (err) {
      toast.error("Location error: " + (err.message || "Permission denied"));
      setQrUrl("");
    }
  };

  useEffect(() => {
    if (!subjectCode) return;

    const startQRLoop = async () => {
      setLoading(true);
      await generateQR(); // Generate first immediately
      setLoading(false);

      intervalRef.current = setInterval(async () => {
        await generateQR();
      }, 5000);
    };

    startQRLoop();

    return () => {
      clearInterval(intervalRef.current);
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [subjectCode]);

  if (!subjectCode) {
    return <div>Please select a subject to generate QR code.</div>;
  }

  return (
  <div className={styles.qrContainer}>
    <h2 className={styles.title}>QR Code Generator</h2>
    {loading && <p className={styles.loading}>Generating QR...</p>}
    {qrUrl && !loading && (
      <div className={styles.qrBlock}>
        <img src={qrUrl} alt="QR Code" className={styles.qrImage} />
        <p className={styles.info}>QR auto-refreshes every 5 seconds</p>
      </div>
    )}
  </div>
);
}

export default QrCodeDisplay;
