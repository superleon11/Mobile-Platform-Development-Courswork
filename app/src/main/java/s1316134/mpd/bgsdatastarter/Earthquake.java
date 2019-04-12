package s1316134.mpd.bgsdatastarter;

//
// Name                 Jordan O'Donnell
// Student ID           s1316134
// Programme of Study   Computing
//

import java.util.HashMap;
import java.util.Map;

public class Earthquake {


    //Holds data on an earthquake
    private String title;
    private String desc;
    private String pubDate;
    private String location;
    private Double geoLat;
    private Double geoLong;
    private Double magnitude;
    private String colour;
    private String depth;


    public Earthquake() {
    }


    public Earthquake(String title, String desc, String pubDate, Double geoLat, Double geoLong) {
        this.title = title;
        this.desc = desc;
        this.pubDate = pubDate;
        this.geoLat = geoLat;
        this.geoLong = geoLong;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        parseDesc(desc);
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public Double getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(Double geoLat) {
        this.geoLat = geoLat;
    }

    public Double getGeoLong() {
        return geoLong;
    }

    public void setGeoLong(Double geoLong) {
        this.geoLong = geoLong;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }



    public void parseDesc(String desc) {
        String[] split = desc.split(";");

        Map<String, String> map = new HashMap<>();

        for (String s : split) {
            if (!s.contains("Origin")) {

                String[] vals = s.split(":");
                for (int i = 0; i < vals.length; i += 2) {
                    map.put(vals[i], vals[i + 1]);
                }
            }
        }



        for (String s : map.keySet()) {
            if (s.contains("Location")) {
                this.location = map.get(s);

            }
        }

        for (String s : map.keySet()) {
            if (s.contains("Depth")) {
                this.depth = map.get(s);

            }
        }

        for (String s : map.keySet()) {
            if (s.contains("Magnitude")) {
                this.magnitude = Double.parseDouble(map.get(s));
                if (magnitude > -2 && magnitude < 1) {
                    this.colour = "green";
                }
                if (magnitude > 0.9 && magnitude < 2) {
                    this.colour = "yellow";
                }
                if (magnitude > 1.9) {
                    this.colour = "red";
                }
            }
        }

    }

    @Override
    public String toString() {
        return ("Earthquake detected at: " +
                 location + "\n" +

                "Date: " + pubDate + "\n" +
        "Magnitude: " + magnitude + "\n" +
                "Depth:" + depth);
    }
}
