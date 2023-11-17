package tn.tym.esprit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.tym.esprit.entities.Client;
import tn.tym.esprit.interfaces.IClientService;
import tn.tym.esprit.repositories.ClientRepository;


@Service
public class ClientService implements IClientService {
	
	 @Autowired
	ClientRepository cr;

	 @Override
	    public Client addClient (Client  client) {
	   
	        return cr.save(client);
	    }
	 	 @Override
	    public void deleteClient(Long id) {
		 cr.deleteById(id);
	    }
		@Override
	    public List<Client> getAllClients() {
	        return cr.findAll();
	    }

}
