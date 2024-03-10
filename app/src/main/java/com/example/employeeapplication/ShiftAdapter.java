package com.example.employeeapplication;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.ShiftViewHolder> {
    private List<Shift> shiftsList;

    public ShiftAdapter(List<Shift> shiftsList) {
        this.shiftsList = shiftsList;
    }

    @NonNull
    @Override
    public ShiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shift, parent, false);
        return new ShiftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftViewHolder holder, int position) {
        Shift shift = shiftsList.get(position);
        holder.bind(shift);
    }

    @Override
    public int getItemCount() {
        return shiftsList.size();
    }

    public class ShiftViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewEmployeeName, textViewShiftDate, textViewStartTime, textViewEndTime,
                textViewClockInTime, textViewClockOutTime;

        public ShiftViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEmployeeName = itemView.findViewById(R.id.textViewEmployeeName);
            textViewShiftDate = itemView.findViewById(R.id.textViewShiftDate);
            textViewStartTime = itemView.findViewById(R.id.textViewStartTime);
            textViewEndTime = itemView.findViewById(R.id.textViewEndTime);
            textViewClockInTime = itemView.findViewById(R.id.textViewClockInTime);
            textViewClockOutTime = itemView.findViewById(R.id.textViewClockOutTime);
        }

        public void bind(Shift shift) {
            textViewEmployeeName.setText(shift.getEmployeeName());
            textViewShiftDate.setText(shift.getShiftDate());
            textViewStartTime.setText(shift.getStartTime());
            textViewEndTime.setText(shift.getEndTime());
            textViewClockInTime.setText(shift.getClockInTime());
            textViewClockOutTime.setText(shift.getClockOutTime());
        }
    }


}
