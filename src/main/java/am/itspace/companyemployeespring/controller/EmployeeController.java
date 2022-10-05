package am.itspace.companyemployeespring.controller;

import am.itspace.companyemployeespring.entity.Company;
import am.itspace.companyemployeespring.entity.Employee;
import am.itspace.companyemployeespring.repository.CompanyRepository;
import am.itspace.companyemployeespring.repository.EmployeeRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class EmployeeController {
    @Value("${company.employee.images.folder}")
    private String folderPath;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/employees")
    public String employee(ModelMap modelMap) {
        List<Employee> employeeList = employeeRepository.findAll();
        modelMap.addAttribute(employeeList);
        return "employees";
    }


    @GetMapping("/employee/add")
    public String add(ModelMap modelMap) {
        List<Company> companyList = companyRepository.findAll();
        modelMap.addAttribute("company", companyList);
        return "/addemployee";
    }

    @PostMapping("/employee/add")
    public String addEmployee(@ModelAttribute Employee employee,
                              @RequestParam("employeeImage") MultipartFile file) throws IOException {
        if (!file.isEmpty() && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File newFile = new File(folderPath + File.separator + fileName);
            file.transferTo(newFile);
            employee.setProfilePic(fileName);
        }
        employeeRepository.save(employee);
        Company company = companyRepository.getReferenceById(employee.getCompany().getId());
        int size = (company.getSize() + 1);
        company.setSize(size);
        companyRepository.save(company);
        return "redirect:/employees";
    }

    @GetMapping(value = "/employees/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping("/employee/remove")
    public String remove(@RequestParam("id") int id) {
        Employee employee = employeeRepository.findById(id).get();
        Company company = companyRepository.getReferenceById(employee.getCompany().getId());
        int size = (company.getSize() - 1);
        company.setSize(size);
        employeeRepository.deleteById(id);
        companyRepository.save(company);
        return "redirect:/employees";
    }
}
