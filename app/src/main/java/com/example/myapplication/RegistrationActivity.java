package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.models.CityModel;
import com.example.myapplication.models.CountryModel;
import com.example.myapplication.models.RegistrationResponseModel;
import com.example.myapplication.models.StateModel;
import com.example.myapplication.network.NetworkClient;
import com.example.myapplication.network.NetworkService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    Spinner countrySpinner, stateSpinner, citySpinner;
    EditText inputFirstName, inputLastName, inputMobile, inputEmail, inputPassword, inputConfirmPassword;
    RadioButton radioMale, radioFemale;
    Button buttonRegister;
    boolean isGenderSelected;
    String selectedGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        inputFirstName = findViewById(R.id.input_first_name);
        inputLastName = findViewById(R.id.input_last_name);
        inputMobile = findViewById(R.id.input_mobile);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        inputConfirmPassword = findViewById(R.id.input_confirm_password);
        radioMale = findViewById(R.id.radio_male);
        radioFemale = findViewById(R.id.radio_female);
        buttonRegister = findViewById(R.id.button_register);

        countrySpinner = findViewById(R.id.country_spinner);
        stateSpinner = findViewById(R.id.state_spinner);
        citySpinner = findViewById(R.id.city_spinner);

        radioMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isGenderSelected = true;
                    selectedGender = "Male";
                }
            }
        });
        radioFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isGenderSelected = true;
                    selectedGender = "Female";
                }
            }
        });

        initCountrySpinner();
        ArrayList<StateModel> states = new ArrayList<>();
        states.add(new StateModel("Select State"));
        ArrayAdapter<StateModel> statesAdapter =
                new ArrayAdapter<>(RegistrationActivity.this, android.R.layout.simple_spinner_item, states);
        statesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        stateSpinner.setAdapter(statesAdapter);

        ArrayList<CityModel> cities = new ArrayList<>();
        cities.add(new CityModel("Select City"));


        ArrayAdapter<CityModel> citiesAdapter =
                new ArrayAdapter<>(RegistrationActivity.this, android.R.layout.simple_spinner_item, cities);
        citiesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        citySpinner.setAdapter(citiesAdapter);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputFirstName.getText().toString().equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Enter first name",Toast.LENGTH_SHORT).show();
                } else if(inputLastName.getText().toString().equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Enter last name",Toast.LENGTH_SHORT).show();
                } else if(!isGenderSelected) {
                    Toast.makeText(RegistrationActivity.this, "Select gender",Toast.LENGTH_SHORT).show();
                } else if(inputMobile.getText().toString().equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Enter mobile no.",Toast.LENGTH_SHORT).show();
                } else if(inputMobile.getText().toString().length() < 10) {
                    Toast.makeText(RegistrationActivity.this, "Enter valid mobile no.",Toast.LENGTH_SHORT).show();
                } else if(countrySpinner.getSelectedItem().toString().equals("Select Country")){
                    Toast.makeText(RegistrationActivity.this, "Select country.",Toast.LENGTH_SHORT).show();
                } else if(stateSpinner.getSelectedItem().toString().equals("Select State")){
                    Toast.makeText(RegistrationActivity.this, "Select state.",Toast.LENGTH_SHORT).show();
                } else if(citySpinner.getSelectedItem().toString().equals("Select City")){
                    Toast.makeText(RegistrationActivity.this, "Select city.",Toast.LENGTH_SHORT).show();
                } else if(inputEmail.getText().toString().equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Enter email",Toast.LENGTH_SHORT).show();
                } else if(!emailValidator(inputEmail.getText().toString())) {
                    Toast.makeText(RegistrationActivity.this, "Enter valid email",Toast.LENGTH_SHORT).show();
                } else if(inputPassword.getText().toString().equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Enter password",Toast.LENGTH_SHORT).show();
                } else if(inputConfirmPassword.getText().toString().equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Confirm password",Toast.LENGTH_SHORT).show();
                } else if (!inputPassword.getText().toString().equals(inputConfirmPassword.getText().toString())) {
                    Toast.makeText(RegistrationActivity.this, "Password must be same",Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("first_name", inputFirstName.getText().toString());
                    params.put("last_name", inputLastName.getText().toString());
                    params.put("gender", selectedGender);
                    params.put("mobile", inputMobile.getText().toString());
                    params.put("country", countrySpinner.getSelectedItem().toString());
                    params.put("state", stateSpinner.getSelectedItem().toString());
                    params.put("city", citySpinner.getSelectedItem().toString());
                    params.put("email", inputEmail.getText().toString());
                    params.put("password", inputPassword.getText().toString());

                    register(params);
                }
            }
        });
    }

    private void initCountrySpinner() {
        ArrayList<CountryModel> countries = new ArrayList<>();
        countries.add(new CountryModel("Select Country"));
        countries.add(new CountryModel("India"));
        countries.add(new CountryModel("United State"));
        countries.add(new CountryModel("Australia"));
        countries.add(new CountryModel("United Kingdom"));

        ArrayAdapter<CountryModel> countriesAdapter =
                new ArrayAdapter<>(RegistrationActivity.this, android.R.layout.simple_spinner_item, countries);
        countriesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        countrySpinner.setAdapter(countriesAdapter);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    ArrayList<StateModel> states = new ArrayList<>();
                    states.add(new StateModel("Select State"));
                    states.add(new StateModel("Gujarat"));
                    states.add(new StateModel("Maharashtra"));
                    states.add(new StateModel("Punjab"));

                    ArrayAdapter<StateModel> statesAdapter =
                            new ArrayAdapter<>(RegistrationActivity.this, android.R.layout.simple_spinner_item, states);
                    statesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                    stateSpinner.setAdapter(statesAdapter);

                    stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 1) {
                                ArrayList<CityModel> cities = new ArrayList<>();
                                cities.add(new CityModel("Select City"));
                                cities.add(new CityModel("Amreli"));
                                cities.add(new CityModel("Rajkot"));
                                cities.add(new CityModel("Ahmedabad"));
                                cities.add(new CityModel("Vadodara"));

                                ArrayAdapter<CityModel> citiesAdapter =
                                        new ArrayAdapter<>(RegistrationActivity.this, android.R.layout.simple_spinner_item, cities);
                                citiesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                                citySpinner.setAdapter(citiesAdapter);
                            } else if (position == 2) {
                                ArrayList<CityModel> cities = new ArrayList<>();
                                cities.add(new CityModel("Select City"));
                                cities.add(new CityModel("Mumbai"));
                                cities.add(new CityModel("Pune"));


                                ArrayAdapter<CityModel> citiesAdapter =
                                        new ArrayAdapter<>(RegistrationActivity.this, android.R.layout.simple_spinner_item, cities);
                                citiesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                                citySpinner.setAdapter(citiesAdapter);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (position == 2) {
                    ArrayList<StateModel> states = new ArrayList<>();
                    states.add(new StateModel("Select State"));
                    states.add(new StateModel("Texas"));
                    states.add(new StateModel("California"));
                    states.add(new StateModel("Maryland"));

                    ArrayAdapter<StateModel> statesAdapter =
                            new ArrayAdapter<>(RegistrationActivity.this, android.R.layout.simple_spinner_item, states);
                    statesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                    stateSpinner.setAdapter(statesAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void register(HashMap<String, String> params) {

        final ProgressDialog progressDialog = new ProgressDialog(RegistrationActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Registering...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<RegistrationResponseModel> registerCall = networkService.register(params);
        registerCall.enqueue(new Callback<RegistrationResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<RegistrationResponseModel> call, @NonNull Response<RegistrationResponseModel> response) {
                    RegistrationResponseModel responseBody = response.body();
                    if (responseBody != null) {
                        if (responseBody.getSuccess().equals("1")) {
                            Toast.makeText(RegistrationActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegistrationActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<RegistrationResponseModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

}