package com.example.calsakay_driver;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

// TODO: BAGUHIN MO TO. DAPAT DETAILS NI PASSENGER NAKALAGAY RITO

public class PassengerAcceptedFragment extends Fragment {
    private int userId, rideTraceId, rideStatus;
    private boolean droppedOff = false;
    String[] rideStatusText = {
            "Picking up your passenger",
            "On the way to the destination",
            "Passenger has been dropped off",
    };
    private ImageView ivPassengerImage;
    private FloatingActionMenu fmPassengerAcceptedMenu;
    private FloatingActionButton fbPassengerAcceptedMenuItem1, fbPassengerAcceptedMenuItem2;
    private TextView tvRideStatus, tvPassengerName, tvPassengerMobileNumber, tvPassengerJob, tvPassengerAge, tvPassengerEmail;
    private Button btPassengerDroppedoff;
    private Context currentContext;
    private Dashboard currentActivity;
    private ArrayList<String> rideTraceInfo;
    private Messages messageData;
    private Passenger passenger;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.ivPassengerImage = view.findViewById(R.id.ivPassengerImage);
        this.fmPassengerAcceptedMenu = view.findViewById(R.id.fmPassengerAcceptedMenu);
        this.fbPassengerAcceptedMenuItem1 = view.findViewById(R.id.fbPassengerAcceptedMenuItem1);
        this.fbPassengerAcceptedMenuItem2 = view.findViewById(R.id.fbPassengerAcceptedMenuItem2);
        this.tvRideStatus = view.findViewById(R.id.tvRideStatus);
        this.tvPassengerName = view.findViewById(R.id.tvPassengerName);
        this.tvPassengerMobileNumber = view.findViewById(R.id.tvPassengerMobileNumber);
        this.tvPassengerJob = view.findViewById(R.id.tvPassengerJob);
        this.tvPassengerAge = view.findViewById(R.id.tvPassengerAge);
        this.tvPassengerEmail = view.findViewById(R.id.tvPassengerEmail);
        this.btPassengerDroppedoff = view.findViewById(R.id.btPassengerDroppedoff);
        this.currentActivity = (Dashboard) getActivity();
        this.userId = Integer.parseInt(currentActivity.getUserData().get(0)[0]);

