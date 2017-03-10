package km.software.agendago.db.service.facade;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import km.software.agendago.db.EstadoTarea;
import km.software.agendago.db.service.facade.AbstractFacade;

@Stateless
@Named("estadoTarea")
public class EstadoTareaFacade extends AbstractFacade<EstadoTarea> {

    @PersistenceContext(unitName = "agendaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoTareaFacade() {
        super(EstadoTarea.class);
    }

}
