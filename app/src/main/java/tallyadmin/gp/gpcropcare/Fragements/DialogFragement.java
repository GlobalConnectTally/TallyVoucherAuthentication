package tallyadmin.gp.gpcropcare.Fragements;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tallyadmin.gp.gpcropcare.R;

public class DialogFragement extends DialogFragment {
    private RecyclerView recycler_company;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        recycler_company = view.findViewById(R.id.recyler_company);
        recycler_company.setHasFixedSize(true);
        recycler_company.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}
