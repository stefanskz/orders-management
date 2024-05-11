package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientsDAO;
import model.Clients;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 */
public class ClientsBLL {

    private List<Validator<Clients>> validators;

    public ClientsBLL() {
        validators = new ArrayList<Validator<Clients>>();
        validators.add(new EmailValidator());
    }

    public Clients findClientById(int id) {
        Clients st = ClientsDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return st;
    }

    public List<Clients> findClients() {
        return ClientsDAO.find();
    }

    public void deleteClientById(int clientId) {
        ClientsDAO.deleteById(clientId);
    }

    public int insertClient(Clients clients) {
        for (Validator<Clients> v : validators) {
            v.validate(clients);
        }
        return ClientsDAO.insert(clients);
    }

    public void updateClient(Clients clients) {
        ClientsDAO.update(clients);
    }
}