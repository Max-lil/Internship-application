const BASE_URL = 'http://localhost:8080';

// BEFINTLIGA FUNKTIONER:

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

// Registrera ny student (från StudentRegistration)
export const registerStudent = async (studentData) => {
  try {
    const formData = new FormData();
    formData.append('firstName', studentData.firstName);
    formData.append('lastName', studentData.lastName);
    formData.append('email', studentData.email);
    formData.append('location', studentData.location);
    formData.append('phoneNumber', studentData.phoneNumber);
    formData.append('education', studentData.education);
    formData.append('password', studentData.password);
    
    console.log('Skickar data:', studentData); // DEBUG
    
    const response = await fetch(`${BASE_URL}/register/student`, {
      method: 'POST',
      body: formData
    });
    
    console.log('Response status:', response.status); // DEBUG
    console.log('Response ok:', response.ok); // DEBUG
    
    if (!response.ok) {
      const errorText = await response.text();
      console.log('Error text:', errorText); // DEBUG
      throw new Error(errorText || `HTTP error! status: ${response.status}`);
    }
    
    const result = await response.json();
    console.log('Success result:', result); // DEBUG
    return result;
  } catch (error) {
    console.error('Error registering student:', error);
    throw error;
  }
};

// Hämta student by ID (GET /students/id/{id})
export const getStudentById = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/students/id/${id}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
  } catch (error) {
    console.error('Error fetching student by ID:', error);
    throw error;
  }
};

// Hämta students skills (GET /students/{id}/skills)
export const getStudentSkills = async (id) => {
  try {
    const response = await fetch(`${BASE_URL}/students/${id}/skills`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
  } catch (error) {
    console.error('Error fetching student skills:', error);
    throw error;
  }
};

// Lägg till skills till student (POST /students/{id}/add-skills)
export const addSkillsToStudent = async (id, skillNames) => {
  try {
    console.log('Lägger till skills:', skillNames, 'för student:', id); // DEBUG
    
    const response = await fetch(`${BASE_URL}/students/${id}/add-skills`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(skillNames) // skillNames ska vara en array av strings
    });
    
    console.log('Add skills response status:', response.status); // DEBUG
    
    if (!response.ok) {
      const errorText = await response.text();
      console.log('Add skills error:', errorText); // DEBUG
      throw new Error(errorText || `HTTP error! status: ${response.status}`);
    }
    
    return response.json();
  } catch (error) {
    console.error('Error adding skills to student:', error);
    throw error;
  }
};

// Ta bort skill från student (DELETE /students/{id}/skills/{skillId})
export const removeSkillFromStudent = async (studentId, skillId) => {
  try {
    console.log('Tar bort skill:', skillId, 'från student:', studentId); // DEBUG
    
    const response = await fetch(`${BASE_URL}/students/${studentId}/skills/${skillId}`, {
      method: 'DELETE'
    });
    
    console.log('Remove skill response status:', response.status); // DEBUG
    
    if (!response.ok) {
      const errorText = await response.text();
      console.log('Remove skill error:', errorText); // DEBUG
      throw new Error(errorText || `HTTP error! status: ${response.status}`);
    }
    
    return response.text(); // Returnerar string-meddelande
  } catch (error) {
    console.error('Error removing skill from student:', error);
    throw error;
  }
};

// Ladda upp CV för befintlig student (PUT /students/{id}/cv)
export const uploadCvForStudent = async (id, file) => {
  try {
    console.log('Laddar upp CV för student:', id); // DEBUG
    
    const formData = new FormData();
    formData.append('cv', file);
    
    const response = await fetch(`${BASE_URL}/students/${id}/cv`, {
      method: 'PUT',
      body: formData
    });
    
    console.log('CV upload response status:', response.status); // DEBUG
    
    if (!response.ok) {
      const errorText = await response.text();
      console.log('CV upload error:', errorText); // DEBUG
      throw new Error(errorText || `HTTP error! status: ${response.status}`);
    }
    
    return response.text(); // Returnerar bekräftelse-meddelande
  } catch (error) {
    console.error('Error uploading CV:', error);
    throw error;
  }
};

// Uppdatera students utbildning (PUT /students/{id}/education)
export const updateStudentEducation = async (id, education) => {
  try {
    console.log('Uppdaterar utbildning för student:', id, 'till:', education); // DEBUG
    
    const response = await fetch(`${BASE_URL}/students/${id}/education`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(education) // Skicka education som string i JSON
    });
    
    console.log('Update education response status:', response.status); // DEBUG
    
    if (!response.ok) {
      const errorText = await response.text();
      console.log('Update education error:', errorText); // DEBUG
      throw new Error(errorText || `HTTP error! status: ${response.status}`);
    }
    
    return response.json(); // Returnerar uppdaterad student
  } catch (error) {
    console.error('Error updating student education:', error);
    throw error;
  }
};