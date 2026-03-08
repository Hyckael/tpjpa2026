package artist.dao;

import artist.entity.Artist;
import daoGeneric.AbstractJpaDao;

public class artistDao extends AbstractJpaDao<Long, Artist> {
    public artistDao() {this.setClazz(Artist.class);}
}
