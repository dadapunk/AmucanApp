package com.amucan.amucan.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amucan.amucan.Models.ParcelablePOI;
import com.amucan.amucan.R;
import com.amucan.amucan.Utils.MapUtils.OpeningHours.OpeningHoursUtils;
import com.amucan.amucan.Utils.MapUtils.OsmTags;
import com.amucan.amucan.Utils.PoiHelper;
import com.amucan.amucan.Utils.RecyclerView.CategoryColoringUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Valdio Veliu on 26/04/2017.
 */
public class ParcelablePoiListAdapter extends RecyclerView.Adapter<ParcelablePoiListAdapter.ViewHolder> {

    private Context context;
    private List<ParcelablePOI> data;// this is a temp file fof preview purposes

    public ParcelablePoiListAdapter(Context context, List<ParcelablePOI> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_list_item, parent, false);
       return new ParcelablePoiListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ParcelablePoiListAdapter.ViewHolder holder, int position) {
        holder.title.setText(data.get(position).getPoiName());

        Map<String, String> tags = data.get(position).getTags();
        boolean hasDetailData = false;
        if (tags != null) {
            // Address info
            holder.address.setText(PoiHelper.createAddressDisplayString(data.get(position)));
            if (holder.address.getText() != null && !holder.address.getText().equals("")) {
                holder.address.setVisibility(View.VISIBLE);
                hasDetailData = true;
            } else {
                holder.address.setVisibility(View.GONE);
            }

            // Opening hours
            if (tags.containsKey(OsmTags.OPENING_HOURS)) {
                if (OpeningHoursUtils.isOpenNow(tags.get(OsmTags.OPENING_HOURS))) {
                    holder.openingHours.setText(R.string.open);
                    holder.openingHours.setTextColor(context.getResources().getColor(R.color.open));
                } else {
                    holder.openingHours.setText(R.string.closed);
                    holder.openingHours.setTextColor(context.getResources().getColor(R.color.closed));
                }
            } else {
                holder.openingHours.setText(null);
            }

            if (holder.openingHours.getText() != null && !holder.openingHours.getText().equals("")) {
                holder.openingHours.setVisibility(View.VISIBLE);
            } else {
                if (hasDetailData) {
                    holder.openingHours.setVisibility(View.GONE);
                } else {
                    holder.openingHours.setText(R.string.no_info_available);
                    holder.openingHours.setTextColor(context.getResources().getColor(R.color.grey600));
                }
            }
        }

        // category icon
        if (data.get(position).getTags().containsKey("cuisine")) {
            CategoryColoringUtil.setupPlaceIcon(
                    context, data.get(position).getPoiClassType(),
                    data.get(position).getTags().get("cuisine"), holder.coverImage
            );
        } else {
            CategoryColoringUtil.setupPlaceIcon(
                    context, data.get(position).getPoiClassType(), holder.coverImage
            );
        }
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImage;
        TextView title;
        TextView openingHours;
        TextView address;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.placeTitle);
            coverImage = itemView.findViewById(R.id.placeImage);
            openingHours = itemView.findViewById(R.id.placeOpeningHours);
            address = itemView.findViewById(R.id.placeAddress);
        }
    }
}
