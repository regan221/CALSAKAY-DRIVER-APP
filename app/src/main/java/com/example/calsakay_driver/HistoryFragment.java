package com.example.calsakay_driver;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView rv_history;
    private HistoryRecViewAdapter historyRecViewAdapter;
    private ArrayList<String> items;
    private DatabaseAccess databaseAccess;
    ArrayList<HistoryModel> historyModels = new ArrayList<>();
    HistoryModel historyModel = new HistoryModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

//        databaseAccess = new DatabaseAccess(getActivity());
//        databaseAccess.executeQuery("SELECT * FROM calsakay_tbl_rides_info");

//        rv_history = view.findViewById(R.id.rv_history);
//        rv_history.setLayoutManager(new LinearLayoutManager(getActivity()));
//        historyRecViewAdapter = new HistoryRecViewAdapter(getActivity(), getHistoryList());
//        historyRecViewAdapter = new HistoryRecViewAdapter(getActivity(), historyModels);
//        rv_history.setAdapter(historyRecViewAdapter);
//        new Connect().execute();
        return view;
    }


//    public class Connect extends AsyncTask<Void, Void, Void> {
//        List<Histories> histories = new ArrayList<>();
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try {
//                Class.forName("com.mysql.jdbc.Driver");
//                Connection connection = DriverManager.getConnection("jdbc:mysql://163.44.242.10:3306/feqxsxpi_calsakay?characterEncoding=latin1", "feqxsxpi_root", "UCC2021bsitKrazy");
//                Statement statement = connection.createStatement();
//                ResultSet rs = statement.executeQuery("SELECT * FROM calsakay_tbl_rides_info");
//                while (rs.next()) {
//                    histories.add(new Histories(
//                            rs.getInt("id"),
//                            rs.getInt("frontliner_id"),
//                            rs.getInt("driver_id"),
//                            rs.getDate("ride_time_start"),
//                            rs.getDate("ride_time_end"),
//                            rs.getString("ride_pickup_point"),
//                            rs.getString("ride_dropoff_point"),
//                            rs.getInt("seat_number"),
//                            rs.getInt("status")
//                    ));
//                }
//            } catch (SQLException e) {
//                Log.d("SQLException ", e.getMessage());
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//                Log.d("Class not found error ", e.getMessage());
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void unused) {
//
//            HistoryRecViewAdapter historyRecViewAdapter = new HistoryRecViewAdapter(getActivity(), histories);
//            rv_history.setAdapter(historyRecViewAdapter);
//            rv_history.setLayoutManager(new LinearLayoutManager(getActivity()));
//            super.onPostExecute(unused);
//        }
//
//        //    public void getHistory(){
////        try {
////            Class.forName("com.mysql.jdbc.Driver");
////            Connection connection = DriverManager.getConnection("jdbc:mysql://163.44.242.10:3306/feqxsxpi_calsakay","feqxsxpi_root", "UCC2021bsitKrazy");
////            Statement statement = connection.createStatement();
////            ResultSet rs = statement.executeQuery("SELECT * FROM calsakay_tbl_rides_info");
////            while(rs.next()){
////                historyModel.setDate("Date 1");
////                historyModel.setPick("Pickup 1");
////                historyModel.setDrop("Drop 1");
////
////                historyModels.add(historyModel);
////            }
////        } catch (SQLException e){
////            Log.d("SQLException ", e.getMessage());
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////            Log.d("Class not found error ", e.getMessage());
////        }
////    }
////    private ArrayList<HistoryModel> getHistoryList() {
////
////        try {
////            Class.forName("com.mysql.jdbc.Driver");
////            Connection connection = DriverManager.getConnection("jdbc:mysql://163.44.242.10:3306/feqxsxpi_calsakay?characterEncoding=latin1","feqxsxpi_root", "UCC2021bsitKrazy");
////            Statement statement = connection.createStatement();
////            ResultSet rs = statement.executeQuery("SELECT * FROM calsakay_tbl_rides_info");
////            while(rs.next()){
////                historyModel.setDate("Date 1");
////                historyModel.setPick("Pickup 1");
////                historyModel.setDrop("Drop 1");
////
////                historyModels.add(historyModel);
////            }
////        } catch (ClassNotFoundException | SQLException e) {
////            e.printStackTrace();
////            Log.d("MysqlError: ", e.getMessage());
////        }
////
////
//////        historyModel.setDate("Date 1");
//////        historyModel.setPick("Pickup 1");
//////        historyModel.setDrop("Drop 1");
//////
//////        historyModels.add(historyModel);
//////
//////        historyModel = new HistoryModel();
//////        historyModel.setDate("Date 2");
//////        historyModel.setPick("Pickup 2");
//////        historyModel.setDrop("Drop 2");
//////
//////        historyModels.add(historyModel);
//////
//////        historyModel = new HistoryModel();
//////        historyModel.setDate("Date 3");
//////        historyModel.setPick("Pickup 3");
//////        historyModel.setDrop("Drop 3");
//////
//////        historyModels.add(historyModel);
////
////        return historyModels;
////
////    }
//    }
}