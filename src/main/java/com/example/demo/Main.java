package com.example.demo;

import com.CMS.Model.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, Hibernate!");
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.postgres.jpa");

        User user = new User();
        user.setFullName("John Doe");
        user.setUserType("Listener");
        user.setSalt("f1nd1ngn3m0");
        user.setPassword("batman12345");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        entityManagerFactory.close();
    }
}
