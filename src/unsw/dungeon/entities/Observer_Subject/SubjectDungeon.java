package unsw.dungeon.entities.Observer_Subject;

public interface SubjectDungeon {
    public void attach(ObserverForDungeon o);
	public void detach(ObserverForDungeon o);
	public void notifyObservers();
}