package com.example.calsakay_driver;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class FoldingCellListAdapter extends ArrayAdapter<Passenger> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private int openedCellIndex = 0;
    private FindPassengersFragment fgFindPassengers;
    private Context currentContext;
    private Dashboard act;

    public FoldingCellListAdapter(Context context, List<Passenger> objects) {
        super(context, 0, objects);
        this.currentContext = context;
        fgFindPassengers = new FindPassengersFragment();
        if(context instanceof Activity){
            this.act = (Dashboard) context;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get passenger for selected view
        Passenger passenger = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.passenger_list_item, parent, false);

            viewHolder.fcopivPassengerImage = cell.findViewById(R.id.fcopivPassengerImage);
            viewHolder.fcclivPassengerImage = cell.findViewById(R.id.fcclivPassengerImage);
            viewHolder.fcopPassengerEmail = cell.findViewById(R.id.fcopPassengerEmail);
            viewHolder.fccltvPassengerName = cell.findViewById(R.id.fccltvPassengerName);
            viewHolder.fccltvPassengerDropoff = cell.findViewById(R.id.fccltvPassengerDropoff);
            viewHolder.fccltvPassengerPickup = cell.findViewById(R.id.fccltvPassengerPickup);
            viewHolder.fcoptvPassengerName = cell.findViewById(R.id.fcoptvPassengerName);
            viewHolder.fcoptvPassengerPickup = cell.findViewById(R.id.fcoptvPassengerPickup);
            viewHolder.fcoptvPassengerDropoff = cell.findViewById(R.id.fcoptvPassengerDropoff);
            viewHolder.fcoptvPassengerMobileNo = cell.findViewById(R.id.fcoptvPassengerMobileNo);
            viewHolder.fcoptvPassengerJob = cell.findViewById(R.id.fcoptvPassengerJob);
            viewHolder.fcoptvPassengerWaitingTime = cell.findViewById(R.id.fcoptvPassengerWaitingTime);
            viewHolder.fcoptvPassengerAge = cell.findViewById(R.id.fcoptvPassengerAge);
            viewHolder.fcopbtPassengerPickup = cell.findViewById(R.id.fcopbtPassengerPickup);
            viewHolder.fcclbtPassengerPickup = cell.findViewById(R.id.fcclbtPassengerPickup);

            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == passenger)
            return cell;

        viewHolder.fcopivPassengerImage.setImageBitmap(passenger.getImage());
        viewHolder.fcclivPassengerImage.setImageBitmap(passenger.getImage());
        viewHolder.fccltvPassengerName.setText(passenger.getFirstname() + " " + passenger.getLastname());
        viewHolder.fccltvPassengerDropoff.setText(passenger.getDropoffLocation());
        viewHolder.fccltvPassengerPickup.setText(passenger.getPickupLocation());
        viewHolder.fcoptvPassengerName.setText(passenger.getFirstname() + " " + passenger.getLastname());
        viewHolder.fcoptvPassengerPickup.setText(passenger.getPickupLocation());
        viewHolder.fcoptvPassengerDropoff.setText(passenger.getDropoffLocation());
        viewHolder.fcoptvPassengerMobileNo.setText(passenger.getMobileNumber());
        viewHolder.fcoptvPassengerJob.setText(passenger.getJob());
        viewHolder.fcoptvPassengerWaitingTime.setText(String.valueOf(passenger.getWaitingTime()));
        viewHolder.fcoptvPassengerAge.setText(String.valueOf(passenger.getAge()));
        viewHolder.fcopPassengerEmail.setText(passenger.getEmail());

        viewHolder.fcclbtPassengerPickup.setOnClickListener(view -> {
            openPassengerAcceptedFragment(passenger);
        });

        viewHolder.fcopbtPassengerPickup.setOnClickListener(view -> {
            openPassengerAcceptedFragment(passenger);
        });

        FoldingCell finalCell = cell;
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalCell.toggle(false);
                openedCellIndex = position;
            }
        });
        return cell;
    }

    private void openPassengerAcceptedFragment(Passenger passenger){
        act.setAccepted(true, passenger);
    }

    public int getOpenedCellIndex(){
        return this.openedCellIndex;
    }

    @Nullable
    @Override
    public Passenger getItem(int position) {
        return super.getItem(position);
    }


    // View lookup cache
    private static class ViewHolder {

        ImageView fcopivPassengerImage, fcclivPassengerImage;
        Button fcopbtPassengerPickup, fcclbtPassengerPickup;
        TextView fcoptvPassengerName,fcoptvPassengerJob,fcoptvPassengerMobileNo,fcopPassengerEmail,fcoptvPassengerAge,fcoptvPassengerPickup,fcoptvPassengerDropoff,fcoptvPassengerWaitingTime,fccltvPassengerName,fccltvPassengerPickup,fccltvPassengerDropoff;

    }
}