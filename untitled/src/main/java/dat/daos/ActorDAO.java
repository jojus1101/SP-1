package dat.daos;
import dat.dtos.ActorDTO;
import dat.dtos.MovieDTO;
import dat.entities.Actor;
import dat.entities.Movie;
import jakarta.persistence.*;

import java.util.List;
// ActorDAO
public class ActorDAO {
    private static ActorDAO instance;
    private static EntityManagerFactory emf;

    private ActorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static ActorDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new ActorDAO(emf);
        }
        return instance;
    }
    public void saveActor(ActorDTO actorDTO) {
        Actor actor = new Actor(actorDTO);
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(actor);
            em.getTransaction().commit();
        }
    }

    // Other CRUD methods
}

