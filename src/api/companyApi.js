// companyApi.js - API-anrop för företag

import { type } from "@testing-library/user-event/dist/type";

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

export const registerCompany = async (company) => {
  const res = await fetch(`${BASE_URL}/register/company`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    },
    credentials: 'include',
    body: JSON.stringify(company),
  });

  const ct = res.headers.get('content-type') || '';
  const payload = ct.includes('application/json')
    ? await res.json().catch(() => null)
    : await res.text().catch(() => null);

  if (!res.ok) {
    throw new Error(
      typeof payload === 'string' && payload ? payload :
      payload ? JSON.stringify(payload) : `HTTP ${res.status}`
    );
  }

  return payload || { ok: true };
}
