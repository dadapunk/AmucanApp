package com.amucan.amucan.Utils.MapUtils.OsmNodeUtils;

import com.amucan.amucan.Models.ParcelablePOI;

/**
 * Created by Valdio Veliu on 03/05/2017.
 */

public interface ParcelablePoiResponseListener {
    void onPoiReceived(ParcelablePOI poi);

    void onFailure();
}
