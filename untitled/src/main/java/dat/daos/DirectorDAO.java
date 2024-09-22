package dat.daos;

import dat.dtos.DirectorDTO;
import dat.entities.Director;
import jakarta.persistence.*;

import java.util.List;

public class DirectorDAO {
    private static DirectorDAO instance;
    private static EntityManagerFactory emf;

    public DirectorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static DirectorDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new DirectorDAO(emf);
        }
        return instance;
    }


    public void saveDirector(DirectorDTO directorDTO) {
        Director director = new Director(directorDTO);
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(director);
            em.getTransaction().commit();
        }
    }

    public List<Director> getAllDirectors() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Director> query = em.createQuery("SELECT d FROM Director d", Director.class);
            return query.getResultList();
        }
    }

    // Other CRUD methods
}
