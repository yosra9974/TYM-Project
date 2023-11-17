package tn.tym.esprit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import tn.tym.esprit.entities.Contract;
import tn.tym.esprit.interfaces.IcontarctService;

@RestController
@RequestMapping("/contracts")
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name = "javainuseapi")
@Validated
public class ContractController {

    @Autowired
    IcontarctService contractService;

    @PostMapping("/add")
    public Contract addContract(@RequestBody Contract contract) {
        return contractService.addContract(contract);
    }

    @GetMapping("/all")
    public List<Contract> getAllContracts() {
        return contractService.getAllContracts();
    }

    @GetMapping("/{id}")
    public Contract getContractById(@PathVariable Long id) {
        return contractService.getContractById(id);
    }

    @GetMapping("/totalDuration")
    public Long getTotalDuration() {
        return contractService.getTotalDuration();
    }

    @GetMapping("/totalContracts")
    public Long getTotalContracts() {
        return contractService.getTotalContracts();
    }
}
