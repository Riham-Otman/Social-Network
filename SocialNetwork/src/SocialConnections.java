import java.util.List;

public interface SocialConnections {

    /**
     * Adds a new person to the social graph. Names are unique i.e, can be used as keys
     * @param name the name of the individual
     * @return returns true if the person was added; false if the person was already in the graph
     */
    boolean addPerson(String name);

    /**
     * Removes a person from the social graph (remember to remove the references!)
     * @param name the name of the individual
     * @throws PersonNotFoundException if the person is not in the graph.
     */
    boolean removePerson(String name) throws PersonNotFoundException;

    /**
     * Connects two people in the social graph.
     * The method will return without changing the graph if firstPerson == secondPerson or if a connection already exist between both
     * @param firstPerson the name of the first individual
     * @param secondPerson the name of the second individual
     * @throws PersonNotFoundException if any of the two names are not present in the graph. The exception message should read "<person name> not found"
     */
    void connectPeople(String firstPerson, String secondPerson) throws PersonNotFoundException, PersonNotFoundException;

    /**
     * Returns a sorted list (A-Z) of connections (1st degree only)
     * @param name the name of the person we want the list
     * @return a sorted list containing the connections
     * @throws PersonNotFoundException if the person is not in the graph.
     */
    List<String> getConnections(String name) throws PersonNotFoundException;

    /**
     * Gets the social degree of separation between two individuals.
     * Individuals may have multiple paths connecting them; this method should look for the smallest path
     * @param firstPerson the name of the first individual
     * @param secondPerson the name of the second individual
     * @return an integer representing the degree of separation between both people; -1 if they are not connected
     * @throws PersonNotFoundException if any of the two names are not present in the graph. The exception message should read "<person name> not found"
     */
    int getMinimumDegreeOfSeparation(String firstPerson, String secondPerson) throws PersonNotFoundException;

    /**
     * Gets a list (sorted A-z) of the connections of a given individual up to a certain degree of separation
     * @param name the person's name
     * @param maxLevel the max level (1-n) where 1 is same as getConnections. Value is inclusive.
     * @return a sorted list containing the connections
     * @throws PersonNotFoundException if the person is not in the graph.
     */
    List<String> getConnectionsToDegree(String name, int maxLevel) throws PersonNotFoundException;

    /**
     * Determines if everyone in the graph is connected to everyone else (that is the graph is connected)
     * This method has an undefined behaviour if the social graph is empty
     * @return true if there is path to everyone;
     */
    boolean areWeAllConnected();
}
