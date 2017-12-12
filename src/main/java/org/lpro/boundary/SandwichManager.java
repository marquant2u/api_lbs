/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lpro.boundary;

import java.util.List;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.lpro.entity.Categorie;
import org.lpro.entity.Sandwich;

/**
 *
 * @author Nicolas
 */
public class SandwichManager {
    
    @PersistenceContext
    EntityManager em;

    public Sandwich findById(long id) {
        return this.em.find(Sandwich.class, id);
    }

    public List<Sandwich> findAll() {
        Query q = this.em.createNamedQuery("Sandwich.findAll", Sandwich.class);
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Sandwich save(Sandwich s) {
        return this.em.merge(s);
    }

    public void delete(long id) {
        try {
            Sandwich ref = this.em.getReference(Sandwich.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException enfe) {
            // rien à faire   
        }
    }
    
    public List<Sandwich> findByImg(String i) {     
        Query q = this.em.createQuery("SELECT s FROM Sandwich s WHERE s.img = :i");
        q.setParameter("i", i);
        return q.getResultList();
    }
    
    public List<Sandwich> findByTypePain(String tp) {
        Query q = this.em.createQuery("SELECT s FROM Sandwich s WHERE s.type_pain = :tp");
        q.setParameter("tp", tp);
        return q.getResultList();
    }
    
    public List<Sandwich> findByTypePainImg(String i, String tp) {
        Query query = this.em.createQuery("SELECT s FROM Sandwich s WHERE s.type_pain = :tp AND s.img != ''");
        query.setParameter("tp", tp);
        return query.getResultList();
    }
    
}
