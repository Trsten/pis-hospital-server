package com.hospital.restapi.misc;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.json.bind.adapter.JsonbAdapter;

public class JsonTimestampAdapter implements JsonbAdapter<Timestamp, String> {

    @Override
    public String adaptToJson(Timestamp obj) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));
        return dateFormat.format(obj);
    }

    @Override
    public Timestamp adaptFromJson(String obj) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));
        try {
            return new Timestamp(dateFormat.parse(obj).getTime());
        } catch (ParseException e) {
            return null;
        }
    }

}