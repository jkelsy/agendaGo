package km.software.agendago.db.service.facade;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import km.software.agendago.db.Permiso;
import km.software.agendago.db.service.facade.AbstractFacade;

@Stateless
@Named("permiso")
public class PermisoFacade extends AbstractFacade<Permiso> {

    @PersistenceContext(unitName = "agendaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermisoFacade() {
        super(Permiso.class);
    }

}
