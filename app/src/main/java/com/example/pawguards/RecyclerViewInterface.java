package com.example.pawguards;

public interface RecyclerViewInterface {

    boolean onQueryTextSubmit(String query);

    boolean onQueryTextChange(String newText);
    void OnItemClick(int position);
}
