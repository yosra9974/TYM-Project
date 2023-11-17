package tn.tym.esprit.interfaces;

import java.util.List;

import tn.tym.esprit.entities.Contract;




public interface IcontarctService {

	Contract addContract(Contract contract);

	List<Contract> getAllContracts();

	Contract getContractById(Long id);

	Long getTotalDuration();

	Long getTotalContracts();


}
