package dao;

import event.Artist;

public class ArtistDao extends AbstractJpaDao<Long, Artist>{
    public ArtistDao(){
        this.setClazz(Artist.class);
    }
}
