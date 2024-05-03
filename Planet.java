import java.util.ArrayList;

/* The `Planet` class represents a celestial body within a planetary system, along with its associated moons.
 - It includes methods to add moons, retrieve a moon by name, and determine the closest and furthest moons.
 - The `toString` method generates a formatted string detailing the planet's name and its moons, providing a
 comprehensive overview of the planetary system.
 */
public class Planet {

    // Instance of private fields.
    private String planetName; // Name of Planet
    private int correctionFactor; // Correction facto for moon orbital period calculation
    private ArrayList<Moon> moonList = new ArrayList<>(); // List to store associated moons

    // Constructor initialising a planet with name and correction factor.
    public Planet(String name, int correctionFactor) {
        planetName = name;
        this.correctionFactor = correctionFactor;
    }

    // Method to add a moon to a planet moon list with the moon name and distance.
    public void addMoon(String name, double distance) {
        moonList.add(new Moon(name, distance, this));
    }

    // Method to add a new moon to the planet's moon list with additional parameters: mass and diameter.
    public void addMoon(String name, double distance, double mass, double diameter) {
        moonList.add(new Moon(name, distance, mass, diameter, this));
    }

    // Getter method to retrieve planet's correction factor.
    public int getCorrectionFactor() {
        return correctionFactor;
    }

    // Method to get a moon from the moon list by its name.
    public Moon getMoonByName(String moonName) {
        for (Moon elem : moonList) {
            if (elem.getName().equals(moonName)) {
                return elem; // Return 'Moon' object if found,...
            }
        }
        return null; // ...otherwise return null
    }

    // Methods to find and return the closest & furthest moon to the planet
    public Moon closest() {
        Moon min = null;

        // As long as there are moon in the planet's moon list...
        for (Moon elem : moonList) {
            // ...compare the distance of the moons to find the closest moon to the planet
            if (null == min || elem.getDistance() < min.getDistance()) {
                min = elem;
            }
        }
        return min;
    }

    public Moon furthest() {
        Moon max = null;
        for (Moon elem : moonList) {
            if (null == max || elem.getDistance() > max.getDistance()) {
                max = elem;
            }
        }
        return max;
    }

    // Override of the toString method to provide a formatted string representation of the planet.
    @Override
    public String toString(){
        String planets = "Planet " + planetName + " has moons:\n";
        for (Moon elem : moonList) {
            planets += elem.toString();
        }
        return planets; // Returns a string containing info about the planet and its moon.
    }
}
