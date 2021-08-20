package com.rumahku.rumahku.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<HomeModel>> listHome = new MutableLiveData<>();
    final ArrayList<HomeModel> homeModelArrayList = new ArrayList<>();

    private static final String TAG = HomeViewModel.class.getSimpleName();

    public void setAllHome() {
        homeModelArrayList.clear();

        try {
            FirebaseFirestore
                    .getInstance()
                    .collection("home")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                HomeModel model = new HomeModel();
                                List<String> hospitals = (List<String>) document.get("hospital");
                                model.setHospital(hospitals);
                                model.setLatlng("" + document.get("latlng"));
                                model.setLocation("" + document.get("location"));
                                List<String> recreation = (List<String>) document.get("recreation");
                                model.setRecreation(recreation);
                                List<String> school = (List<String>) document.get("school");
                                model.setSchool(school);
                                List<String> shopping = (List<String>) document.get("shopping");
                                model.setShopping(shopping);
                                model.setTitle("" + document.get("title"));
                                homeModelArrayList.add(model);
                            }
                            listHome.postValue(homeModelArrayList);
                        } else {
                            Log.e(TAG, task.toString());
                        }
                    });
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public LiveData<ArrayList<HomeModel>> getHome() {
        return listHome;
    }

}