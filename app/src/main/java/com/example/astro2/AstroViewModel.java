package com.example.astro2;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.astrocalculator.AstroCalculator;


public class AstroViewModel extends ViewModel{

    private final MutableLiveData<Double> longitude = new MutableLiveData<>();
    private final MutableLiveData<Double> latitude=new MutableLiveData<>();
    private final MutableLiveData<Integer> refresh=new MutableLiveData<>();

    private MutableLiveData<AstroCalculator> astro=new MutableLiveData<>();

    public MutableLiveData<Double> getLongitude() {
        return longitude;
    }

    public MutableLiveData<Double> getLatitude() {
        return latitude;
    }

    public MutableLiveData<Integer> getRefresh() {
        return refresh;
    }

    public MutableLiveData<AstroCalculator> getAstro() {
        return astro;
    }

    public void setAstro(AstroCalculator newastro) {
        astro.setValue(newastro);
    }

    public void setLatitude(Double newlatitude) {
        latitude.setValue(newlatitude);
    }
    public void setLongitude(Double newLongitude) {
        longitude.setValue(newLongitude);
    }
    public void setRefresh(Integer newRefresh) {
        refresh.setValue(newRefresh);
    }


}


