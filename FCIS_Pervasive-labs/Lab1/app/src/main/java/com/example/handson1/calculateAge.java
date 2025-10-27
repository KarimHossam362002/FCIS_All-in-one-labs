package com.example.handson1;

public class calculateAge {

    private int birthYear;



    public int getBirthYear() {
        return this.birthYear;
    }
    public void setBirthYear(int year) {
        if (year > 0) {
            this.birthYear = year;
        } else {
            //Toast.makeText(this,"Invalid year. Please provide a positive number for the year.",Toast.LENGTH_LONG).show();
            System.out.println("Invalid year. Please provide a positive number for the year.");
        }
    }

    public int ageCalculate() {

        if (this.birthYear > 0 && this.birthYear <= 2025) {
            return 2025 - this.birthYear;
        } else {
            return -1;
        }
    }
}
