const BASE_URL = 'http://localhost:8080';

// Hämta alla studenter (från StudentController)
export const getAllStudents = async () => {
  try {
    const response = await fetch(`${BASE_URL}/students/all`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
  } catch (error) {
    console.error('Error fetching students:', error);
    throw error;
  }
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
