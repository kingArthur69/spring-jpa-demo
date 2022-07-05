package com.amihaliov.spring_jpa_demo.dao;

import com.amihaliov.spring_jpa_demo.model.Customer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Component
public class CustomerDaoImpl implements CustomerDao {

    private final EntityManagerFactory emf;

    public CustomerDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Customer> findAll() {
        EntityManager em = getEntityManager();

        try {
            return em.createNamedQuery("findAllCustomers", Customer.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Customer getById(Long id) {
        return getEntityManager().find(Customer.class, id);
    }

    @Override
    public Customer getByFirstNameAndLastName(String firstName, String lastName) {
        TypedQuery<Customer> query = getEntityManager().createQuery("select c from Customer c " +
                "where c.firstName = :first_name and c.lastName = :last_name", Customer.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);
        return query.getSingleResult();
    }

    @Override
    public List<Customer> findAllCustomerByLastNameLike(String lastName) {

        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("select c from Customer c where c.lastName like :last_name");
            query.setParameter("last_name", lastName + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Customer> findCustomersByAddress(String address) {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Customer> query = em.createNamedQuery("findCustomersByAddress", Customer.class);
            query.setParameter("address", address);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Customer> findAllByFirstName(String firstName) {
        EntityManager em = getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Customer> query = cb.createQuery(Customer.class);

            Root<Customer> root = query.from(Customer.class);

            ParameterExpression<String> firstNameParameter = cb.parameter(String.class);

            Predicate firstNamePred = cb.equal(root.get("firstName"), firstNameParameter);

            query.select(root).where(firstNamePred);

            TypedQuery<Customer> typedQuery = em.createQuery(query);
            typedQuery.setParameter(firstNameParameter, firstName);
            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Customer> findAllByFirstNameNative(String firstName) {

        EntityManager em = getEntityManager();

        try {
            Query nativeQuery = em.createNativeQuery("select * from customers c where c.first_name = ?", Customer.class);
            nativeQuery.setParameter(1, firstName);
            return nativeQuery.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Customer save(Customer customer) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        em.persist(customer);

        em.flush();
        em.getTransaction().commit();

        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        EntityManager em = getEntityManager();
        em.joinTransaction();

        em.merge(customer);

        em.flush();
        em.clear();

        return em.find(Customer.class, customer.getId());
    }

    @Override
    public void delete(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        Customer customer = em.find(Customer.class, id);

        em.remove(customer);

        em.flush();
        em.getTransaction().commit();
    }

    @Override
    public void deleteByFirstNameAndLastName(String firstName, String lastName) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        TypedQuery<Customer> query = em.createQuery("select c from Customer c " +
                "where c.firstName = :first_name and c.lastName = :last_name", Customer.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);

        List<Customer> customers = query.getResultList();

        customers.forEach(em::remove);

        em.flush();
        em.getTransaction().commit();
    }

    EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
