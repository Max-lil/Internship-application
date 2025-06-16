const BASE_URL = 'http://localhost:8080';

// Hämta alla studenter (från StudentController)
export const getAllStudents = async () => {
  const response = await fetch(`${BASE_URL}/students/all`); 
  return response.json();
};

/*
// Registrera ny student (från StudentRegistration)
export const registerStudent = async (studentData) => {
  const response = await fetch(`${BASE_URL}/register/student`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(studentData)
  });
  return response.json();
};*/