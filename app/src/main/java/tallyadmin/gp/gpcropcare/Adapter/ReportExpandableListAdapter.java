package tallyadmin.gp.gpcropcare.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tallyadmin.gp.gpcropcare.Model.Report;

import tallyadmin.gp.gpcropcare.R;
import tallyadmin.gp.gpcropcare.Sharepreference.Companysave;

public class ReportExpandableListAdapter extends BaseExpandableListAdapter {
    Context context;
    private ArrayList<Report> expandableListTitle;

    Companysave companydata;

    public ReportExpandableListAdapter(Context context, ArrayList<Report> expandableListTitle) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return expandableListTitle.get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;




    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.reportsubitem, null);
        }
        TextView subnamet = (TextView) convertView.findViewById(R.id.subnamet);
        TextView credit = (TextView) convertView.findViewById(R.id.texto_crd);
        TextView percentage = (TextView) convertView.findViewById(R.id.texto_pcdt);
        TextView salesamnt = (TextView) convertView.findViewById(R.id.txt_slsamountt);
        TextView sqntnt = (TextView) convertView.findViewById(R.id.txt_sq);
        TextView subnametdaytw = (TextView) convertView.findViewById(R.id.txt_daycr);
//        subnamet.setText(expandableListTitle.get(listPosition).getSubcategoryname());
//        credit.setText(expandableListTitle.get(listPosition).getCreditAmtsb());
//        percentage.setText(expandableListTitle.get(listPosition).getPercentage());
//        salesamnt.setText(expandableListTitle.get(listPosition).getSalesAmountsb());
//        sqntnt.setText(expandableListTitle.get(listPosition).getSalesQuantitysb());
//        subnametdaytw.setText(expandableListTitle.get(listPosition).getCreditDayssb());
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
//        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
//                .size();

        companydata = new Companysave(context);
//        return  expandableListTitle.size();
        return  companydata.getSizel();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

//    @Override
//    public Object getChild(int i, int i1) {
//        return null;
//    }

    @Override
    public int getGroupCount() {
        return expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.reportsubitemtotal, null);
        }

//        TextView category = (TextView) convertView.findViewById(R.id.categoryst);
//        TextView alowed = (TextView) convertView.findViewById(R.id.allowedst);
//        TextView subcate = (TextView) convertView.findViewById(R.id.texto_pcdt);
        TextView crday = (TextView) convertView.findViewById(R.id.crdday);
        TextView crdamt = (TextView) convertView.findViewById(R.id.crdamt);
        TextView salesamt = (TextView) convertView.findViewById(R.id.slesamt);
        TextView sasledmiaish= (TextView) convertView.findViewById(R.id.miaishi);
        TextView daythema = (TextView) convertView.findViewById(R.id.miathem);
        TextView crdqnrtnt = (TextView) convertView.findViewById(R.id.crdqnt);
//        category.setText(expandableListTitle.get(listPosition).getCategoryName());
//        alowed.setText(expandableListTitle.get(listPosition).getAllowedSubGrp());
        crday.setText(expandableListTitle.get(listPosition).getCreditDays());
        crdamt.setText(expandableListTitle.get(listPosition).getCreditAmt());
        salesamt.setText(expandableListTitle.get(listPosition).getSalesAmount());
        sasledmiaish.setText(expandableListTitle.get(listPosition).getThe120Days());
        daythema.setText(expandableListTitle.get(listPosition).getThe180Days());
        crdqnrtnt.setText(expandableListTitle.get(listPosition).getSalesQuantity());
//        subnametdaytw.setText(expandableListTitle.get(listPosition).getCreditDays());
        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return false;
    }



    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}

