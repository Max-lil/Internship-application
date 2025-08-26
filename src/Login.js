/* eslint-disable no-undef */
import { useState } from "react";

export default function Login({setCurrentPage}) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [showPW, setShowPw] = useState(false);
    const [status, setStatus] = useState(null); // "ok" | "error" | null
    const API_BASE = "http://localhost:8080";


    async function handleSubmit(e) {
        e.preventDefault();

            const body = new URLSearchParams({email, password}).toString();

            const res = await fetch("http://localhost:8080/login", {
                method: "POST",
                headers: {"Content-Type": "application/x-www-form-urlencoded"},
                credentials: "include", // viktigt: spara session-cookie
                body
            });

            if (!res.ok) {
              setStatus("error");
              return;
            }

            try {
              const meRes = await fetch(`${API_BASE}/me`, { credentials: "include" });
              const me = meRes.ok ? await meRes.json() : {};
              const roles = me?.roles || [];

              if (roles.includes("ROLE_COMPANY")) {
                const cRes = await fetch(`${API_BASE}/company/me`, { credentials: "include" });
                if (cRes.ok) {
                  const company = await cRes.json();
                  sessionStorage.setItem("companyId", String(company.id));
                }
                setCurrentPage("company-portal");
              } else if (roles.includes("ROLE_STUDENT")) {
                setCurrentPage("student-portal");
              } else {
                setCurrentPage("home");
              }
            } catch {
                console.error("Login error:", err);
                setStatus?.("error");
                alert(`Inloggning misslyckades: ${err?.message || "Okänt fel"}`);
            }


                
                
            };


  return(
    <form className="student-form" onSubmit={handleSubmit}>
        <div>
            <label>E-post</label>
            <input
            type="email"
            placeholder="din@mail.se"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            />
        </div>
        
        <div>
            <label>Lösenord</label>
            <input
            type={showPW ? "text" : "password"}
            placeholder="*******"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            />
        </div>

              <div style={{ display: "flex", alignItems: "center", gap: "8px", marginTop: "-8px" }}>
        <button
          type="button"
          className="snabblänkar-btn"
          onClick={() => setShowPw((s) => !s)}
          title="Visa/Dölj lösenord"
        >
          Visa/Dölj
        </button>
      </div>

      <button type="submit">Logga in</button>

        {status === "ok" && <p style={{ marginTop: 8 }}>✅ Inloggad!</p>}
        {status === "error" && <p style={{ marginTop: 8 }}>❌ Fel e-post eller lösenord.</p>} 
    </form>
  )
}