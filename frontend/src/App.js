import './App.css';
import { getAllStudents } from './api/studentApi';
import { useState, useEffect } from 'react';

function App() {
  const [students, setStudents] = useState([]);
  
  useEffect(() => {
    getAllStudents().then(data => {
      console.log('Data from API:', data);
      console.log('Array length:', data.length);
      setStudents(data);
    });
  }, []);

  return (
    <div>
      <h1>Studenter:</h1>
      {students.map(student => (
        <div key={student.id}>
          <p>{student.first_name} {student.last_name}</p>
          <p>Email: {student.email}</p>
          <p>Plats: {student.location}</p>
          <hr />
        </div>
      ))}
    </div>
  );
}

export default App;
