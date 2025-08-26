import "./App.css"; // css som gäller bara för dessa knappar
import React from "react";

export default function Snabblänkar({ setCurrentPage }) {

    const handleLogout = async () => {
      if (!window.confirm("Vill du logga ut?")) return;

      try {
        await fetch("http://localhost:8080/logout", {
          method: "POST",
          credentials: "include",
        });
      } catch {}

      ["token", "role", "companyId", "studentId", "user"].forEach(k => {
      localStorage.removeItem(k);
      sessionStorage.removeItem(k);
      });

      alert("✅ Utloggad.");
      setCurrentPage("home");
    };


  return (
    <nav className="snabblänkar">
      <button onClick={() => setCurrentPage('home')} className="snabblänkar-btn">
        Förstasidan
      </button>
      <button onClick={() => setCurrentPage("support")} className="snabblänkar-btn">
        Support
      </button>
      <button type="button" onClick={handleLogout} className="snabblänkar-btn">
        Logga ut
      </button>
    </nav>
  );
}
