package com.hospital.restapi.misc;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ParsedHeaderData {

    public final String username;
    public final String password;

    public ParsedHeaderData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static ParsedHeaderData fromHeaderString(String authHeader) {
        if (authHeader != null && authHeader.toLowerCase().startsWith("basic")) {
            String base64Credentials = authHeader.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);

            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            if (credentials.contains(":")) {
                String[] values = credentials.split(":", 2);
                return new ParsedHeaderData(values[0], values[1]);
            }
        }
        return new ParsedHeaderData("", "");
    }
}