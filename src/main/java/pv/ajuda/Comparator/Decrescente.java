package pv.ajuda.Comparator;

import pv.ajuda.System.TopBuilder;

import java.util.Comparator;

public class Decrescente implements Comparator<TopBuilder> {
    @Override
    public int compare(TopBuilder staff1, TopBuilder staff2) {
        if(staff1.getPoints() > staff2.getPoints()){
            return -1;
        }
        return 1;
    }
}
