package dao;

import event.Artist;

public class ArtistDao extends AbstractJpaDao{
    public ArtistDao(){
        setClazz(Artist.class);
    }
}
