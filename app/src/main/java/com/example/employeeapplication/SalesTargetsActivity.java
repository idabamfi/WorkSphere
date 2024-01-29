package com.example.employeeapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SalesTargetsActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference salesTargetsRef = database.getReference("workspheredatabase");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light);
        setContentView(R.layout.activity_sales);
    }
    public class SalesTarget {
        private int salesTarget;
        private int salesAchieved;
        private float salesDifference;
        private String dateGiven;

        public SalesTarget() {
            // Default constructor required for calls to DataSnapshot.getValue(SalesTarget.class)
        }

        public SalesTarget(int salesTarget, int salesAchieved, float salesDifference, String dateGiven) {
            this.salesTarget = salesTarget;
            this.salesAchieved = salesAchieved;
            this.salesDifference = salesDifference;
            this.dateGiven = dateGiven;
        }

//        DatabaseReference mondayRef = salesTargetsRef.child("Monday");
//        mondayRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot){
//                if (dataSnapshot.exists()) {
//                    SalesTarget mondaySalesTarget = dataSnapshot.getValue(SalesTarget.class);
//                    if (mondaySalesTarget != null) {
//                        // Use mondaySalesTarget object to access the retrieved data
//                        int salesTarget = mondaySalesTarget.getSalesTarget();
//                        int salesAchieved = mondaySalesTarget.getSalesAchieved();
//                        float salesDifference = mondaySalesTarget.getSalesDifference();
//                        String dateGiven = mondaySalesTarget.getDateGiven();
//
//                        // Perform operations with the retrieved data
//                    }
//                }
//            }
//    }
//
//        private int getDateGiven() {
//            return String;
//        }

        private float getSalesDifference() {
            return 0;
        }

        private int getSalesAchieved() {
            return 0;
        }

        private int getSalesTarget() {

            return 0;
        }
    }
}
