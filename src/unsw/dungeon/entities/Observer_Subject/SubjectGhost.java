package unsw.dungeon.entities.Observer_Subject;

public interface SubjectGhost {

    public void attach(ObserverWall o);
	public void detach(ObserverWall o);
	public void notifyObservers(boolean i);
}