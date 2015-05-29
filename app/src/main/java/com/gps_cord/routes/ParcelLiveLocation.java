package com.gps_cord.routes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.PolylineOptions;

public class ParcelLiveLocation implements Parcelable {
	private PolylineOptions rectLine;
	
	public ParcelLiveLocation(PolylineOptions rectLine) {
		this.rectLine = rectLine;
	}

	public PolylineOptions getPolyLine() {
		return rectLine;
	}
	
	public ParcelLiveLocation(Parcel in) {
        
    }
	
	public final Parcelable.Creator<ParcelLiveLocation> CREATOR = new Parcelable.Creator<ParcelLiveLocation>() {
        public ParcelLiveLocation createFromParcel(Parcel in) {
            return new ParcelLiveLocation(in);
        }

        public ParcelLiveLocation[] newArray(int size) {
            return new ParcelLiveLocation[size];
        }
    };
    
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		rectLine.writeToParcel(dest, flags);
		
	}


}
