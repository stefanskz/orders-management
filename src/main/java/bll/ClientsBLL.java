package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientsDAO;
import dao.AbstractDAO;
import model.Clients;
/**
 * ClientsBLL Class represents the client logic, using ClientsDAO
 */
public class ClientsBLL extends AbstractDAO<Clients> {
    private AbstractDAO<Clients> abstractDAO;
    private List<Validator<Clients>> validators;

    public ClientsBLL() {
        super(Clients.class);
        abstractDAO = new AbstractDAO<>(Clients.class);
        validators = new ArrayList<Validator<Clients>>();
        validators.add(new EmailValidator());
    }

    public Clients findClientById(int id) {
        Clients st = ClientsDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The client with id = " + id + " was not found!");
        }
        return st;
    }

    public Clients findClientByEmails(String email) {
        return ClientsDAO.findByEmail(email);
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

    public Clients absFind(int id) {
        return abstractDAO.findById(id);
    }

    public void absDelete(int id) {
        abstractDAO.deleteById(id);
    }

    public int absInsertion(Clients clients) {
        return abstractDAO.absInsert(clients);
    }

    public void abstrUpdate(Clients clients) {
        abstractDAO.absUpdate(clients);
    }
}