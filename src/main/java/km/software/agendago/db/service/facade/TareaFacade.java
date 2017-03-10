package km.software.agendago.db.service.facade;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import km.software.agendago.db.Tarea;
import km.software.agendago.db.service.facade.AbstractFacade;

@Stateless
@Named("tarea")
public class TareaFacade extends AbstractFacade<Tarea> {

    @PersistenceContext(unitName = "agendaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TareaFacade() {
        super(Tarea.class);
    }

}
