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

    public List<Sandwich> findAll(int numPage, int taillePage) {
        Query q = this.em.createNamedQuery("Sandwich.findAll", Sandwich.class);
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        q.setFirstResult((numPage-1) * taillePage);
        q.setMaxResults(taillePage);
        return q.getResultList();
    }
    
    public List<Sandwich> findWithParams(int i, String tp, int numPage, int taillePage){
        String qry = "SELECT s FROM Sandwich s";
        if(i == 1) qry += " WHERE s.img != 'null'";
        if (tp != null) qry += " WHERE s.type_pain ='"+tp+"'";
        if (i==1 && tp != null) qry += " WHERE s.type_pain ='"+tp+"' AND s.img != 'null'";
        Query query = this.em.createQuery(qry);
        
        query.setFirstResult((numPage-1) * taillePage);
        query.setMaxResults(taillePage);
        
        Query nbElementsQuery = em.createQuery("SELECT count(s.id)from Sandwich s");
        int numDernierePage = (int)((numPage/taillePage)+1);
        
        return query.getResultList();
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

    Query createQuery(String select_s_FROM_Sandwich_stype_pain__type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
