/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fixer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 *
 * @author FARIZ-T14
 */
public class UpdateChecker {

    private static final String VERSION_URL =
        "https://raw.githubusercontent.com/fariz-armesta/fixer/main/version.txt";
    private static final String CURRENT_VERSION = "1.0.0";

    public static String checkForUpdate() {
        try {
            URL url = new URL(VERSION_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String latestVersion = reader.readLine().trim();
            reader.close();

            if (!latestVersion.equals(CURRENT_VERSION)) {
                return latestVersion;
            }
        } catch (Exception e) {
            // Silently fail if offline or GitHub unreachable — don't block the app
        }
        return null;
    }
}
