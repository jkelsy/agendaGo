package km.software.agendago.db.service.facade;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import km.software.agendago.db.ArchivoHistorial;
import km.software.agendago.db.service.facade.AbstractFacade;

@Stateless
@Named("archivoHistorial")
public class ArchivoHistorialFacade extends AbstractFacade<ArchivoHistorial> {

    @PersistenceContext(unitName = "agendaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArchivoHistorialFacade() {
        super(ArchivoHistorial.class);
    }

}
