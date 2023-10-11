import java.util.*;

/**
 * This class implements the SocialConnections interface and represents a social network graph implemented with an adjacency list.
 * A HashMap is used to store the social network.
 * The key is the name of the person and the value is a HashSet of the person's friends.
 * @author Riham Otman
 * @version 1.0
 * @since 2023-04-11
 */
public class SocialNetwork implements SocialConnections{
    HashMap<String, HashSet<String>> connections;
    SocialNetwork(){
        connections = new HashMap<>();
    }

    /**This method takes in a String name and returns true
     if it finds that the person exists in the map or false otherwise.
     * @param name the name of the individual
     * @return true if person is found, false otherwise.
     */
    @Override
    public boolean addPerson(String name) {
        if(connections.containsKey(name)){
            return false;
        }
        connections.put(name, new HashSet<String>());
        return true;
    }

    /**
     * Removes a person from the social graph (remember to remove the references!)
     * @param name the name of the individual
     * @throws PersonNotFoundException if the person is not in the graph.
     */
    @Override
    public boolean removePerson(String name) throws PersonNotFoundException {
        if(!connections.containsKey(name)){
            throw new PersonNotFoundException(name + " is not found in the social network");
        }
        for(HashSet<String> friends : connections.values()){
            friends.remove(name);
        }
        return true;
    }

    /**
     * Connects two people in the social graph.
     * The method will return without changing the graph if firstPerson == secondPerson or if a connection already exist between both
     * @param firstPerson the name of the first individual
     * @param secondPerson the name of the second individual
     * @throws PersonNotFoundException if any of the two names are not present in the graph. The exception message should read "<person name> not found"
     */
    @Override
    public void connectPeople(String firstPerson, String secondPerson) throws PersonNotFoundException {
        if (!connections.containsKey(firstPerson)) {
            throw new PersonNotFoundException(firstPerson + " is not found in the social network ");
        }
        if (!connections.containsKey(secondPerson)) {
            throw new PersonNotFoundException(secondPerson + " is not found in the social network");
        }
        if(connections.containsKey(firstPerson)){
            connections.get(firstPerson).add(secondPerson);
            connections.get(secondPerson).add(firstPerson);
        }
    }

    /**
     * Returns a sorted list (A-Z) of connections (1st degree only)
     * @param name the name of the person we want the list
     * @return a sorted list containing the connections
     * @throws PersonNotFoundException if the person is not in the graph.
     */
    @Override
    public List<String> getConnections(String name) throws PersonNotFoundException {
        if(!connections.containsKey(name)){
            throw new PersonNotFoundException(name + " is not found in the social network");
        }
        ArrayList<String> friends = new ArrayList<>(connections.get(name));
        Collections.sort(friends);
        return friends;

    }

    /**
     * @param firstPerson  name of the first person
     * @param secondPerson name of the second person
     * @return int representing minimum degree of separation  between both people
     * @throws PersonNotFoundException if any of the two persons names are not in the graph
     */
    @Override
    public int getMinimumDegreeOfSeparation(String firstPerson, String secondPerson) throws PersonNotFoundException {

        if (!connections.containsKey(firstPerson)) {
            throw new PersonNotFoundException(firstPerson + " is not found in the social network");
        }
        if (!connections.containsKey(secondPerson)) {
            throw new PersonNotFoundException(secondPerson + " is not found in the social network");
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        int degree = 0;

        queue.offer(firstPerson);

        while (!(queue.isEmpty())){
            int peopleInCurrentLevel = queue.size();
            for(int i = 0; i < peopleInCurrentLevel; i++){
                String currentPerson = queue.poll();
                if (currentPerson.equals(secondPerson)) {
                    return degree;
                }
                for(String friend: connections.get(currentPerson)){
                    if(!visited.contains(friend)){
                        visited.add(friend);
                        queue.offer(friend);
                    }
                }
            }
            degree++;
        }

        return degree;
    }

    /**
     * Gets a list (sorted A-z) of the connections of a given individual up to a certain degree of separation
     * @param name the person's name
     * @param maxLevel the max level (1-n) where 1 is same as getConnections. Value is inclusive.
     * @return a sorted list containing the connections
     * @throws PersonNotFoundException if the person is not in the graph.
     */
    @Override
    public List<String> getConnectionsToDegree(String name, int maxLevel) throws PersonNotFoundException {
        if (!connections.containsKey(name)) {
            throw new PersonNotFoundException(name + " is not found in the social network");
        }

        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> degrees = new HashMap<>();

        queue.offer(name);
        visited.add(name);
        degrees.put(name, 0);

        while (!queue.isEmpty()) {
            String currentPerson = queue.poll();
            int currentDegree = degrees.get(currentPerson);

            if (currentDegree > maxLevel) {
                break;
            }

            if (currentDegree > 0) {
                result.add(currentPerson);
            }

            for (String friend : connections.get(currentPerson)) {
                if (!visited.contains(friend)) {
                    visited.add(friend);
                    queue.offer(friend);
                    degrees.put(friend, currentDegree + 1);
                }
            }
        }

        Collections.sort(result);
        return result;
    }

    /**
     * Determines if everyone in the graph is connected to everyone else (that is, the graph is connected).
     * This method has an undefined behavior if the social graph is empty.
     * @return true if there is a path to everyone;
     */
    @Override
    public boolean areWeAllConnected() {

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(connections.keySet().iterator().next());

        while(!queue.isEmpty()){
            String currentPerson = queue.poll();
            visited.add(currentPerson);
            for(String friend: connections.get(currentPerson)){
                if(!visited.contains(friend)){
                    queue.add(friend);
                }
            }
        }
        return visited.size() == connections.size();
    }
}
