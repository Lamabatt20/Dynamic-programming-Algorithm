package Application;

import java.util.ArrayList;
import java.util.List;

public class City {
	private String nameCity;
    private ArrayList<AdjacentCity> adjacentsCity;
	public City(String nameCity) {
		this.nameCity = nameCity;
		this.adjacentsCity = new ArrayList<>();
	}
	public String getNameCity() {
		return nameCity;
	}
	public void setNameCity(String nameCity) {
		this.nameCity = nameCity;
	}
	public List<AdjacentCity> getAdjacentsCity() {
		return adjacentsCity;
	}
	public void setAdjacentsCity(ArrayList<AdjacentCity> adjacentsCity) {
		this.adjacentsCity = adjacentsCity;
	}
	

    @Override
	public String toString() {
		return  nameCity + ", " + adjacentsCity ;
	}
	public AdjacentCity getAdjacent(String cityName) {
        for (int i=0;i<adjacentsCity.size();i++) {
            if (adjacentsCity.get(i).getName().equals(cityName)) {
                return adjacentsCity.get(i);
            }
        }
        return null;
    }
   

    
    

}
