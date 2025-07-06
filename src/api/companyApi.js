// companyApi.js - API-anrop för företag

const BASE_URL = 'http://localhost:8080';

// Hämta alla företag (från CompanyController)
export const getAllCompanies = async () => {
  try {
    const response = await fetch(`${BASE_URL}/company/all`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
  } catch (error) {
    console.error('Error fetching companies:', error);
    throw error;
  }
};