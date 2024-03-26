package com.example.healthtrack.Utils;

import com.example.healthtrack.Models.Record;

import java.util.Comparator;

public class stepComparator implements Comparator<Record> {
    @Override
    public int compare(Record p1, Record p2) {
        return Integer.compare(p2.getStepTotal(), p1.getStepTotal());
    }
}
