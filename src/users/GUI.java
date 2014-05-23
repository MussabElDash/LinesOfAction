package users;

public class GUI extends User {

	public GUI() {
	}

	@Override
	public void play() {
		getBoardLayout().addAllListeners();
		while (!getBoardLayout().allListenersAreNull()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
