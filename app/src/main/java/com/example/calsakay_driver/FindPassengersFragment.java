package com.example.calsakay_driver;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FindPassengersFragment extends Fragment{

    private int userId;
    private Context currentContext;
    private Dashboard currentActivity;
    ListView lvPassengerContainerList;
    ArrayList<Passenger> passengerList;
    FoldingCellListAdapter fcAdapter;
    private final Handler handler = new Handler();
    private Runnable refreshPassengerList = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 20000);
            new FindPassengers().execute();
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.currentActivity = (Dashboard) getActivity();
        this.userId = Integer.parseInt(currentActivity.getUserData().get(0)[0]);
        this.lvPassengerContainerList = view.findViewById(R.id.lvFindPassengerContainer);
        passengerList = new ArrayList<>();

        handler.postDelayed(refreshPassengerList, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_passengers, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.currentContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        handler.removeCallbacks(refreshPassengerList);
    }

    class FindPassengers extends AsyncTask<Void, Void, Void> {
        boolean passengerAccepted;
        String error;
        Statement statement;

        private Date convertToDateObject(String mysqlString, int dateType) throws ParseException {
            DateFormat formatter;
            switch (dateType){
                case 1:
                    formatter = new SimpleDateFormat("yyyy-MM-d");
                    break;
                case 2:
                    formatter = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + dateType);
            }

            Date date = formatter.parse(mysqlString);
            return date;
        }

        private Bitmap convertToBitmap(String streamData){
            InputStream stream = new ByteArrayInputStream(Base64.decode(streamData.getBytes(), Base64.DEFAULT));
            Bitmap imageBitmap = BitmapFactory.decodeStream(stream);
            return imageBitmap;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://163.44.242.10:3306/feqxsxpi_calsakay?characterEncoding=latin1","feqxsxpi_root", "UCC2021bsitKrazy");
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT *, pickupLocation.location AS pickupLocation, dropoffLocation.location AS dropoffLocation FROM ride_trace JOIN calsakay_tbl_users ON ride_trace.passenger = calsakay_tbl_users.id JOIN locations AS pickupLocation ON ride_trace.pickup = pickupLocation.id JOIN locations AS dropoffLocation ON ride_trace.dropoff = dropoffLocation.id WHERE ride_trace.driver IS NULL");
                passengerList.clear();
                while(resultSet.next()){
                    passengerList.add(new Passenger(
                            resultSet.getInt("passenger"),
                            resultSet.getString("trace_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            convertToDateObject(resultSet.getString("birthday"), 1),
                            resultSet.getString("gender"),
                            resultSet.getString("medical_job"),
                            resultSet.getString("mobile_number"),
                            resultSet.getString("company_name"),
                            resultSet.getString("company_address"),
                            resultSet.getString("company_number"),
                            resultSet.getString("email"),
                            convertToBitmap(resultSet.getString("user_image")),
                            convertToDateObject(resultSet.getString("date_joined"), 2),
                            resultSet.getString("dropoffLocation"),
                            resultSet.getInt("dropoff"),
                            resultSet.getString("pickupLocation"),
                            resultSet.getInt("pickup"),
                            convertToDateObject(resultSet.getString("time"), 2)
                    ));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            fcAdapter = new FoldingCellListAdapter(getActivity(), passengerList);
            lvPassengerContainerList.setAdapter(fcAdapter);

        }
    }
}