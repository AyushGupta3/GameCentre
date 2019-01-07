package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * The account manager class handles user information
 */
public class AccountManager {

    /**
     * A map of users. The key is the username. The value corresponding to the Key is the User
     * corresponding to the username.
     */
    private Map<String, User> userList = new HashMap<>();

    /**
     * A static variable storing an instance of this class to
     * follow the Singleton design pattern.
     */
    private static AccountManager accountManager = new AccountManager();

    /**
     * The name of the file the bestScore and the userWithBestScore is saved.
     */
    private final String fileName = "AccountManager_save_file.ser";

    /**
     * The return states for successful status of user registration
     */
    static final int SUCCESSFUL_REGISTER = 2;

    /**
     * The return states for already existing status of user registration
     */
    static final int ALREADY_EXISTS = 0;

    /**
     * The return states for invalid username status of user registration
     */
    private static final int INVALID_USERNAME = 1;

    /**
     * The user that is currently logged in.
     */
    private User currentUser;

    /**
     * Return the user that is currently logged in.
     *
     * @return the user that is currently logged in.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Set the user that is currently logged in.
     *
     * @param currentUser the user that is currently logged in.
     */
    void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Private default constructor so no new instance can be created.
     */
    private AccountManager() {
    }

    /**
     * A public static method which returns the instance of account manager,
     * this is to follow the singleton design pattern.
     *
     * @return the single AccountManager instance.
     */
    public static AccountManager getInstance() {
        return accountManager;
    }

    /**
     * This will register a new user to the database, assuming the desired username does not
     * already exist. Also, the username cannot contain the character "_".
     *
     * @param username Username to be registered
     * @param password Password to be registered
     * @return 0 if the username already exists, 1 if the username is invalid, and 2 if the
     * registration is successful.
     */
    int registerUser(String username, String password) {
        String lowercaseUsername = username.toLowerCase();
        if (userList.containsKey(lowercaseUsername)) {
            return ALREADY_EXISTS;
        } else if (lowercaseUsername.contains("_") || lowercaseUsername.contains(" ")) {
            return INVALID_USERNAME;
        } else {
            User newUser = new User(lowercaseUsername, password);
            userList.put(lowercaseUsername, newUser);
            return SUCCESSFUL_REGISTER;
        }
    }

    /**
     * Check if the username and password entered exists in the database, i.e. is a
     * valid account.
     *
     * @param username Username to be checked
     * @param password Password to be checked
     * @return whether or not the account information is correct.
     */
    boolean isValidAccount(String username, String password) {
        String lowercaseUsername = username.toLowerCase();
        return userList.containsKey(lowercaseUsername) && userList.get(lowercaseUsername).getPassword().equals(password);
    }

    /**
     * Retrieve the User with the username
     *
     * @param username the username
     * @return the User with the username
     */
    User getUser(String username) {
        return userList.get(username.toLowerCase());
    }

    /**
     * Saves the list of users
     *
     * @param context the context of the current application.
     */
    public void saveUsers(Context context) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(userList);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Loads the list of users
     *
     * @param context the context of the current application.
     */
    @SuppressWarnings("unchecked")
    void loadUsers(Context context) {
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userList = (Map<String, User>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("AccountManager", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("AccountManager", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("AccountManager", "File contained unexpected data type: " +
                    e.toString());
        }
    }
}