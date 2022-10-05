package am.itspace.companyemployeespring.controller;

import am.itspace.companyemployeespring.entity.Company;
import am.itspace.companyemployeespring.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/companies")
    public String company(ModelMap modelMap) {
        List<Company> allCompany = companyRepository.findAll();
        modelMap.addAttribute("companies", allCompany);
        return "companies";
    }
    @GetMapping("/company/add")
    public String addCompany() {
        return "/addcompany";
    }

    @PostMapping("/company/add")
    public String add(@ModelAttribute Company company) {
        companyRepository.save(company);
        return "redirect:/companies";
    }
    @GetMapping("/company/remove")
    public String remove(@RequestParam("id") int id) {
        companyRepository.deleteById(id);
        return "redirect:/companies";
    }
}
