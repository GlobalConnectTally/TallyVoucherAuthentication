//package tallyadmin.gp.gpcropcare.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import tallyadmin.gp.gpcropcare.Model.Subcategorylist;
//import tallyadmin.gp.gpcropcare.Model.categorylist;
//import tallyadmin.gp.gpcropcare.R;
//import java.util.ArrayList;
//
//public class Subcategoryadapter extends BaseExpandableListAdapter {
//
//
//        Context mContext;
//        private ArrayList<categorylist> pendinglist1;
//        private ArrayList<categorylist> originalList;
//
//        public Subcategoryadapter(Context context, ArrayList<categorylist> outlist) {
//            this.mContext = context;
//            this.pendinglist1 = new ArrayList<>();
//            this.pendinglist1.addAll(outlist);
//            this.originalList = new ArrayList<categorylist>();
//            this.originalList.addAll(outlist);
//        }
//
//        @Override
//        public int getGroupCount() {
//            return pendinglist1.size();
//        }
//
//        @Override
//        public int getChildrenCount(int i) {
//            ArrayList<Subcategorylist> billdetailsList = pendinglist1.get(i).getSubcategorylists();
//            return billdetailsList.size();
//        }
//
//        @Override
//        public Object getGroup(int i) {
//            return pendinglist1.get(i);
//        }
//
//        @Override
//        public Object getChild(int i, int i1) {
//            ArrayList<Subcategorylist> billdetails =pendinglist1.get(i).getSubcategorylists();
//            return billdetails.get(i1);
//        }
//
//        @Override
//        public long getGroupId(int i) {
//            return i;
//        }
//
//        @Override
//        public long getChildId(int i, int i1) {
//            return i1;
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return false;
//        }
//
//        @Override
//        public View getGroupView(int groupPosition, final boolean isExpanded, View convertView, ViewGroup viewGroup) {
//            final categorylist pendingdetails = (categorylist) getGroup(groupPosition);
//            if (convertView == null) {
//                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = layoutInflater.inflate(R.layout.pending_group, null);
//            }
//            TextView heading = convertView.findViewById(R.id.orde_noheading);
//            TextView amtheading = convertView.findViewById(R.id.amt_heading);
//
//            ImageButton groupindicator = convertView.findViewById(R.id.group_indicator);
//
//            heading.setText(pendingdetails.getSubcategory().trim());
//
//
//            groupindicator.setSelected(isExpanded);
//            groupindicator.setTag(groupPosition);
//            groupindicator.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = (Integer)v.getTag();
//                    OnIndicatorClick(isExpanded,position);
//                }
//                private void OnIndicatorClick(boolean isExpanded, int position) {
//                    if (isExpanded) {
//                        categorylist.collapseGroup(position);
//                    } else {
//                        categorylist.expandGroup(position);
//                    }
//                }
//            });
//
//            return convertView;
//        }
//
//        @Override
//        public View getChildView(int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
//            Subcategorylist subcategorylist = (Subcategorylist) getChild(groupPosition, childPosition);
//            if (convertView == null) {
//                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = layoutInflater.inflate(R.layout.reportsubitem, null);
//            }
//            TextView subcategoryname = convertView.findViewById(R.id.textp_part);
//            TextView creditamount = convertView.findViewById(R.id.texto_amountpp);
//            TextView percentage = convertView.findViewById(R.id.texto_datepp);
//            TextView salesquantity = convertView.findViewById(R.id.txt_reffpp);
//            TextView creditdays = convertView.findViewById(R.id.txt_day);
//            TextView salesamount = convertView.findViewById(R.id.txt_slsamount);
//
//            subcategoryname.setText(Subcategorylist.getSubcategoryname().trim());
//            creditamount.setText(Subcategorylist.getCreditamount().trim());
//            percentage.setText(Subcategorylist.getPercentage().trim());
//            salesquantity.setText(Subcategorylist.getSalesquantity().trim());
//            creditdays.setText(Subcategorylist.getCreditdays().trim());
//            salesamount.setText(Subcategorylist.getSalesamount().trim());
//
//            return convertView;
//        }
//
//
//        @Override
//        public boolean isChildSelectable(int i, int i1) {
//            return false;
//        }
//
////        class ViewHolder {
////            @BindView(R.id.subcategory)
////            TextView billno;
////
////
////
////            ViewHolder(View view)
////            {
////                ButterKnife.bind(this, view);
////            }
////        }
//    }
//
