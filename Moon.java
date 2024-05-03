/*
 * The Moon class represents a moon in a planetary system. It includes constants for mathematical operations,
 * instance variables to store moon properties, and constructors for initializing moon objects with or without
 * known properties. The class calculates the orbital period, density, and composition of the moon based on its
 * distance, mass, diameter, and the correction factor of the associated planet.
 */
public class Moon {

    /*
    Constants:
            * - DENOMINATOR_BY_PI, NUMERATOR_BY_PI: Constants for mathematical operations.
            * - HASH_PRIME_NUMBER, HASH_CODE_PRIME_NUMBER: Constants for generating hash codes.
            * - SCALE_MULTIPLIER, SCI_NOTATION_10E2: Constants for scaling and scientific notation.
            * - ICE_AND_ROCK_MIN_VALUE, ICE_AND_ROCK_MAX_VALUE: Constants for density-based composition determination.
     */
    static final int DENOMINATOR_BY_PI = 3;
    static final int NUMERATOR_BY_PI = 4;
    static final int HASH_PRIME_NUMBER = 7;
    static final int HASH_CODE_PRIME_NUMBER = 79;
    static final double SCALE_MULTIPLIER = Math.pow(10, 2);
    static final double SCI_NOTATION_10E2 = 10E2;
    static final double ICE_AND_ROCK_MIN_VALUE = 0.9;
    static final double ICE_AND_ROCK_MAX_VALUE = 2.0;

    /*
    Instance Variables:
     - moonName: Stores the name of the moon.
     - composition: Stores the composition of the moon (ice, rock, or mixture).
     - distance: Stores the distance of the moon from its planet.
     - period: Stores the orbital period of the moon.
     - mass, density, diameter: Properties of the moon used in calculations.
     - breakTest: A boolean variable used to distinguish between constructors.
    */
    private String moonName;
    private String composition;
    private double distance;
    private double period;
    private double mass;
    private double density;
    private double diameter;

    private boolean breakTest;

    /*Constructors:
            * - Two constructors are provided: one for initializing moons with known distance and an associated planet,
            *   and the other for initializing moons with additional known properties (mass, diameter).
     */
    public Moon(String name, double distance, Planet planet) {
        moonName = name;
        this.distance = distance;

        // Calculate orbital period based on the distance and correction factor of associated planet.
        calculateOrbitalPeriod(planet);

        breakTest = true;
    }

    // Constructor with other known moon properties
    public Moon(String name, double distance, double mass, double diameter, Planet planet) {
        moonName = name;
        this.mass = mass;
        this.distance = distance;
        this.diameter = diameter;

        calculateOrbitalPeriod(planet);

        // Calculates radius, density and composition of the moon.
        calculateOtherProperties();
    }

    /*Methods:
     * - getName(): Getter method for the moon's name.
     * - getDistance(): Getter method for the moon's distance.
     * - calculateOrbitalPeriod(Planet planet): Private method to calculate the orbital period based on distance
     *   and the correction factor of the associated planet.
     * - calculateOtherProperties(): Private method to calculate density, composition, and other properties.
     * - toString(): Override of the toString method to provide a formatted string representation of the moon.
     * - equals(Object moon): Override of the equals method to compare moon objects based on their name and distance.
     * - hashCode(): Override of the hashCode method to generate a hash code for moon objects.
    * */

    // Moon name getter method.
    public String getName() {
        return moonName;
    }

    // Getter method for moon distance.
    public double getDistance() {
        return distance;
    }


    // Private method to calculate orbital period.
    private void calculateOrbitalPeriod(Planet planet) {

        double orbit = Math.sqrt(distance * distance * distance) / planet.getCorrectionFactor();
        period = Math.round(orbit * SCALE_MULTIPLIER) / SCALE_MULTIPLIER;

    }

    // Private method to calculate other properties
    private void calculateOtherProperties() {

        double radius = diameter/2; // Calculate radius by dividing diameter by 2

        // Calculate density mass/pi*4/3(radius^3)*10^2
        density = mass/(Math.PI * NUMERATOR_BY_PI / DENOMINATOR_BY_PI * (radius * radius * radius)) * SCI_NOTATION_10E2;

        // Determines the composition of the moon based on the density.
        if (density < ICE_AND_ROCK_MIN_VALUE) {
            composition = "mostly ice";
        } else if (density > ICE_AND_ROCK_MAX_VALUE) {
            composition = "mostly rock";
        } else {
            composition = "a mixture of rock and ice";
        }
    }

    // Override of the toString method to provide a formatted string representation of the moon.
    @Override
    public String toString() {

        long roundUpDiameter = Math.round(diameter);
        if (breakTest) {

            return moonName + " is " + (int) distance + " km from its planet, and orbits in "
                    + String.format("%.2f", period) + " days\n";

        } else {

            return moonName + " is " + (int) distance + " km from it's planet, orbits in "
                    + String.format("%.2f", period) + " days, has a " + "mass" + " of " + String.format("%.2f", mass)
                    + " * 10e15 kg, a diameter of " + (int) roundUpDiameter + " km, and a density of "
                    + String.format("%.2f", density) + " g/cm^2 - it is probably " + composition + "\n";
        }
    }

    // Override of the equals method to compare moon objects based on their name and distance.
    @Override
    public boolean equals(Object moon) {

        if (!(moon instanceof Moon)) {
            return false;
        } else if (moon == this) {
            return  true;
        } else {
            Moon compareMoons = (Moon) moon;
            return compareMoons.getName().equals(moonName)
                    && compareMoons.getDistance() == distance;
        }
    }

    // Override of the hashCode method to generate a hash code for moon objects.
    @Override
    public int hashCode() {
        int hash = HASH_PRIME_NUMBER;

        // Combines current hash value (hash) with the result of hashing the moonName variable using bitwise AND.
        hash &= HASH_CODE_PRIME_NUMBER + (moonName != null ? moonName.hashCode(): 0);

        //combining the current hash value with the hash of the distance variable using multiplication.
        hash  *= HASH_CODE_PRIME_NUMBER + distance;
        return hash; // returns hash result.
    }

}
