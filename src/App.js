// App.js - Landningssida för Internship Portal med Portal-struktur

import './App.css';
import { useState } from 'react';
import { 
  getAllStudents, 
  registerStudent, 
  getStudentById, 
  addSkillsToStudent, 
  uploadCvForStudent,
  updateStudentEducation 
} from './api/studentApi';
import { getAllCompanies } from './api/companyApi';

// HUVUDFUNKTION - Här börjar hela appen
function App() {
  
  // REACT SKAPAR MINNE FÖR APPEN (useState = minneslådor):
  const [currentPage, setCurrentPage] = useState('home');     // Kommer ihåg vilken sida som visas (startar med 'home')
  const [students, setStudents] = useState([]);               // Kommer ihåg studentdata (startar tom [])
  const [companies, setCompanies] = useState([]);             // Kommer ihåg företagsdata (startar tom [])
  const [formData, setFormData] = useState({                  // Kommer ihåg studentregistrering
    firstName: '',
    lastName: '',
    email: '',
    location: '',
    phoneNumber: '',
    education: '' 
  }); 
  
  // State för att hantera student-profil funktionalitet
  const [currentStudent, setCurrentStudent] = useState(null); // Sparar vald students data
  const [studentId, setStudentId] = useState('');             // För ID-input i "Hantera Profil"
  const [skillsToAdd, setSkillsToAdd] = useState('');         // För att lägga till skills
  
  // FUNKTION SOM KÖRS NÄR ANVÄNDAREN KLICKAR "VISA STUDENTER" (Admin-funktionalitet)
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

  // FUNKTION SOM KÖRS NÄR ANVÄNDAREN KLICKAR "VISA FÖRETAG" (Admin-funktionalitet)
  const showCompanies = () => {
    // Hämtar företagsdata från Spring Boot API
    getAllCompanies().then(data => {
      setCompanies(data);                            // SPARAR företagsdata i minneslådan
      setCurrentPage('companies');                   // ÄNDRAR sida till 'companies'
      // ↑ React ritar om skärmen igen!
    });
  };

  // Funktioner för portal-navigering
  const showAdminPortal = () => {
    setCurrentPage('admin-portal');
  };

  const showStudentPortal = () => {
    setCurrentPage('student-portal');
  };

  const showCompanyPortal = () => {
    setCurrentPage('company-portal');
  };

  // Visa studentregistrering
  const showStudentRegistration = () => {
    setCurrentPage('register-student');
    setFormData({
      firstName: '',
      lastName: '',
      email: '',
      location: '',
      phoneNumber: '',
      education: ''
    });
  };

  // Visa "Hantera Profil" där student anger sitt ID
  const showManageProfile = () => {
    setCurrentPage('manage-profile');
    setStudentId('');
    setCurrentStudent(null);
  };

  // Hämta student-profil baserat på ID
  const fetchStudentProfile = async () => {
    if (!studentId) {
      alert('Vänligen ange ditt student-ID');
      return;
    }
    
    try {
      //Använder API-funktion 
      const student = await getStudentById(studentId);
      setCurrentStudent(student);
      setCurrentPage('student-profile');
    } catch (error) {
      alert('Kunde inte hämta student-profil. Kontrollera ditt ID.');
      console.error('Error fetching student:', error);
    }
  };

  // Hantera när användaren skriver i registreringsformuläret
  const handleInputChange = (e) => {
    // Hämta vilket fält som ändrats och vad användaren skrev
    const { name, value } = e.target;
    
    // Uppdatera formData med det nya värdet
    setFormData(prev => ({
      ...prev,        // Behåll alla gamla värden
      [name]: value   // Ändra bara det fält som användaren skrev i
    }));
  };

  // Hantera när användaren trycker "Registrera"-knappen
  const handleSubmit = async (e) => {
    e.preventDefault();  // Stoppa sidan från att ladda om
    
    try {
      // Skicka all formulärdata till backend (Spring Boot)
      await registerStudent(formData);
      
      alert('Du är nu registrerad i systemet!');   // Visa bekräftelse
      setFormData({
        firstName: '',
        lastName: '',
        email: '',
        location: '',
        phoneNumber: '',
        education: ''
      });
      
      setCurrentPage('home');   // Gå tillbaka till startsidan
      
    } catch (error) {
      // Om något gick fel:
      alert('Något gick fel vid registrering!');  // Visa felmeddelande
    }
  };

  // Hantera skills-tillägg
  const handleAddSkills = async (e) => {
  e.preventDefault();
  if (!skillsToAdd.trim()) {
    alert('Ange minst en skill');
    return;
  }
  
  try {
    // Konvertera komma-separerad sträng till array
    const skillsArray = skillsToAdd.split(',').map(skill => skill.trim());
    
    // Använd API-funktion
    await addSkillsToStudent(currentStudent.id, skillsArray);
    
    alert('Skills tillagda!');
    
    // Uppdatera student-profil
    const updatedStudent = await getStudentById(currentStudent.id);
    setCurrentStudent(updatedStudent);
    
    // FLYTTA DENNA HIT - rensa EFTER allt annat är klart:
    setSkillsToAdd('');
    
  } catch (error) {
    alert('Fel vid tillägg av skills');
    console.error('Error adding skills:', error);
  }
};

  // Hantera CV-uppladdning
  const handleCvUpload = async (e) => {
    e.preventDefault();
    const fileInput = e.target.querySelector('input[type="file"]');
    const file = fileInput.files[0];
    
    if (!file) {
      alert('Välj en fil att ladda upp');
      return;
    }
    
    try {
      // Använd API-funktion 
      await uploadCvForStudent(currentStudent.id, file);
      
      alert('CV uppladdat!');
      // Uppdatera student-profil
      const updatedStudent = await getStudentById(currentStudent.id);
      setCurrentStudent(updatedStudent);
    } catch (error) {
      alert('Fel vid uppladdning av CV');
      console.error('Error uploading CV:', error);
    }
  };

  // VILLKORLIG RENDERING - React kollar currentPage och visar rätt innehåll

  // Admin Portal
  if (currentPage === 'admin-portal') {
    return (
      <div className="App">
        <button onClick={() => setCurrentPage('home')} className="back-button">
          ← Tillbaka till startsidan
        </button>
        
        <h1>Admin Portal</h1>
        
        <div className="options">
          <div className="option-card" onClick={showStudents}>
            <div className="icon">👨‍🎓</div>
            <h3>Visa Studenter</h3>
            <p>Se alla registrerade studenter och deras profiler</p>
          </div>
          
          <div className="option-card" onClick={showCompanies}>
            <div className="icon">🏢</div>
            <h3>Visa Företag</h3>
            <p>Se alla registrerade företag</p>
          </div>
        </div>
      </div>
    );
  }

  // Student Portal
  if (currentPage === 'student-portal') {
    return (
      <div className="App">
        <button onClick={() => setCurrentPage('home')} className="back-button">
          ← Tillbaka till startsidan
        </button>
        
        <h1>Student Portal</h1>
        
        <div className="options">
          <div className="option-card" onClick={showStudentRegistration}>
            <div className="icon">📝</div>
            <h3>Registrera dig</h3>
            <p>Skapa ett nytt studentkonto</p>
          </div>
          
          <div className="option-card" onClick={showManageProfile}>
            <div className="icon">👤</div>
            <h3>Hantera Profil</h3>
            <p>Visa profil, ladda upp CV, hantera skills</p>
          </div>
        </div>
      </div>
    );
  }

  // Company Portal (placeholder)
  if (currentPage === 'company-portal') {
    return (
      <div className="App">
        <button onClick={() => setCurrentPage('home')} className="back-button">
          ← Tillbaka till startsidan
        </button>
        
        <h1>Företag Portal</h1>
        
        <div className="options">
          <div className="option-card" onClick={() => alert('Kommer snart!')}>
            <div className="icon">➕</div>
            <h3>Registrera Företag</h3>
            <p>Lägg till ditt företag i systemet</p>
          </div>
          
          <div className="option-card" onClick={() => alert('Kommer snart!')}>
            <div className="icon">📋</div>
            <h3>Hantera Praktikplatser</h3>
            <p>Skapa och hantera praktikplatser</p>
          </div>
        </div>
      </div>
    );
  }

  // Hantera Profil - ID input
  if (currentPage === 'manage-profile') {
    return (
      <div className="App">
        <button onClick={() => setCurrentPage('student-portal')} className="back-button">
          ← Tillbaka till Student Portal
        </button>
        
        <h1>Hantera Profil</h1>
        
        <div className="student-form">
          <div>
            <label>Ange ditt Student-ID:</label>
            <input
              type="number"
              value={studentId}
              onChange={(e) => setStudentId(e.target.value)}
              placeholder="T.ex. 1"
            />
          </div>
          
          <button onClick={fetchStudentProfile}>
            Hämta Min Profil
          </button>
        </div>
      </div>
    );
  }

  // Student Profile - alla profil-funktioner
  if (currentPage === 'student-profile' && currentStudent) {
    return (
      <div className="App">
        <button onClick={() => setCurrentPage('manage-profile')} className="back-button">
          ← Tillbaka till Hantera Profil
        </button>
        
        <h1>Min Profil</h1>
        
        {/* Visa grundinfo */}
        <div className="student-card">
          <h2>{currentStudent.first_name} {currentStudent.last_name}</h2>
          <p><strong>Email:</strong> {currentStudent.email}</p>
          <p><strong>Telefon:</strong> {currentStudent.phone_number}</p>
          <p><strong>Plats:</strong> {currentStudent.location}</p>
          <p><strong>Utbildning:</strong> {currentStudent.education}</p>
          <p><strong>CV-fil:</strong> {currentStudent.cvFile || 'Ingen CV uppladdad'}</p>
          
          {/* Visa skills */}
          <div>
            <strong>Skills:</strong>
            {currentStudent.skills && currentStudent.skills.length > 0 ? (
              <ul>
                {currentStudent.skills.map((skill, index) => (
                  <li key={index}>{skill}</li>
                ))}
              </ul>
            ) : (
              <p>Inga skills tillagda än</p>
            )}
          </div>
        </div>
        
        {/* CV-uppladdning */}
        <div className="profile-section">
          <h3>Ladda upp CV</h3>
          <form onSubmit={handleCvUpload}>
            <input type="file" accept=".pdf,.doc,.docx" />
            <button type="submit">Ladda upp CV</button>
          </form>
        </div>
        
        {/* Lägg till skills */}
        <div className="profile-section">
          <h3>Lägg till Skills</h3>
          <form onSubmit={handleAddSkills}>
            <input
              type="text"
              value={skillsToAdd}
              onChange={(e) => setSkillsToAdd(e.target.value)}
              placeholder="Ange skills separerade med komma (t.ex. Java, React, SQL)"
            />
            <button type="submit">Lägg till Skills</button>
          </form>
        </div>
      </div>
    );
  }

  // Visa studenter (Admin-funktionalitet)
  if (currentPage === 'students') {
    return (
      <div className="App">
        <button onClick={() => setCurrentPage('admin-portal')} className="back-button">
          ← Tillbaka till Admin Portal
        </button>
        
        <h1>Studenter:</h1>
        
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

  // Visa företag (Admin-funktionalitet)
  if (currentPage === 'companies') {
    return (
      <div className="App">
        <button onClick={() => setCurrentPage('admin-portal')} className="back-button">
          ← Tillbaka till Admin Portal
        </button>
        
        <h1>Företag:</h1>
        
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

  // Registrera student
  if (currentPage === 'register-student') {
    return (
      <div className="App">
        <button onClick={() => setCurrentPage('student-portal')} className="back-button">
          ← Tillbaka till Student Portal
        </button>
        
        <h1>Registrera ny student</h1>
        
        <form onSubmit={handleSubmit} className="student-form">
          <div>
            <label>Förnamn:</label>
            <input
              type="text"
              name="firstName"
              value={formData.firstName}
              onChange={handleInputChange}
              required
            />
          </div>
          
          <div>
            <label>Efternamn:</label>
            <input
              type="text"
              name="lastName"
              value={formData.lastName}
              onChange={handleInputChange}
              required
            />
          </div>
          
          <div>
            <label>Email:</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleInputChange}
              required
            />
          </div>
          
          <div>
            <label>Telefonnummer:</label>
            <input
              type="tel"
              name="phoneNumber"
              value={formData.phoneNumber}
              onChange={handleInputChange}
              required
            />
          </div>
          
          <div>
            <label>Plats:</label>
            <input
              type="text"
              name="location"
              value={formData.location}
              onChange={handleInputChange}
              required
            />
          </div>
          
          <div>
            <label>Utbildning:</label>
            <input
              type="text"
              name="education"
              value={formData.education}
              onChange={handleInputChange}
              required
            />
          </div>
          
          <button type="submit">Registrera Student</button>
        </form>
      </div>
    );
  }

  // Hemsida med 3 portaler 
  return (
    <div className="landing-container">
      <h1>Internship Portal</h1>
      <p className="subtitle"></p>
      
      <div className="options">
        {/* PORTAL 1: Admin Portal */}
        <div className="option-card" onClick={showAdminPortal}>
          <div className="icon">⚙️</div>
          <h3>Admin Portal</h3>
          <p>Hantera studenter, företag och systemfunktioner</p>
        </div>
        
        {/* PORTAL 2: Student Portal */}
        <div className="option-card" onClick={showStudentPortal}>
          <div className="icon">👨‍🎓</div>
          <h3>Student Portal</h3>
          <p>Registrera dig eller hantera din profil</p>
        </div>
        
        {/* PORTAL 3: Företag Portal */}
        <div className="option-card" onClick={showCompanyPortal}>
          <div className="icon">🏢</div>
          <h3>Företag Portal</h3>
          <p>Registrera företag och hantera praktikplatser</p>
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
UPPDATERAT FLÖDE:
1. Home → 3 portaler (Admin, Student, Företag)
2. Student Portal → Registrera dig ELLER Hantera Profil
3. Hantera Profil → Ange ID → Visa profil + CV/Skills funktioner
4. Admin Portal → Visa studenter/företag
5. Företag Portal → Placeholder för framtida funktioner
*/