package tn.tym.esprit.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.tym.esprit.entities.Contract;
import tn.tym.esprit.interfaces.IcontarctService;
import tn.tym.esprit.repositories.ContractRepository;


@Service


public class ContractServiceImpl implements IcontarctService{
	 @Autowired
	 ContractRepository  cr ;
@Override
	    public Contract addContract(Contract contract) {
	        return cr.save(contract);
	    }
@Override
	    public List<Contract> getAllContracts() {
	        return cr.findAll();
	    }
@Override
	    public Contract getContractById(Long id) {
	        return cr.findById(id).orElse(null);
	    }
@Override
	    // Calculate total duration of all contracts
	    public Long getTotalDuration() {
	        List<Contract> contracts = getAllContracts();
	        return contracts.stream()
	                .mapToLong(Contract::getDuration)
	                .sum();
	    }
@Override
	    // Count total number of contracts
	    public Long getTotalContracts() {
	        return (long) getAllContracts().size();
	    }

    
	}
	
	

	