        DatabaseAccess dbUpdate = new DatabaseAccess(currentContext);
        dbUpdate.executeNonQuery("UPDATE `ride_trace` SET `driver` = " + this.userId + " WHERE `ride_trace`.`trace_id` = '" + passenger.getRideTraceId() + "'");
        DatabaseAccess dbPickup = new DatabaseAccess(currentContext);
        dbPickup.executeNonQuery("UPDATE `ride_trace` SET `status` = '2' WHERE `ride_trace`.`trace_id` = '" + passenger.getRideTraceId() + "'");
        DatabaseAccess dbsAccess = new DatabaseAccess(currentContext);
        dbsAccess.executeQuery("SELECT calsakay_tbl_users.user_image AS chatmateImage, " +
                "CONCAT(calsakay_tbl_users.first_name, ' ', calsakay_tbl_users.last_name) AS threadName, " +
                "IF(messages.sender = " + this.userId + ", 'outgoing', 'ingoing') AS messageType, " +
                "messages.sender, messages.reciever, messages.message_id, messages.message, messages.read, messages.time, messages.read, 'MESSAGE' as Data " +
                "FROM `messages` JOIN calsakay_tbl_users ON " +
                "IF(messages.sender = " + this.userId + ", messages.reciever = calsakay_tbl_users.id, messages.sender = calsakay_tbl_users.id)" +
                "WHERE (`reciever` = " + this.userId + " AND `sender` = " + this.passenger.getPassengerId() + ") " +
                "OR (`reciever` = " + this.passenger.getPassengerId() + " AND `sender` = " + userId + ") " +
                "ORDER BY message_id DESC LIMIT 1");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_passenger_accepted, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.currentContext = context;
        this.passenger = getArguments().getParcelable("passenger");
        getArguments().clear();
    }



    private Date convertToDateObject(String mysqlString) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
        Date date = formatter.parse(mysqlString);
        return date;
    }

    public void setRideTraceInfo(List<String[]> data){
        rideTraceInfo = new ArrayList<>();
        for(String col : data.get(0)){
            this.rideTraceInfo.add(col);
        }

        this.fbPassengerAcceptedMenuItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoConvo = new Intent(currentContext, Conversation.class);
                gotoConvo.putExtra("messageData", messageData);
                currentContext.startActivity(gotoConvo);
            }
        });

        this.btPassengerDroppedoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseAccess dbAccess = new DatabaseAccess(currentContext);
                dbAccess.executeNonQuery("INSERT INTO ride_trace SET " +
                        "trace_id = '" + rideTraceInfo.get(1) +
                        "', passenger = " + rideTraceInfo.get(3) +
                        ", driver = " + rideTraceInfo.get(4) +
                        ", persons = " + rideTraceInfo.get(5) +
                        ", pickup = " + rideTraceInfo.get(6) +
                        ", dropoff = " + rideTraceInfo.get(7) +
                        ", status = 5"
                );
                new SweetAlertDialog(currentContext, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Dropped Off")
                        .setContentText("Your passenger has been dropped off in their destination. Thank you for using CALSAKAY! Stay safe!")
                        .setConfirmText("Ok")
                        .showCancelButton(false)
                        .setCustomImage(getResources().getDrawable(R.drawable.ic_dialog_dropped_off))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                            }
                        })
                        .show();
                droppedOff = true;
                currentActivity.droppedOff();
            }
        });

        this.fbPassengerAcceptedMenuItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(currentContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Cancel Ride")
                        .setContentText("Are you sure you want to cancel your ride?")
                        .setCancelText("No")
                        .setConfirmText("Yes.")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                DatabaseAccess dbAccess = new DatabaseAccess(currentContext);
                                dbAccess.executeNonQuery("INSERT INTO ride_trace SET " +
                                        "trace_id = '" + rideTraceInfo.get(1) +
                                        "', passenger = " + rideTraceInfo.get(3) +
                                        ", driver = " + rideTraceInfo.get(4) +
                                        ", persons = " + rideTraceInfo.get(5) +
                                        ", pickup = " + rideTraceInfo.get(6) +
                                        ", dropoff = " + rideTraceInfo.get(7) +
                                        ", status = 6"
                                );
                                currentActivity.droppedOff();
                                sweetAlertDialog.cancel();
                            }
                        })
                        .show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void initializeMessageDetails(){
        this.tvRideStatus.setText(rideStatusText[0]);
        this.ivPassengerImage.setImageBitmap(passenger.getImage());
        this.tvPassengerMobileNumber.setText(this.tvPassengerMobileNumber.getText() + passenger.getMobileNumber());
        this.tvPassengerJob.setText(this.tvPassengerJob.getText() + passenger.getJob());
        this.tvPassengerAge.setText(this.tvPassengerAge.getText() + String.valueOf(passenger.getAge()));
        this.tvPassengerEmail.setText(this.tvPassengerEmail.getText() + passenger.getEmail());
        this.tvPassengerName.setText(this.tvPassengerName.getText() + passenger.getLastname() + passenger.getLastname());

        DatabaseAccess dbAccess = new DatabaseAccess(currentContext);
        dbAccess.executeQuery("SELECT *, 'RIDE_TRACE' as Data FROM ride_trace WHERE trace_id = '" + passenger.getRideTraceId() + "'");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-d HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try {
            this.messageData = new Messages(passenger.getPassengerId(),
                    userId,
                    convertToDateObject(dtf.format(now)),
                    "",
                    0,
                    passenger.getFirstname() + passenger.getLastname(),
                    "ingoing",
                    true,
                    this.passenger.getPassengerId(),
                    this.userId);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    handler.postDelayed(this, 5000);
                    new CheckStatus().execute();
                }
            }, 3000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    class CheckStatus extends AsyncTask<Void, Void, Void> {
        Statement statement;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://163.44.242.10:3306/feqxsxpi_calsakay?characterEncoding=latin1","feqxsxpi_root", "UCC2021bsitKrazy");
                statement = connection.createStatement();
                rideStatus = 2;

                ResultSet resultSet = statement.executeQuery("SELECT status FROM ride_trace WHERE trace_id = '" + rideTraceInfo.get(1) + "' ORDER BY id DESC LIMIT 1");
                while(resultSet.next()){
                    rideStatus = resultSet.getInt("status");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            switch (rideStatus){
                case 2:
                    tvRideStatus.setText(rideStatusText[0]);
                    break;
                case 3:
                    tvRideStatus.setText(rideStatusText[1]);
                    break;
                case 4:
                    tvRideStatus.setText(rideStatusText[1]);
                    btPassengerDroppedoff.setVisibility(View.VISIBLE);
                    break;
            }
            super.onPostExecute(unused);
        }
    }
}