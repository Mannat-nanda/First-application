package com.example.mannatsandroidlab;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public MutableLiveData<Boolean> isChecked = new MutableLiveData<>(false);
}