package km.software.agendago.db.service.facade;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import km.software.agendago.db.Historial;
import km.software.agendago.db.service.facade.AbstractFacade;

@Stateless
@Named("historial")
public class HistorialFacade extends AbstractFacade<Historial> {

    @PersistenceContext(unitName = "agendaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistorialFacade() {
        super(Historial.class);
    }

}
