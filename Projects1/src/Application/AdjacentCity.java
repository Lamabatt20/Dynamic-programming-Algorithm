package Application;

public class AdjacentCity {
	private String name;
	private int hotelCost;
	private int petrolCost;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHotelCost() {
		return hotelCost;
	}
	public void setHotelCost(int hotelCost) {
		this.hotelCost = hotelCost;
	}
	public int getPetrolCost() {
		return petrolCost;
	}
	public void setPetrolCost(int petrolCost) {
		this.petrolCost = petrolCost;
	}
	
	@Override
	public String toString() {
		return "[" + name + "," + hotelCost + "," + petrolCost + "]";
	}
	
	public AdjacentCity(String nameCity, int hotelCost, int petrolCost) {
		super();
		this.name = nameCity;
		this.hotelCost = hotelCost;
		this.petrolCost = petrolCost;
	}
	

}
