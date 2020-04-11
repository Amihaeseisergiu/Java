package repo;

public class RepositoryFactory implements AbstractFactory<AbstractRepository> {

    @Override
    public AbstractRepository create(String type)
    {
        switch(type)
        {
            case "Album":
                return new AlbumRepository();
            case "Artist":
                return new ArtistRepository();
            default:
                return new ChartRepository();
        }
    }

}
