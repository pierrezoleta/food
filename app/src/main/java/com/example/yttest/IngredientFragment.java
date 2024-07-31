package com.example.yttest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


public class IngredientFragment extends Fragment {

    String ingredientList[];
public IngredientFragment(String... ingredientList) {
    this.ingredientList = ingredientList;
}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        //String ingredientList[] = {"Ingredient 1","Ingredient 2", "Ingredient 3", "Ingredient 4","Ingredient 5", "Ingredient 6", "Ingredient 7", "Ingredient 8"};
        ListView ingredientLv = view.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, ingredientList);
        ingredientLv.setAdapter(adapter);

        return view;
    }
}