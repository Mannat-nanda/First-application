package com.example.mannatsandroidlab;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

public class ChatRoomViewModel extends ViewModel {

    // LiveData to hold the list of chat messages
    public MutableLiveData<ArrayList<ChatMessage>> messages = new MutableLiveData<>(new ArrayList<>());
}