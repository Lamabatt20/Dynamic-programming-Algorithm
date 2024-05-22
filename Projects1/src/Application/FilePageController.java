package Application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


import javafx.event.ActionEvent;

public class FilePageController {
	@FXML
	private TextField filetext;
	private static ArrayList<City> citys;
	private static int numCities;// size of city
	private static String startCity;
	private static String endCity;
	private static int[][] dp;//initialize dp table 

	public void initialize() {
		citys = new ArrayList<>();

	}
//choose file using file chooser  and read file 
	@FXML
	public void filebutton(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(filetext.getScene().getWindow());
		if (file != null) {
			String filePath = file.getAbsolutePath();//get path of a file 
			filetext.setText(filePath);
			citys = ReadFile(filePath);//read file 
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setContentText("File successfully read.");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("No file selected");
			alert.showAndWait();
		}
	}
// button for display result 
	@FXML
	public void startbutton(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("ResultPage.fxml"));
			Scene s = new Scene(root);
			Stage travel = new Stage();
			travel.setScene(s);
			travel.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dp = initializeDp(citys);//initialize dp array 
		fillDp(citys, dp);
		// Result for DP Table
		
		printDp(citys, dp, ResultPageController.r.tabelresult);
		
		// Result for minimum cost
		if(dp[getIndex(startCity)][getIndex(endCity)]!=Integer.MAX_VALUE) {
		ResultPageController.r.costtext.setText(dp[0][numCities - 1] + "");
		}
		ArrayList<Route> allPaths = findAlternativePath(startCity, new ArrayList<>(),dp);
		// sort all paths
	    sortRoutes(allPaths);
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < 2; i++) {
			sb.append(allPaths.get(i).getPath()).append(" (Cost: ").append(allPaths.get(i).getCost()).append(")")
					.append("\n");
			// Result for shortest path
			ResultPageController.r.pathtext.setText(allPaths.get(0).getPath());
		
		}
		// Results for alternative Paths with cost
		ResultPageController.r.textarea.appendText(sb.toString());

	}

//Read file 
	public static ArrayList<City> ReadFile(String filePath) {
		citys = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			numCities = Integer.parseInt(br.readLine());
			String[] startEnd = br.readLine().split(", ");
			startCity = startEnd[0];
			endCity = startEnd[1];

			// System.out.println(numCities);
			// System.out.println(startCity + "," + endCity);

			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(", ");

				String cityName = parts[0];
				City city = null;

				for (int i = 0; i < citys.size(); i++) {
					City existingCity = citys.get(i);
					if (existingCity.getNameCity().equals(cityName)) {
						city = existingCity;
						break;
					}
				}

				if (city == null) {
					city = new City(cityName);
					citys.add(city);
				}

				// System.out.print(cityName + ", ");

				for (int i = 1; i < parts.length; i++) {
					String[] adjacentData = parts[i].substring(1, parts[i].length() - 1).split(",");
					String adjacentName = adjacentData[0];
					int hotelCost = Integer.parseInt(adjacentData[1]);
					int petrolCost = Integer.parseInt(adjacentData[2]);

					AdjacentCity adjacentCity = new AdjacentCity(adjacentName, hotelCost, petrolCost);
					city.getAdjacentsCity().add(adjacentCity);

					// System.out.print("[" + adjacentName + ", " + hotelCost + ", " + petrolCost +
					// "] ");
				}

				// System.out.println();
			}
			citys.add(new City(endCity));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return citys;
	}

// Initializes the dynamic programming matrix 
	public static int[][] initializeDp(ArrayList<City> citys) {
		numCities = citys.size();
		int[][] dp = new int[numCities][numCities];
		for (int i = 0; i < numCities; i++) {
			for (int j = 0; j < numCities; j++) {
				if (i == j) {
					dp[i][j] = 0;
				} else {
					dp[i][j] = Integer.MAX_VALUE;
				}
			}
		}
		return dp;
	}

// fill table DP
	public static void fillDp(ArrayList<City> citys, int[][] dp) {
		numCities = citys.size();
		for (int i = 0; i < numCities; i++) {// fill the DP table with initial values
			for (int j = 0; j < numCities; j++) {
				if (i == j) {
					dp[i][j] = 0;
				} else { // i<j If it is in direct distance between two cities  
					City city1 = citys.get(i);
					City city2 = citys.get(j);
					AdjacentCity adjacentCity = city1.getAdjacent(city2.getNameCity());
					if (adjacentCity != null) {
						dp[i][j] = adjacentCity.getHotelCost() + adjacentCity.getPetrolCost();
					} else {
						dp[i][j] = Integer.MAX_VALUE;
					}
				}
			}
		}

		for (int k = 0; k < numCities; k++) { // fill the DP table with minimum
			for (int i = 0; i < numCities; i++) {
				for (int j = 0; j < numCities; j++) {
					if (dp[i][k] != Integer.MAX_VALUE && dp[k][j] != Integer.MAX_VALUE) {
						if (dp[i][j] > dp[i][k] + dp[k][j]) {
							dp[i][j] = dp[i][k] + dp[k][j];
						}

					}
				}
			}
		}
	}

