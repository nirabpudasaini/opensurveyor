package devedroid.opensurveyor.data;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.osmdroid.api.IGeoPoint;

import android.location.Location;
import devedroid.opensurveyor.Utils;

public class Drawing implements Serializable {
	
	protected LocationData location;
	
	private List<List<IGeoPoint> > data = new ArrayList<List<IGeoPoint>>();
	
	protected  long timeStamp;

	public LocationData getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		if(location==null) 
			this.location = null; 
		else
			this.location = new LocationData(location);
	}
	
	public void setLocation(LocationData location) {
		if(location==null) 
			this.location = null; 
		else
			this.location = new LocationData(location);
	}
	public void setLocation(IGeoPoint location) {
		if(location==null) 
			this.location = null; 
		else
			this.location = new LocationData(location);
	}

	public long getTimestamp() {
		return timeStamp;
	}
	public void setTimestamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	
	public void writeXML(Writer w)  throws IOException {
		w.append("\t<drawing time=\"").append(Utils.formatISOTime(new Date(getTimestamp()))).append("\" ");
		w.append(">\n");
		for(List<IGeoPoint> segment: data) {
			w.append("\t<segment>\n");
			for( IGeoPoint pt: segment) {
				w.append(String.format(Locale.US, 
						"\t\t<pt lat=\"%.6f\" lon=\"%.6f\">\n", 
						pt.getLatitudeE6()*1e-6d, pt.getLongitudeE6()*1e-6d));
			}
			w.append("\t</segment>\n");
		}
		w.append("\t</drawing>\n");
	}
	public void setData(List<List<IGeoPoint>> data) {
		this.data = data;
		if(!data.isEmpty() && !data.get(0).isEmpty())
			setLocation(data.get(0).get(0));
	}

}
