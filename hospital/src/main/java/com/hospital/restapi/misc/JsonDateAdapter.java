package com.hospital.restapi.misc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.json.bind.adapter.JsonbAdapter;

public class JsonDateAdapter implements JsonbAdapter<Date, String> {

    @Override
    public String adaptToJson(Date obj) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));
        return dateFormat.format(obj);
    }

    @Override
    public Date adaptFromJson(String obj) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));
        try {
            return dateFormat.parse(obj);
        } catch (ParseException e) {
            return null;
        }
    }

}