// print table dp in text area
	public static void printDp(ArrayList<City> cities, int[][] dp, TextArea textArea) {
		textArea.setText("");
		StringBuilder sb = new StringBuilder("\t");
		for (int i = 0; i < cities.size(); i++) {
			sb.append(cities.get(i).getNameCity()).append("\t");//print name city in table 
		}
		sb.append("\n");
		textArea.appendText(sb.toString());

		// Print the DP table
		for (int i = 0; i < cities.size(); i++) {
			StringBuilder sb2 = new StringBuilder(cities.get(i).getNameCity()).append("\t");
			for (int j = 0; j < cities.size(); j++) {
				if (dp[i][j] == Integer.MAX_VALUE) {//if it is no direct distance between two cities
					sb2.append(" ").append("\t");
				} else {
					sb2.append(dp[i][j]).append("\t");
				}
			}
			sb2.append("\n");
			textArea.appendText(sb2.toString());
		}
	}

// find results Alternative paths 
	private ArrayList<Route> findAlternativePath(String currentCity, ArrayList<String> path, int[][] dp) {
	    path.add(currentCity);
	    if (currentCity.equals(endCity)) {
	        int cost = calculatePathCost(path);//calculate cost for each paths 
	        String pathString = "";
			for (int i = 0; i < path.size(); i++) {
				pathString += path.get(i);
				if (i != path.size() - 1) {
					pathString += " -> ";
				}
			}
	        ArrayList<Route> result = new ArrayList<>();
	        result.add(new Route(pathString, cost));
	        return result;
	    }

	    ArrayList<Route> allPaths = new ArrayList<>();
	    int currentCityIndex = getIndex(currentCity);//get index for current city 

	    for (int i = 0; i < citys.size(); i++) {
	        if (citys.get(i).getNameCity().equals(currentCity)) {
	            for (int j = 0; j < citys.get(i).getAdjacentsCity().size(); j++) {
	                String adjacentName = citys.get(i).getAdjacentsCity().get(j).getName();
	                int adjacentCityIndex = getIndex(adjacentName);//get index for adjacent city 
	                
	                if (currentCityIndex >= 0 && currentCityIndex < dp.length &&
	                	    adjacentCityIndex >= 0 && adjacentCityIndex < dp[0].length &&!path.contains(adjacentName) && dp[currentCityIndex][adjacentCityIndex]!= Integer.MAX_VALUE) {//if it is direct distance between current city and adjacent city 
	                    ArrayList<Route> paths = findAlternativePath(adjacentName, new ArrayList<>(path), dp);
	                    allPaths.addAll(paths);
	                }else {
	                	
	                }
	            }
	            break;
	        }
	    }
	    return allPaths;
	}

	// get the index of a city
	private int getIndex(String cityName) {
	    for (int i = 0; i < citys.size(); i++) {
	        if (citys.get(i).getNameCity().equals(cityName)) {//if the city at index i 
	            return i;
	        }
	    }
	    return -1;
	}

// calculate cost for all paths
	private int calculatePathCost(ArrayList<String> path) {
		int cost = 0;
		for (int i = 0; i < path.size() - 1; i++) {
			String cityName = path.get(i);
			String adjacentCityName = path.get(i + 1);
			for (int j = 0; j < citys.size(); j++) {// get index for name city and calculate cost
				if (citys.get(j).getNameCity().equals(cityName)) {
					AdjacentCity adjacentCity = citys.get(j).getAdjacent(adjacentCityName);
					cost += adjacentCity.getPetrolCost() + adjacentCity.getHotelCost();//calculate the total cost by sum petrol cost and hotel cost of the adjacent city
					break;
				}
			}
		}
		return cost;
	}

//sort Routes	
	public static void sortRoutes(ArrayList<Route> routes) {
		for (int i = 0; i < routes.size() - 1; i++) {
			for (int j = i + 1; j < routes.size(); j++) {
				if (routes.get(i).getCost() > routes.get(j).getCost()) {
					Route temp = routes.get(i);//swap 
					routes.set(i, routes.get(j));
					routes.set(j, temp);
				}
			}
		}
	}

}
