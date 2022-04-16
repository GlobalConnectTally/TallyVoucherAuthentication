package tallyadmin.gp.gpcropcare.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import tallyadmin.gp.gpcropcare.Interface.ItemClickListener;

public class CompanyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ItemClickListener itemClickListener;

    public CompanyViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
        super(itemView);
        this.itemClickListener = itemClickListener;
    }

    public CompanyViewHolder(@NonNull View itemView) {
        super(itemView);

    }

    @Override
    public void onClick(View v) {

    }
}
