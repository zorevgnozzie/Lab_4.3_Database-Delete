package com.edu.tarc.lab42database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;

//TODO 6: Create a repository class to manage data query thread

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);

        userDao = db.userDao();
        allUsers = userDao.loadAllUsers();
    }

    //Update change to the LiveDate (View Model)
    LiveData<List<User>> getAllUsers(){
        return allUsers;
    }
    
    public void insertUser(User user){
        new insertAsyncTask(userDao).execute(user);
    }
    public void deleteUser(User user)  {
        new deleteAsyncTask(userDao).execute(user);
    }

    //<Param, Progress, Results>
    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public insertAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        deleteAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(final User... users) {
            userDao.deleteUser(users[0]);
            return null;
        }

    }

}
