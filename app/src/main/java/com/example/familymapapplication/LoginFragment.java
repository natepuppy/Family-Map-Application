package com.example.familymapapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.familymapapplication.task.EventTask;
import com.example.familymapapplication.task.FamilyDataTask;
import com.example.familymapapplication.task.LoginTask;
import com.example.familymapapplication.task.RegisterTask;

import java.util.HashMap;

import model.Event;
import model.Person;
import request.EventRequestAll;
import request.LoginRequest;
import request.PersonRequestAll;
import request.RegisterRequest;
import result.EventResultAll;
import result.LoginResult;
import result.PersonResultAll;
import result.RegisterResult;


public class LoginFragment extends Fragment implements RegisterTask.Context, LoginTask.Context, FamilyDataTask.Context, EventTask.Context {
    View view ;
    private Button mSignInButton;
    private Button mRegisterButton;
    private EditText mServerHost;
    private EditText mServerPort;
    private EditText mUserName;
    private EditText mPassword;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private RadioGroup mRadioGenderGroup;
    private RadioButton mRadioButton;
    private boolean mRadioButtonChecked = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {  // This is where you do the initialization
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  // This is where you do the view (GUI)
        view = inflater.inflate(R.layout.fragment_login, container, false);

        mServerHost = view.findViewById(R.id.Server_Host_Entry);
        mServerPort = view.findViewById(R.id.Server_Port_Entry);
        mUserName = view.findViewById(R.id.User_Name_Entry);
        mPassword = view.findViewById(R.id.Password_Entry);
        mFirstName = view.findViewById(R.id.First_Name_Entry);
        mLastName = view.findViewById(R.id.Last_Name_Entry);
        mEmail = view.findViewById(R.id.Email_Entry);
        mRadioGenderGroup = view.findViewById(R.id.Group_Radio);

        /** UNCOMMENT FOR FINAL PROJECT!!!!!! **/
        mServerHost.addTextChangedListener(watcher);
        mServerPort.addTextChangedListener(watcher);
        mUserName.addTextChangedListener(watcher);
        mPassword.addTextChangedListener(watcher);
        mFirstName.addTextChangedListener(watcher);
        mLastName.addTextChangedListener(watcher);
        mEmail.addTextChangedListener(watcher);

        // This overrides the radiogroup onCheckListener
        mRadioGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mRadioButtonChecked = true;
                if (mServerHost.getText().toString().length() != 0 && mServerPort.getText().toString().length() != 0 &&
                        mUserName.getText().toString().length() != 0 && mPassword.getText().toString().length() != 0) {
                    if (mFirstName.getText().toString().length() != 0 && mLastName.getText().toString().length() != 0 &&
                            mEmail.getText().toString().length() != 0) {
                        mRegisterButton.setEnabled(true);
                        mSignInButton.setEnabled(true);
                    }
                    else {
                        mSignInButton.setEnabled(true);
                        mRegisterButton.setEnabled(false);
                    }
                }
                else {
                    mSignInButton.setEnabled(false);
                    mRegisterButton.setEnabled(false);
                }
            }
        });


        mSignInButton = view.findViewById(R.id.Sign_In_Button);
        /** SET TO FALSE FOR FINAL PROJECT!!!!!! **/
        mSignInButton.setEnabled(true);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonClicked();
            }
        });

        mRegisterButton = view.findViewById(R.id.Register_Button);
        /** SET TO FALSE FOR FINAL PROJECT!!!!!! **/
        mRegisterButton.setEnabled(true);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerButtonClicked();
            }
        });
        return view;
    }


    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            if (mServerHost.getText().toString().length() != 0 && mServerPort.getText().toString().length() != 0 &&
                    mUserName.getText().toString().length() != 0 && mPassword.getText().toString().length() != 0) {
                if (mFirstName.getText().toString().length() != 0 && mLastName.getText().toString().length() != 0 &&
                        mEmail.getText().toString().length() != 0 && mRadioButtonChecked) {
                    mRegisterButton.setEnabled(true);
                    mSignInButton.setEnabled(true);
                }
                else {
                    mSignInButton.setEnabled(true);
                    mRegisterButton.setEnabled(false);
                }
            }
            else {
                mSignInButton.setEnabled(false);
                mRegisterButton.setEnabled(false);
            }
        }
    };

    private void loginButtonClicked() {
        // get values from user

        /** UNCOMMENT FOR FINAL PROJECT!!!!!! **/
//        String _serverHost = mServerHost.getText().toString();
//        String _serverPort = mServerPort.getText().toString();
//        String _userName = mUserName.getText().toString();
//        String _password = mPassword.getText().toString();

        String _serverHost = "10.0.2.2";
        String _serverPort = "8080";
        String _userName = "nathan_clark";
        String _password = "clark";

        ModelData data = ModelData.getInstance( );
        data.setServerHost(_serverHost);
        data.setServerPort(_serverPort);

        // Create an async task
        LoginRequest request = new LoginRequest(_userName, _password);
        LoginTask task = new LoginTask(this, _serverHost, _serverPort);
        task.execute(request);
    }

    private void registerButtonClicked() {
        // get values from user
        String _serverHost = mServerHost.getText().toString();
        String _serverPort = mServerPort.getText().toString();
        String _userName = mUserName.getText().toString();
        String _password = mPassword.getText().toString();
        String _firstName = mFirstName.getText().toString();
        String _lastName = mLastName.getText().toString();
        String _email = mEmail.getText().toString();

        ModelData data = ModelData.getInstance( );
        data.setServerHost(_serverHost);
        data.setServerPort(_serverPort);

        int selectedId = mRadioGenderGroup.getCheckedRadioButtonId();
        mRadioButton = view.findViewById(selectedId);
        String _gender = mRadioButton.getText().charAt(0) + "";

        // Create an async task
        RegisterRequest request = new RegisterRequest(_userName, _password, _email,
                _firstName, _lastName, _gender);
        RegisterTask task = new RegisterTask(this, _serverHost, _serverPort);
        task.execute(request);
    }

    @Override
    public void onRegisterComplete(RegisterResult result) {
        if (result.getErrorMessage() == null) {
            String authToken = result.getAuthToken();
            String personID = result.getPersonID();
            String userName = result.getUserName();

            // Set ModelData dor singleton class
            ModelData data = ModelData.getInstance( );
            data.setAuthToken(authToken);
            data.setPersonID(personID);
            data.setUserName(userName);

            getFamilyData(authToken);
            ((MainActivity)getActivity()).switchToMapFragment();
        }
        else {
            //Toast.makeText(getActivity(), result.getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onLoginComplete(LoginResult result) {
        if (result.getErrorMessage() == null) {
            String authToken = result.getAuthToken();
            String personID = result.getPersonID();
            String userName = result.getUserName();

            // Set ModelData dor singleton class
            ModelData data = ModelData.getInstance( );
            data.setAuthToken(authToken);
            data.setPersonID(personID);
            data.setUserName(userName);

            getFamilyData(authToken);
            ((MainActivity)getActivity()).switchToMapFragment();
        }
        else {
            //Toast.makeText(getActivity(), result.getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getFamilyData(String authToken) {
        ModelData data = ModelData.getInstance( );
        String host = data.getServerHost();
        String port = data.getServerPort();

        // Create an async task
        PersonRequestAll requestPerson = new PersonRequestAll(authToken);
        FamilyDataTask personTask = new FamilyDataTask(this, host, port);
        personTask.execute(requestPerson);

        EventRequestAll requestEvent = new EventRequestAll(authToken);
        EventTask eventTask = new EventTask(this, host, port);
        eventTask.execute(requestEvent);
    }

//    private void getEventData(String authToken) {
//        ModelData data = ModelData.getInstance( );
//        String host = data.getServerHost();
//        String port = data.getServerPort();
//
//        EventRequestAll requestEvent = new EventRequestAll(authToken);
//        EventTask eventTask = new EventTask(this, host, port);
//        eventTask.execute(requestEvent);
//    }

    @Override
    public void onFamilyDataComplete(PersonResultAll result) {
        System.out.println("Person Result ------------" + result.getData().length);

        if (result.getErrorMessage() == null) {
            ModelData data = ModelData.getInstance( );
            HashMap<String, Person> persons = new HashMap<>();
            for (Person person : result.getData()) {
                persons.put(person.getPersonID(), person);
            }

            data.setPersons(persons);
            Person person = data.getSpecificPerson(data.getPersonID());
            String display = "Welcome " + person.getFirstName() + " " + person.getLastName();
            //Toast.makeText(getActivity(), display, Toast.LENGTH_SHORT).show();
        }
        else {
            ModelData data = ModelData.getInstance( );
            data.setErrorMessage(result.getErrorMessage());
        }
    }

    @Override
    public void onEventTaskComplete(EventResultAll result) {
        System.out.println("Event Result ------------" + result.getData().length);


        if (result.getErrorMessage() == null) {
            ModelData data = ModelData.getInstance( );
            HashMap<String, Event> events = new HashMap<>();
            for (Event event : result.getData()) {
                events.put(event.getEventID(), event);
            }
            data.setEvents(events);
        }
        else {
            ModelData data = ModelData.getInstance( );
            data.setErrorMessage(result.getErrorMessage());
        }
    }
}





























































/*

    public class MainActivity extends AppCompatActivity implements DownloadTask.Context {

        private ProgressBar progressBar;
        private TextView totalSizeTextView;
        private Button downloadButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            progressBar = (ProgressBar)findViewById(R.id.progressBar);

            totalSizeTextView = (TextView)findViewById(R.id.totalSizeTextView);

            downloadButton = (Button)findViewById(R.id.downloadButton);
            downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadButtonClicked();
                }
            });

            resetViews();
        }

        private void resetViews() {
            progressBar.setProgress(0);
            totalSizeTextView.setText("Total Size:");
        }

        private void downloadButtonClicked() {
            try {
                resetViews();

                DownloadTask task = new DownloadTask(this);

                task.execute(new URL("https://home.byu.edu/home/"),
                        new URL("https://www.whitehouse.gov/"),
                        new URL("http://www.oracle.com/index.html"));
            }
            catch (MalformedURLException e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
        }

        @Override
        public void onProgressUpdate(int percent) {
            progressBar.setProgress(percent);
        }

        @Override
        public void onDownloadComplete(long totalBytes) {
            totalSizeTextView.setText("Total Size: " + totalBytes);
        }

    }


*/


//        System.out.println("mServerHost " + _serverHost);
//        System.out.println("mServerPort " + _serverPort);
//        System.out.println("mUserName " + _userName);
//        System.out.println("mPassword " + _password);
//        System.out.println("mFirstName " + _firstName);
//        System.out.println("mLastName " + _lastName);
//        System.out.println("mEmail " + _email);
//        System.out.println("gender ID: " + selectedId);
//        System.out.println("gender Text: " + mRadioButton.getText());
//        System.out.println();
//        System.out.println();
//        System.out.println("---------------");
//        System.out.println();
//        System.out.println();