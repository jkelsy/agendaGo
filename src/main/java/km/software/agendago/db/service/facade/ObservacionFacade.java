package km.software.agendago.db.service.facade;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import km.software.agendago.db.Observacion;
import km.software.agendago.db.service.facade.AbstractFacade;

@Stateless
@Named("observacion")
public class ObservacionFacade extends AbstractFacade<Observacion> {

    @PersistenceContext(unitName = "agendaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ObservacionFacade() {
        super(Observacion.class);
    }

}
