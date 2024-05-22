package Application;

public class Route {
	private String path;
    private int cost;

    public Route(String path, int cost) {
        this.path = path;
        this.cost = cost;
    }

    public String getPath() {
        return path;
    }

    public int getCost() {
        return cost;
    }

	@Override
	public String toString() {
		return "Route [path=" + path + ", cost=" + cost + "]";
	}
    

}
