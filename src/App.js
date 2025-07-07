// App.js - Landningssida för Internship Portal

import './App.css';
import { useState } from 'react';
import { getAllStudents } from './api/studentApi';
import { getAllCompanies } from './api/companyApi';

// HUVUDFUNKTION - Här börjar hela appen
function App() {
  
  // REACT SKAPAR MINNE FÖR APPEN (useState = minneslådor):
  const [currentPage, setCurrentPage] = useState('home');     // Kommer ihåg vilken sida som visas (startar med 'home')
  const [students, setStudents] = useState([]);               // Kommer ihåg studentdata (startar tom [])
  const [companies, setCompanies] = useState([]);             // Kommer ihåg företagsdata (startar tom [])

  // FUNKTION SOM KÖRS NÄR ANVÄNDAREN KLICKAR "VISA STUDENTER"
  const showStudents = () => {
    // Hämtar data från Spring Boot API (port 8080)
    getAllStudents().then(data => {
      console.log('Data from API:', data);           // Loggar för debugging
      console.log('Array length:', data.length);     // Kollar hur många studenter
      setStudents(data);                             // SPARAR studentdata i minneslådan
      setCurrentPage('students');                    // ÄNDRAR sida till 'students'
      // ↑ React märker att state ändrats och ritar om skärmen automatiskt!
    });
  };

  // FUNKTION SOM KÖRS NÄR ANVÄNDAREN KLICKAR "VISA FÖRETAG"
  const showCompanies = () => {
    // Hämtar företagsdata från Spring Boot API
    getAllCompanies().then(data => {
      setCompanies(data);                            // SPARAR företagsdata i minneslådan
      setCurrentPage('companies');                   // ÄNDRAR sida till 'companies'
      // ↑ React ritar om skärmen igen!
    });
  };

  // VILLKORLIG RENDERING - React kollar currentPage och visar rätt innehåll

  // OM vi är på studenter-sidan, visa studentlistan
  if (currentPage === 'students') {
    return (
      <div className="App">
        {/* Tillbaka-knapp som ändrar currentPage till 'home' */}
        <button onClick={() => setCurrentPage('home')} className="back-button">
          ← Tillbaka till startsidan
        </button>
        
        <h1>Studenter:</h1>
        
        {/* LOOPAR igenom alla studenter från minneslådan och skapar HTML för var och en */}
        {students.map(student => (
          <div key={student.id} className="student-card">
            <p><strong>{student.first_name} {student.last_name}</strong></p>
            <p>Email: {student.email}</p>
            <p>Plats: {student.location}</p>
          </div>
        ))}
      </div>
    );
  }

  // OM vi är på företag-sidan, visa företagslistan
  if (currentPage === 'companies') {
    return (
      <div className="App">
        {/* Tillbaka-knapp */}
        <button onClick={() => setCurrentPage('home')} className="back-button">
          ← Tillbaka till startsidan
        </button>
        
        <h1>Företag:</h1>
        
        {/* LOOPAR igenom alla företag från minneslådan */}
        {companies.map(company => (
          <div key={company.id} className="student-card">
            <p><strong>{company.name}</strong></p>
            <p>Bransch: {company.industry}</p>
            <p>Plats: {company.location}</p>
          </div>
        ))}
      </div>
    );
  }

  // STANDARD-SIDA: Om currentPage = 'home', visa landningssidan
  return (
    <div className="landing-container">
      <h1>Internship Portal</h1>
      <p className="subtitle"></p>  {/* Tom p-tagg som du behåller */}
      
      <div className="options">
        {/* KORT 1: Klick kör showStudents-funktionen */}
        <div className="option-card" onClick={showStudents}>
          <div className="icon">👨‍🎓</div>
          <h3>Visa Studenter</h3>
          <p>Se alla registrerade studenter och deras profiler</p>
        </div>
        
        {/* KORT 2: Klick kör showCompanies-funktionen */}
        <div className="option-card" onClick={showCompanies}>
          <div className="icon">🏢</div>
          <h3>Visa Företag</h3>
          <p>Utforska våra partnerföretag och praktikplatser</p>
        </div>
        
        {/* KORT 3: Placeholder för framtida registrering */}
        <div className="option-card" onClick={() => alert('Registrering kommer snart!')}>
          <div className="icon">📝</div>
          <h3>Registrera Student</h3>
          <p>Lägg till en ny student i systemet</p>
        </div>
        
        {/* KORT 4: Placeholder för framtida företagsregistrering */}
        <div className="option-card" onClick={() => alert('Företagsregistrering kommer snart!')}>
          <div className="icon">➕</div>
          <h3>Lägg till Företag</h3>
          <p>Registrera ett nytt partnerföretag</p>
        </div>
      </div>
      
      {/* Footer */}
      <div className="footer">
        <p>&copy; 2025 Internship Portal - Enkel och effektiv praktikhantering</p>
      </div>
    </div>
  );
}

// EXPORTERAR App så den kan användas i index.js
export default App;

/*
FLÖDET SAMMANFATTAT:
1. App startar → React skapar minne (useState)
2. currentPage = 'home' → Landningssida visas
3. Användare klickar "Visa Studenter" → showStudents körs
4. showStudents hämtar data → setStudents + setCurrentPage
5. React märker state-ändring → Ritar om skärmen automatiskt
6. currentPage = 'students' → Studentlista visas istället
7. Användare klickar "Tillbaka" → setCurrentPage('home')
8. React ritar om igen → Landningssida visas
*/