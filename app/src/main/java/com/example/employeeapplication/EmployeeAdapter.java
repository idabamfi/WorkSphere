package com.example.employeeapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private List<Employee> employees;

    public EmployeeAdapter(List<Employee> employees) {
        this.employees = employees;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.textViewEmployeeName.setText("Name: " + employee.getName());
        holder.textViewEmployeeNumber.setText("Employee Number: " + employee.getNumber());
        holder.textViewHolidayDays.setText("Holiday Days: " + employee.getHolidayDays());
        holder.textViewHourlyPay.setText("Hourly Pay: " + employee.getHourlyPay());
        holder.textViewEmployeeId.setText("Employee ID: " + employee.getId());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEmployeeName;
        TextView textViewEmployeeNumber;
        TextView textViewHolidayDays;
        TextView textViewHourlyPay;
        TextView textViewEmployeeId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEmployeeName = itemView.findViewById(R.id.textViewEmployeeName);
            textViewEmployeeNumber = itemView.findViewById(R.id.textViewEmployeeNumber);
            textViewHolidayDays = itemView.findViewById(R.id.textViewHolidayDays);
            textViewHourlyPay = itemView.findViewById(R.id.textViewHourlyPay);
            textViewEmployeeId = itemView.findViewById(R.id.textViewEmployeeId);
        }
    }

}
