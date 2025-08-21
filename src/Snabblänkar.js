import "./App.css"; // css som gäller bara för dessa knappar

export default function Snabblänkar({ setCurrentPage }) {
  return (
    <nav className="snabblänkar">
      <button onClick={() => setCurrentPage('home')} className="snabblänkar-btn">
        Förstasidan
      </button>
      <button onClick={() => setCurrentPage("support")} className="snabblänkar-btn">
        Support
      </button>
      <button onClick={() => setCurrentPage("login")} className="snabblänkar-btn">
        Logga in
      </button>
    </nav>
  );
}
