package com.example.internshipapplication.Company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;  // Kör kod innan varje test
import org.junit.jupiter.api.Test;        // Markerar en testmetod
import java.util.Optional;                // För när metoder kanske returnerar något eller inget
import java.util.List;                    // För listor av data
import static org.mockito.Mockito.*;      // Skapar fake-objekt för testning
import static org.junit.jupiter.api.Assertions.*; // assertEquals, assertNotNull osv

public class CompanyServiceTest {

    private CompanyService companyService;
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setUp() {
        companyRepository = mock(CompanyRepository.class);
        companyService = new CompanyService(companyRepository);
    }


    @Test
    void testGetAllCompanies() {

        when(companyRepository.findAll()).thenReturn(List.of());

        List<Company> result = companyService.getAll();

        assertEquals(0, result.size());

    }

    @Test
    void testGetCompanyById() {
        Company company = new Company();
        company.setId(1L);
        company.setName("Company");

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        Company result = companyService.getCompanyById(1L);
        assertNotNull(result);
        assertEquals("Company", result.getName());
    }



    /*@AfterEach
    public void tearDown() {
        companyRepository = null;
    }*/
}
