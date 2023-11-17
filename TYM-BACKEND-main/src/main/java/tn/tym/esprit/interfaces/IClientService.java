package tn.tym.esprit.interfaces;

import java.util.List;

import tn.tym.esprit.entities.Client;


public interface IClientService {


	List<Client> getAllClients();

	Client addClient(Client client);

	void deleteClient(Long id);

}
